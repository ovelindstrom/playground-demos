package com.example.demo.tpch.entities;

import java.util.Map;
import java.util.Objects;

public record PartSupplier(
    long partKey,
    long suppKey,
    int availQty,
    double supplyCost,
    String comment
) implements TpchEntity<PartSupplier> {

    public PartSupplier() {
        this(0, 0, 0, 0.0, "");
    }
    
    @Override
    public Integer getKey() {
        return Objects.hash(partKey, suppKey);
    }

}
