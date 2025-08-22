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
    String comment,
    Order order,
    Part part,
    Supplier supplier
) implements TpchEntity<LineItem> {

    public LineItem() {
        this(0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0, "", "", LocalDate.now(), LocalDate.now(), LocalDate.now(), "", "", "", null, null, null);
    }

    @Override
    public String toLine() {
        return String.join("|",
            String.valueOf(orderKey),
            String.valueOf(partKey),
            String.valueOf(suppKey),
            String.valueOf(lineNumber),
            String.valueOf(quantity),
            String.valueOf(extendedPrice),
            String.valueOf(discount),
            String.valueOf(tax),
            returnFlag,
            lineStatus,
            shipDate.toString(),
            commitDate.toString(),
            receiptDate.toString(),
            shipInstruct,
            shipMode,
            comment
        );
    }

    @Override
    public LineItem fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        String[] parts = line.split("\\|");
        if (parts.length != 16) {
            throw new IllegalArgumentException("Invalid line format");
        }

        long orderKey = Long.parseLong(parts[0]);
        Order order = null;

        long partKey = Long.parseLong(parts[1]);
        Part part = null;

        long suppKey = Long.parseLong(parts[2]);
        Supplier supplier = null;
        

        if(maps != null) {
            if (maps.containsKey("parts")) {
                part = (Part) maps.get("parts").get( partKey);
            }
            if (maps.containsKey("suppliers")) {
                supplier = (Supplier) maps.get("suppliers").get(suppKey);
            }
            if (maps.containsKey("orders")) {
                order = (Order) maps.get("orders").get( orderKey);
            }
        }
        
        return new LineItem(
            orderKey,
            partKey,
            suppKey,
            Integer.parseInt(parts[3]),
            Double.parseDouble(parts[4]),
            Double.parseDouble(parts[5]),
            Double.parseDouble(parts[6]),
            Double.parseDouble(parts[7]),
            parts[8],
            parts[9],
            LocalDate.parse(parts[10]),
            LocalDate.parse(parts[11]),
            LocalDate.parse(parts[12]),
            parts[13],
            parts[14],
            parts[15],
            order,
            part,
            supplier
        );
    }

    public record Key(long orderKey, long partKey, long suppKey, long lineNumber) {
    // hashCode() and equals() automatically generated and optimized
}
    @Override
    public Key getKey() {

        return new Key(orderKey, partKey, suppKey, lineNumber);
    }
}