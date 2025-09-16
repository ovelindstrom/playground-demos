package com.example.demo.tpch.entities;

import java.time.LocalDate;

public interface TpchEntityFactory {
    enum EntityType {
        CUSTOMER,
        LINE_ITEM,
        ORDER,
        PART,
        PART_SUPPLIER,
        REGION,
        NATION,
        SUPPLIER
    }

    static TpchEntity<?> fromLine(EntityType type, String line) {
        String[] parts = line.split("\\|");
        return switch (type) {
            case CUSTOMER -> customersFromLine(parts);
            case LINE_ITEM -> lineItemsfromLine(parts);
            case ORDER -> ordersFromLine(parts);
            case PART -> partsfromLine(parts);
            case PART_SUPPLIER -> partSuppliersFromLine(parts);
            case REGION -> regionsFromLine(parts);
            case NATION -> nationsFromLine(parts);
            case SUPPLIER -> suppliersFromLine(parts);
        };
    }



    static Region regionsFromLine(String[] parts) {
         if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Region(
            Long.parseLong(parts[0]),
            parts[1],
            parts[2]
        );
    }



    static PartSupplier partSuppliersFromLine(String[] parts) {
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid line format");
        }

        return new PartSupplier(
            Long.parseLong(parts[0]),
            Long.parseLong(parts[1]),
            Integer.parseInt(parts[2]),
            Double.parseDouble(parts[3]),
            parts[4]
        );
    }



    static Part partsfromLine(String[] parts) {
         if (parts.length != 9) {
            throw new IllegalArgumentException("Invalid line format");
        }
        return new Part(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                Integer.parseInt(parts[5]),
                parts[6],
                Double.parseDouble(parts[7]),
                parts[8]);
    }



    static Order ordersFromLine(String[] parts) {
        if (parts.length != 9) {
            throw new IllegalArgumentException("Invalid line format");
        }
        long partsKey = Long.parseLong(parts[1]);
        return new Order(
            Long.parseLong(parts[0]),
            partsKey,
            parts[2],
            Double.parseDouble(parts[3]),
            LocalDate.parse(parts[4]),
            parts[5],
            parts[6],
            Integer.parseInt(parts[7]),
            parts[8]
        );
    }



    static Nation nationsFromLine(String[] parts) {
         if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid line format");
        }

        long regionKey = Long.parseLong(parts[2]);

        return new Nation(
            Integer.parseInt(parts[0]),
            parts[1],
            regionKey,
            parts[3]
        );
    }



    static Customer customersFromLine(String[] parts) {
       if (parts.length != 8) {
           throw new IllegalArgumentException("Invalid line format");
       }
       long nationKey = Long.parseLong(parts[3]);

       return new Customer(
               Long.parseLong(parts[0]),
               parts[1],
               parts[2],
               nationKey,
               parts[4],
               (long) (Double.parseDouble(parts[5]) * 100),
               parts[6],
               parts[7]
       );
    }

    static LineItem lineItemsfromLine(String[] parts) {
        
        if (parts.length != 16) {
            throw new IllegalArgumentException("Invalid line format");
        }

        long orderKey = Long.parseLong(parts[0]);
        long partKey = Long.parseLong(parts[1]);
        long suppKey = Long.parseLong(parts[2]);
        
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
            parts[15]
        );
    }

    static Supplier suppliersFromLine(String[] parts) {
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid line format");
        }

        long nationKey = Long.parseLong(parts[3]);

        return new Supplier(
            Long.parseLong(parts[0]),
            parts[1],
            parts[2],
            nationKey,
            parts[4],
            Double.parseDouble(parts[5]),
            parts[6]
        );
    }
}
