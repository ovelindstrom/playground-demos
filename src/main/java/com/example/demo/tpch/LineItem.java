package com.example.demo.tpch;

import java.time.LocalDate;

public record LineItem(
    int orderKey,
    int partKey,
    int suppKey,
    int lineNumber,
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
    public LineItem fromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 16) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new LineItem(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
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
            parts[15]
        );
    }
}
