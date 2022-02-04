package com.hkmc.sample.repo.jpa;

import com.hkmc.sample.model.enums.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
