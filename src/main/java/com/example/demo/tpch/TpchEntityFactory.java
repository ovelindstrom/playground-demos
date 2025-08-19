package com.example.demo.tpch;

import java.util.function.Supplier;

public interface TpchEntityFactory {
    static <T extends TpchEntity<T>> T fromLine(String line, Supplier<T> supplier) {
        T entity = supplier.get();
        return entity.fromLine(line);
    }
}
