package com.hkmc.sample.service;

import com.hkmc.sample.entity.Address;
import com.hkmc.sample.entity.Member;
import com.hkmc.sample.entity.Order;
import com.hkmc.sample.entity.item.Book;
import com.hkmc.sample.model.enums.OrderStatus;
import com.hkmc.sample.repo.jpa.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("회원 가입이 정상 적으로 되어야 한다")
    public void 상품주문() throws Exception {
        //given
        Member member = Member.builder()
                .name("lim")
                .address(new Address("경기도","수지","123-123"))
                .build();
        em.persist(member);

        Book book = Book.builder()
                .name("JPA 책")
                .price(10000)
                .stockQuantity(10)
                .build();
        em.persist(book);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, order.getStatus(),"상품 주문시 상태는 ORDER이다");
        assertEquals(1, order.getOrderItems().size(), "주문수는 정확해야한다");
        assertEquals(10000 * orderCount, order.getTotalPrice(),"주문 가격은 가격 * 수량이다");
        assertEquals(8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어든다");
    }

}