package com.hkmc.sample.repo.jpa;

import com.hkmc.sample.entity.Order;
import com.hkmc.sample.model.dto.OrderDto;
import com.hkmc.sample.model.enums.OrderStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hkmc.sample.entity.QMember.member;
import static com.hkmc.sample.entity.QOrder.order;
import static com.hkmc.sample.entity.QOrderItem.orderItem;
import static com.hkmc.sample.entity.item.QItem.item;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> search(OrderSearch orderSearch) {
        return queryFactory
                .select(order).distinct()
                .from(order)
                .join(order.member,member).fetchJoin()
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .where(memberNameEq(orderSearch.getMemberName())
                        ,orderStatusEq(orderSearch.getOrderStatus()))
                .fetch();
    }

    @Override
    public Page<Order> searchPageSimple(OrderSearch orderSearch, Pageable pageable) {
        QueryResults<Order> results =  queryFactory
                .select(order)
                .from(order)
                .join(order.member, member).fetchJoin()
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .where(memberNameEq(orderSearch.getMemberName())
                        , orderStatusEq(orderSearch.getOrderStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Order> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<OrderDto> searchPageComplex(OrderSearch orderSearch, Pageable pageable) {
        return null;
    }

    private BooleanExpression memberNameEq(String memberName) {
        return hasText(memberName) ? member.name.eq(memberName) : null;
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus){
        return orderStatus != null ? order.status.eq(orderStatus) : null;
    }
}
