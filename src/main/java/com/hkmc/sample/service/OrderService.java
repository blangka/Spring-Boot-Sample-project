package com.hkmc.sample.service;

import com.hkmc.sample.entity.Delivery;
import com.hkmc.sample.entity.Member;
import com.hkmc.sample.entity.Order;
import com.hkmc.sample.entity.OrderItem;
import com.hkmc.sample.entity.item.Item;
import com.hkmc.sample.repo.jpa.ItemRepository;
import com.hkmc.sample.repo.jpa.MemberRepository;
import com.hkmc.sample.repo.jpa.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
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
    public void cancleOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancle();
    }

    /**
     * 검색
     */
}
