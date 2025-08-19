package com.example.demo.tpch;

public record Supplier(
    int suppKey,
    String name,
    String address,
    int nationKey,
    String phone,
    double acctBal,
    String comment
) implements TpchEntity<Supplier> {

    public Supplier() {
        this(0, "", "", 0, "", 0.0, "");
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
    public Supplier fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Supplier(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            Integer.parseInt(parts[3]),
            parts[4],
            Double.parseDouble(parts[5]),
            parts[6]
        );
    }
}
