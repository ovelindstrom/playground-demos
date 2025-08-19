package com.example.demo.tpch;

public record Customer (
                        long customerKey,
                        String name,
                        String address,
                        long nationKey,
                        String phone, 
                        long accountBalanceInCents,
                        String marketSegment,
                        String comment) implements TpchEntity<Customer> {

    public Customer() {
        this(0, "", "", 0, "", 0, "", "");
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
    public Customer fromLine(String line) {
       String[] parts = line.split("\\|");
       if (parts.length != 8) {
           throw new IllegalArgumentException("Invalid line format");
       }
       return new Customer(
               Long.parseLong(parts[0]),
               parts[1],
               parts[2],
               Long.parseLong(parts[3]),
               parts[4],
               Long.parseLong(parts[5]),
               parts[6],
               parts[7]
       );
    }



}
