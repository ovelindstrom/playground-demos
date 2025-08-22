package com.example.demo.tpch.entities;

import java.util.Map;

public record Customer (
                        long customerKey,
                        String name,
                        String address,
                        long nationKey,
                        String phone, 
                        long accountBalanceInCents,
                        String marketSegment,
                        String comment,
                        Nation nation) implements TpchEntity<Customer> {

    public Customer() {
        this(0, "", "", 0, "", 0, "", "", null);
    }

    @Override
    public String toLine() {
        return String.join("|",
                String.valueOf(customerKey),
                name,
                address,
                String.valueOf(nationKey),
                phone,
                String.valueOf(accountBalanceInCents),
                marketSegment,
                comment);
    }

    @Override
    public Customer fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
       String[] parts = line.split("\\|");
       if (parts.length != 8) {
           throw new IllegalArgumentException("Invalid line format");
       }
       long nationKey = Long.parseLong(parts[3]);
       Nation nation = null;
       if (maps != null && maps.containsKey("nation")) {
           nation = (Nation) maps.get("nation").get(nationKey);
       }

       return new Customer(
               Long.parseLong(parts[0]),
               parts[1],
               parts[2],
               nationKey,
               parts[4],
               (long) (Double.parseDouble(parts[5]) * 100),
               parts[6],
               parts[7],
               nation
       );
    }

    @Override
    public Long getKey() {
        return customerKey;
    }

}
