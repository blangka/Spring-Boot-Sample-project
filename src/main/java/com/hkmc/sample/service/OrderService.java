package com.hkmc.sample.service;

import com.hkmc.sample.entity.Delivery;
import com.hkmc.sample.entity.Member;
import com.hkmc.sample.entity.Order;
import com.hkmc.sample.entity.OrderItem;
import com.hkmc.sample.entity.item.Item;
import com.hkmc.sample.repo.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderRepositoryOld orderRepositoryOld;
    private final MemberRepository memberRepository;
    private final ItemRepositoryOld itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.cancel();
    }

    /**
     * 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepositoryOld.findAllByString(orderSearch);
    }
}
