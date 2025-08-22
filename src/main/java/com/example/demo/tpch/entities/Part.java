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
    public String toLine() {
        return String.join("|",
                String.valueOf(partKey),
                name,
                mfgr,
                brand,
                type,
                String.valueOf(size),
                container,
                String.valueOf(retailPrice),
                comment);
    }

    @Override
    public Part fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        String[] parts = line.split("\\|");
        if (parts.length != 9) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Part(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                Integer.parseInt(parts[5]),
                parts[6],
                Double.parseDouble(parts[7]),
                parts[8]);
    }

    @Override
    public Long getKey() {
        return Long.valueOf(partKey);
    }
}
