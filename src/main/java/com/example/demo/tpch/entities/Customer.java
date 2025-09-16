package com.example.demo.tpch.entities;

public record Customer (
                        long customerKey,
                        String name,
                        String address,
                        long nationKey,
                        String phone, 
                        long accountBalanceInCents,
                        String marketSegment,
                        String comment) implements TpchEntity<Customer> {

    public Customer() {
        this(0, "", "", 0, "", 0, "", "");
    }


    @Override
    public Long getKey() {
        return customerKey;
    }

}
