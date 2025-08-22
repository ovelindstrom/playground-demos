package com.example.demo.tpch.entities;

import java.util.Map;
import java.util.function.Supplier;

public record TpchEntityCreationContext(Supplier<? extends TpchEntity<?>> supplier, Map<String, Map<Integer, ? extends TpchEntity<?>>> maps) {
}
