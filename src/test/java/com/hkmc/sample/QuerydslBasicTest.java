package com.hkmc.sample;

import com.hkmc.sample.entity.*;
import com.hkmc.sample.entity.item.Book;
import com.hkmc.sample.entity.item.QBook;
import com.hkmc.sample.entity.item.QItem;
import com.hkmc.sample.model.dto.ResItem;
import com.hkmc.sample.model.enums.OrderStatus;
import com.hkmc.sample.service.MemberService;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static com.hkmc.sample.entity.QMember.member;
import static com.hkmc.sample.entity.QOrder.order;
import static com.hkmc.sample.entity.item.QBook.book;
import static com.hkmc.sample.entity.item.QItem.item;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        Member member1 = createMember("member1", "서울", "1", "1111");
        em.persist(member1);

        Member member2 = createMember("member2", "경기", "1", "1111");
        em.persist(member2);

        Book book1 = createBook("book1", 10000, 100);
        em.persist(book1);

        Book book2 = createBook("book2", 20000, 100);
        em.persist(book2);

        OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
        OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

        Delivery delivery = createDelivery(member1);
        Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
        em.persist(order);
    }

    @Test
    @DisplayName("쿼리 DSL 결과는 같아야 한다")
    public void 쿼리DSL() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("testlim")
                .address(new Address("seoul", "suji", "6666"))
                .build();

        em.persist(member1);

        //when
        Member member2 = queryFactory
                .selectFrom(member)
                .where(member.id.eq(member1.getId()))
                .fetchOne();

        //then
        assertEquals(member1.getId(),member2.getId());
    }

    @Test
    public void 쿼리DSL_검색_테스트() throws Exception {
        //given
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.name.eq("member1"))
                .fetchOne();

        Book findBook = queryFactory
                .select(book)
                .from(book)
                .where(
                        book.name.eq("book1")
                        ,book.price.gt(1000)
                )
                .fetchOne();
        //when


        //then
        assertEquals(findMember.getName(),"member1");
        assertEquals(findBook.getName(),"book1");
    }

    @Test
    public void 쿼리DSL_패치테스트() throws Exception {
        // List<Member> fetch = queryFactory
        // 	.selectFrom(member)
        // 	.fetch();

        // Member fetchOne = queryFactory
        // 	.selectFrom(member)
        // 	.fetchOne();

        // Member fetchFirst = queryFactory
        // 	.selectFrom(member)
        // 	.fetchFirst();

        // QueryResults<Member> results = queryFactory
        // 	.selectFrom(member)
        // 	.fetchResults();
        //
        // results.getTotal();
        // List<Member> content = results.getResults();

        long total = queryFactory
                .selectFrom(member)
                .fetchCount();
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void 패치JOIN_테스트() throws Exception {

        em.flush();
        em.clear();

        Order order1 = queryFactory
                .selectFrom(order)
                .where(order.status.eq(OrderStatus.ORDER))
                .limit(1)
                .fetchOne();

        boolean loaded1 = emf.getPersistenceUnitUtil().isLoaded(order1.getMember());
        assertFalse(loaded1,"패치조인 미적용");

        em.flush();
        em.clear();


        Order order2 = queryFactory
                .selectFrom(order)
                .join(order.member, member).fetchJoin()
                .where(order.status.eq(OrderStatus.ORDER))
                .limit(1)
                .fetchOne();

        boolean loaded2 = emf.getPersistenceUnitUtil().isLoaded(order2.getMember());
        assertTrue(loaded2,"패치조인 적용");
    }

    @Test
    public void 서브쿼리_테스트() throws Exception {

        QMember memberSub = new QMember("memberSub");

        List<Member> member1 = queryFactory
                .selectFrom(member)
                .where(member.name.in(
                        JPAExpressions
                                .select(memberSub.name)
                                .from(memberSub)
                                .where(memberSub.name.eq("member1"))
                ))
                .fetch();

        boolean loaded1 = member1.size() > 0 ? true : false;

        assertTrue(loaded1);
    }

    @Test
    public void 튜플() throws Exception {


        List<Tuple> fetch = queryFactory
                .select(member.name, member.id)
                .from(member)
                .fetch();

        for (Tuple table : fetch){
            System.out.println("name = " + table.get(member.name));
            System.out.println("id = " + table.get(member.id));
        }

    }

    @Test
    public void DTO() throws Exception {

        List<ResItem> fetch = queryFactory
                .select(Projections.fields(ResItem.class,
                        item.id.as("id"),
                        item.name,
                        item.price
                        ))
                .from(item)
                .fetch();

        for (ResItem table : fetch){
            System.out.println("name = " + table.getName());
            System.out.println("id = " + table.getId());
        }

    }

    @Test
    public void 동적쿼리() throws Exception {

        List<Member> findMember = queryFactory
                .selectFrom(member)
                .where(nameEq("member1"))
                .fetch();

    }

    private BooleanExpression nameEq(String name) {
        return name != null ? member.name.eq(name) : null;
    }

    private Member createMember(String name, String city, String street, String zipcode) {
        return Member.builder()
                .name(name)
                .address(new Address(city, street, zipcode))
                .build();
    }

    private Book createBook(String name, int price, int stockQuantity) {
        return Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }

    private Delivery createDelivery(Member member) {
        return Delivery.builder()
                .address(member.getAddress())
                .build();
    }
}
