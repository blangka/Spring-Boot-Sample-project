package com.hkmc.sample.model.dto;

import com.hkmc.sample.entity.Order;
import com.hkmc.sample.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResOrder {

    private Long id;

    private ResMember member;
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;
    private OrderStatus status;

    @Data
    public static class OrderItem {
        private ResItem item;
        private int orderPrice; //주문가격
        private int count; //주문수량
    }

    public static ResOrder of(Order order) {
        return new ModelMapper().map(order,ResOrder.class);
    }
}
