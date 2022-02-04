package com.hkmc.sample.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hkmc.sample.common.error.ApiException;
import com.hkmc.sample.common.error.ExceptionEnum;
import com.hkmc.sample.model.enums.DeliveryStatus;
import com.hkmc.sample.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) //그냥 생성하지말고 create를 쓰라는 것임
@AllArgsConstructor
@Builder
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = LAZY) //다대일 관계
    @JoinColumn(name = "member_id") //외래키의 관계, 이런경우 연관관계 주인, 쓰기 가능하다
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /*
    * persist(orderItemA)
    * persist(orderItemB)
    * persist(orderItemC)
    * persist(order)
    *
    * 로 넣어야 되지만 cascade를 넣어주면
    * persist(order)하나만 넣어도됨
    * */

    @JsonIgnore
    @OneToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") //일대일 관계에서 외래키를 주문에 넣는 이유는 많이 사용하는 엔티티에 넣는 것이 접근하기 편. 연관관계 주인이됨
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING) //Enum Ordinal 과  String이 있는데 Ordinal 은 숫자기 때문에 쓰지 말아야 한다. 중간에 추가 되는경우 대응 안됨
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 편의==//
    /*
    *    연관관계 편의 메서드
    *
    * Member member = new Member();
    * Order order = new Order();
    *
    * member.getOrders().add(order)  -> 삭제 가능함
    * order.setMember(member);
    * */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        return Order.builder()
                .member(member)
                .delivery(delivery)
                .orderItems(List.of(orderItems))
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();
    }

    //==비즈니스 로직==//
    public void cancel() {
        if (DeliveryStatus.COMP.equals(delivery.getStatus())) {
            throw new ApiException(ExceptionEnum.STATUS_CHANGED_ERROR);
        }

        this.status = OrderStatus.CANCEL;
        orderItems.forEach(item -> {item.cancel();});
    }


    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice).sum(); //더하기
    }
}
