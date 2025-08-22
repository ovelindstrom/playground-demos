package com.example.demo.tpch.entities;

import java.util.Map;
import java.util.function.Supplier;

public interface TpchEntityFactory {

    static <T extends TpchEntity<T>> T fromLine(String line, Supplier<T> supplier) {
        return fromLine(line, supplier, null);
    }

    static <T extends TpchEntity<T>> T fromLine(String line, Supplier<T> supplier, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
        T entity = supplier.get();
        return entity.fromLine(line, maps);
    }
}
