package com.hkmc.sample.entity;

import com.hkmc.sample.model.enums.OrderStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne //다대일 관계
    @JoinColumn(name = "member_id") //외래키의 관계, 이런경우 연관관계 주인, 쓰기 가능하다
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id") //일대일 관계에서 외래키를 주문에 넣는 이유는 많이 사용하는 엔티티에 넣는 것이 접근하기 편. 연관관계 주인이됨
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING) //Enum Ordinal 과  String이 있는데 Ordinal 은 숫자기 때문에 쓰지 말아야 한다. 중간에 추가 되는경우 대응 안됨
    private OrderStatus status; //주문상태 [ORDER, CANCLE]
}
