package com.example.demo.tpch;

import java.time.LocalDate;

public record Order(
    int orderKey,
    int custKey,
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
    public String toLine() {
        return String.join("|",
            String.valueOf(orderKey),
            String.valueOf(custKey),
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
    public Order fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 9) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Order(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1]),
            parts[2],
            Double.parseDouble(parts[3]),
            LocalDate.parse(parts[4]),
            parts[5],
            parts[6],
            Integer.parseInt(parts[7]),
            parts[8]
        );
    }
}
