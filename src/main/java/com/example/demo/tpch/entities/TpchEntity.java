package com.example.demo.tpch.entities;

public sealed interface TpchEntity<T>
        permits Customer, LineItem, Nation, Order, Part, PartSupplier, Region, Supplier {

    Object getKey();
}
