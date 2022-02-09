package com.hkmc.sample.repo.jpa;

import com.hkmc.sample.entity.Order;
import com.hkmc.sample.model.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {
    List<Order> search(OrderSearch orderSearch);
    Page<Order> searchPageSimple(OrderSearch orderSearch, Pageable pageable);
    Page<OrderDto> searchPageComplex(OrderSearch orderSearch, Pageable pageable);
}
