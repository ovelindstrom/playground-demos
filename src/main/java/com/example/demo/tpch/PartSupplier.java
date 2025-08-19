package com.example.demo.tpch;

public record PartSupplier(
    int partKey,
    int suppKey,
    int availQty,
    double supplyCost,
    String comment
) implements TpchEntity<PartSupplier> {

    public PartSupplier() {
        this(0, 0, 0, 0.0, "");
    }

    @Override
    public String toLine() {
        return String.join("|",
            String.valueOf(partKey),
            String.valueOf(suppKey),
            String.valueOf(availQty),
            String.valueOf(supplyCost),
            comment
        );
    }

    @Override
    public PartSupplier fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new PartSupplier(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            Double.parseDouble(parts[3]),
            parts[4]
        );
    }
}
