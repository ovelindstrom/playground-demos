package com.example.demo.tpch.entities;

import java.util.Map;

public record Supplier(
    long suppKey,
    String name,
    String address,
    long nationKey,
    String phone,
    double acctBal,
    String comment
) implements TpchEntity<Supplier> {

    public Supplier() {
        this(0, "", "", 0, "", 0.0, "");
    }


    @Override
    public Long getKey() {
        return Long.valueOf(suppKey);
    }
}
