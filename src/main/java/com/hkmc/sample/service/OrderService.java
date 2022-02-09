package com.hkmc.sample.service;

import com.hkmc.sample.entity.Delivery;
import com.hkmc.sample.entity.Member;
import com.hkmc.sample.entity.Order;
import com.hkmc.sample.entity.OrderItem;
import com.hkmc.sample.entity.item.Item;
import com.hkmc.sample.model.ReqList;
import com.hkmc.sample.model.ResPage;
import com.hkmc.sample.model.dto.OrderDto;
import com.hkmc.sample.model.dto.ResOrder;
import com.hkmc.sample.model.dto.SimpleOrderDto;
import com.hkmc.sample.repo.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderRepositoryOld orderRepositoryOld;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

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
        return orderRepository.search(orderSearch);
    }

    public List<ResOrder> findOrdersMappingResOrder(OrderSearch orderSearch) {
        List<Order> orders = findOrders(orderSearch);
        return orders.stream().map(ResOrder::of).collect(Collectors.toList());
    }

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    public List<SimpleOrderDto> simpleOrdersV1() {
        return findOrders().stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    public List<SimpleOrderDto> simpleOrdersV2() {
        List<Order> orders = orderRepositoryOld.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    public List<OrderDto> orderV1() {
        return findOrders().stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
    }

    public List<OrderDto> orderV2() {
        List<Order> orders = orderRepositoryOld.findAllWithItem();
        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
    }

    public List<OrderDto> orderV3(int offset, int limit) {
        List<Order> orders = orderRepositoryOld.findAllWithMemberDelivery(offset,limit);

        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
    }

    public ResPage<OrderDto> orderV4(OrderSearch orderSearch, ReqList reqList) {
        Pageable pageable = PageRequest.of(reqList.getPage(), reqList.getSize());

        Page<Order> orderPage = orderRepository.searchPageSimple(orderSearch , pageable);

        List<OrderDto> result = orderPage.getContent().stream().map(OrderDto::new).collect(Collectors.toList());

        return new ResPage<>(result, reqList.getPage(), reqList.getSize(), orderPage.getTotalElements());
    }
}
