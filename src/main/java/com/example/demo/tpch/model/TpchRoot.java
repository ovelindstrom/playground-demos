package com.example.demo.tpch.model;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.tpch.Customer;
import com.example.demo.tpch.LineItem;
import com.example.demo.tpch.Nation;
import com.example.demo.tpch.Order;
import com.example.demo.tpch.Part;
import com.example.demo.tpch.PartSupplier;
import com.example.demo.tpch.Region;
import com.example.demo.tpch.Supplier;

public class TpchRoot {
    private final List<Customer> customers = new ArrayList<>();
    private final List<LineItem> lineItems = new ArrayList<>();
    private final List<Nation> nations = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private final List<Part> parts = new ArrayList<>();
    private final List<PartSupplier> partSuppliers = new ArrayList<>();
    private final List<Region> regions = new ArrayList<>();
    private final List<Supplier> suppliers = new ArrayList<>();

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    public List<Nation> getNations() {
        return this.nations;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public List<Part> getParts() {
        return this.parts;
    }

    public List<PartSupplier> getPartSuppliers() {
        return this.partSuppliers;
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    public List<Supplier> getSuppliers() {
        return this.suppliers;
    }
}
