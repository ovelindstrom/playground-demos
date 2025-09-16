package com.example.demo.tpch.entities;


public record Nation(
    long nationKey,
    String name,
    long regionKey,
    String comment
) implements TpchEntity<Nation> {

    public Nation() {
        this(0, "", 0, "");
    }   

    @Override
    public Long getKey() {
        return Long.valueOf(nationKey);
    }
   

}
