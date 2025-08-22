package com.example.demo.tpch.entities;

import java.util.Map;
import java.util.Objects;

public record PartSupplier(
    long partKey,
    long suppKey,
    int availQty,
    double supplyCost,
    String comment,
    Part part,
    Supplier supplier
) implements TpchEntity<PartSupplier> {

    public PartSupplier() {
        this(0, 0, 0, 0.0, "", null, null);
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
    public PartSupplier fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid line format");
        }
        Part part = null;
        long partsKey = Long.parseLong(parts[0]);
        if (maps != null && maps.containsKey("parts")) {
            part = (Part) maps.get("parts").get(partsKey);
        }
        Supplier supplier = null;
        long supplierKey = Long.parseLong(parts[1]);
        if (maps != null && maps.containsKey("suppliers")) {
            supplier = (Supplier) maps.get("suppliers").get(supplierKey);
        }
        return new PartSupplier(
            partsKey,
            supplierKey,
            Integer.parseInt(parts[2]),
            Double.parseDouble(parts[3]),
            parts[4],
            part,
            supplier
        );
    }

    @Override
    public Integer getKey() {
        return Objects.hash(partKey, suppKey);
    }

}
