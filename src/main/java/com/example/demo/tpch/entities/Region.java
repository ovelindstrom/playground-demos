package com.example.demo.tpch.entities;

import java.util.Map;

public record Region(
    long regionKey,
    String name,
    String comment
) implements TpchEntity<Region> {

    public Region() {
        this(0, "", "");
    }

    @Override
    public Long getKey() {
        return regionKey;
    }
}
