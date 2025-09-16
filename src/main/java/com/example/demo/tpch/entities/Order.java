package com.example.demo.tpch.entities;

import java.time.LocalDate;

public record Order(
    long orderKey,
    long customerKey,
    String orderStatus,
    double totalPrice,
    LocalDate orderDate,
    String orderPriority,
    String clerk,
    int shipPriority,
    String comment
) implements TpchEntity<Order> {

    public Order() {
        this(0, 0, "", 0.0, LocalDate.now(), "", "", 0, "");
    }

    @Override
    public Long getKey() {
        return Long.valueOf(orderKey);
    }
}
