package com.example.demo.tpch;

public sealed interface TpchEntity<T>
        permits Customer, LineItem, Nation, Order, Part, PartSupplier, Region, Supplier {

    String toLine();

    T fromLine(String line);

    static <E extends TpchEntity<E>> E fromLine(String line, java.util.function.Supplier<E> supplier) {
        E entity = supplier.get();
        return entity.fromLine(line);
    }
}
