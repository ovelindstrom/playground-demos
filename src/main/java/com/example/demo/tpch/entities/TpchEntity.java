package com.example.demo.tpch.entities;

import java.util.Map;

public sealed interface TpchEntity<T>
        permits Customer, LineItem, Nation, Order, Part, PartSupplier, Region, Supplier {

    String toLine();

    T fromLine(String line, Map<String, Map<Long, ? extends TpchEntity<?>>> maps);

    default T fromLine(String line) {
        return fromLine(line, null);
    }

    
    Object getKey();

    // Utility method
    /* 
    static <E extends TpchEntity<E>> E fromLine(String line, java.util.function.Supplier<E> supplier) {
        E entity = supplier.get();
        return entity.fromLine(line);
    }
        */

}
