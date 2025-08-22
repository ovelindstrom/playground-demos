package com.example.demo.tpch.entities;

import java.util.Map;

public record Supplier(
    long suppKey,
    String name,
    String address,
    long nationKey,
    String phone,
    double acctBal,
    String comment,
    Nation nation
) implements TpchEntity<Supplier> {

    public Supplier() {
        this(0, "", "", 0, "", 0.0, "", null);
    }

    @Override
    public String toLine() {
        return String.join("|",
            String.valueOf(suppKey),
            name,
            address,
            String.valueOf(nationKey),
            phone,
            String.valueOf(acctBal),
            comment
        );
    }

    @Override
    public Supplier fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        String[] parts = line.split("\\|");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid line format");
        }

        Nation nation = null;
        long nationKey = Long.parseLong(parts[3]);
        if (maps != null && maps.containsKey("nations")) {
            nation = (Nation) maps.get("nations").get(nationKey);
        }

        return new Supplier(
            Long.parseLong(parts[0]),
            parts[1],
            parts[2],
            nationKey,
            parts[4],
            Double.parseDouble(parts[5]),
            parts[6],
            nation
        );
    }

    @Override
    public Long getKey() {
        return Long.valueOf(suppKey);
    }
}
