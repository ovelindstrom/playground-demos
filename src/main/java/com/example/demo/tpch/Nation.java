package com.example.demo.tpch;

public record Nation(
    int nationKey,
    String name,
    int regionKey,
    String comment
) implements TpchEntity<Nation> {

    public Nation() {
        this(0, "", 0, "");
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
    public Nation fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Nation(
            Integer.parseInt(parts[0]),
            parts[1],
            Integer.parseInt(parts[2]),
            parts[3]
        );
    }
}
