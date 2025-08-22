package com.example.demo.tpch.entities;

import java.time.LocalDate;
import java.util.Map;

public record Order(
    long orderKey,
    long customerKey,
    String orderStatus,
    double totalPrice,
    LocalDate orderDate,
    String orderPriority,
    String clerk,
    int shipPriority,
    String comment,
    Customer customer
) implements TpchEntity<Order> {

    public Order() {
        this(0, 0, "", 0.0, LocalDate.now(), "", "", 0, "", null);
    }

    @Override
    public String toLine() {
        return String.join("|",
            String.valueOf(orderKey),
            String.valueOf(customerKey),
            orderStatus,
            String.valueOf(totalPrice),
            orderDate.toString(),
            orderPriority,
            clerk,
            String.valueOf(shipPriority),
            comment
        );
    }

    @Override
    public Order fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        String[] parts = line.split("\\|");
        if (parts.length != 9) {
            throw new IllegalArgumentException("Invalid line format");
        }
        Customer customer = null;
        long partsKey = Long.parseLong(parts[1]);
        if (maps != null && maps.containsKey("customers")) {
            customer = (Customer) maps.get("customers").get(partsKey);
        }
        return new Order(
            Long.parseLong(parts[0]),
            partsKey,
            parts[2],
            Double.parseDouble(parts[3]),
            LocalDate.parse(parts[4]),
            parts[5],
            parts[6],
            Integer.parseInt(parts[7]),
            parts[8],
            customer
        );
    }

    @Override
    public Long getKey() {
        return Long.valueOf(orderKey);
    }
}
