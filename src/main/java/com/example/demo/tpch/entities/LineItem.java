package com.example.demo.tpch.entities;

import java.time.LocalDate;
import java.util.Map;

public record LineItem(
    long orderKey,
    long partKey,
    long suppKey,
    long lineNumber,
    double quantity,
    double extendedPrice,
    double discount,
    double tax,
    String returnFlag,
    String lineStatus,
    LocalDate shipDate,
    LocalDate commitDate,
    LocalDate receiptDate,
    String shipInstruct,
    String shipMode,
    String comment
) implements TpchEntity<LineItem> {

    public LineItem() {
        this(0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0, "", "", LocalDate.now(), LocalDate.now(), LocalDate.now(), "", "", "");
    }


    public record Key(long orderKey, long partKey, long suppKey, long lineNumber) {
    // hashCode() and equals() automatically generated and optimized
}
    @Override
    public Key getKey() {

        return new Key(orderKey, partKey, suppKey, lineNumber);
    }
}