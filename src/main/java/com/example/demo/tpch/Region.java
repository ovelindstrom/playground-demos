package com.example.demo.tpch;

public record Region(
    int regionKey,
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
    public Region fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Region(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2]
        );
    }
}
