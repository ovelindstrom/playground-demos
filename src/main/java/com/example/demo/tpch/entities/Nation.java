package com.example.demo.tpch.entities;

import java.util.Map;

public record Nation(
    long nationKey,
    String name,
    long regionKey,
    Region region,
    String comment
) implements TpchEntity<Nation> {

    public Nation() {
        this(0, "", 0, null, "");
    }   

    @Override
    public String toLine() {
        return String.join("|",
            String.valueOf(nationKey),
            name,
            String.valueOf(regionKey),
            comment
        );
    }

    @Override
    public Nation fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        String[] parts = line.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid line format");
        }

        long regionKey = Long.parseLong(parts[2]);
        Region region = null;
        if (maps != null && maps.containsKey("region")) {
            region = (Region) maps.get("region").get(regionKey);
        }

        return new Nation(
            Integer.parseInt(parts[0]),
            parts[1],
            regionKey,
            region,
            parts[3]
        );
    }

    @Override
    public Long getKey() {
        return Long.valueOf(nationKey);
    }
   

}
