package com.example.demo.tpch.entities;

import java.util.Map;

public record Part(
        long partKey,
        String name,
        String mfgr,
        String brand,
        String type,
        int size,
        String container,
        double retailPrice,
        String comment) implements TpchEntity<Part> {

    public Part() {
        this(0, "", "", "", "", 0, "", 0.0, "");
    }


    @Override
    public Long getKey() {
        return Long.valueOf(partKey);
    }
}
