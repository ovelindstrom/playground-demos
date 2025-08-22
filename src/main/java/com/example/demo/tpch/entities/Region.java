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
    public String toLine() {
        return String.join("|",
            String.valueOf(regionKey),
            name,
            comment
        );
    }

    @Override
    public Region fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        String[] parts = line.split("\\|");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Region(
            Long.parseLong(parts[0]),
            parts[1],
            parts[2]
        );
    }

    @Override
    public Long getKey() {
        return regionKey;
    }
}
