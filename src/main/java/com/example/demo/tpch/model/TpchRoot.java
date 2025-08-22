package com.example.demo.tpch.model;

import java.util.HashMap;

import java.util.Map;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.PartSupplier;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.Supplier;

public class TpchRoot {
    // Base entities (no dependencies)
    private final Map<Integer, Region> regions;
    private final Map<Integer, Part> parts;

    // Level 1 dependencies (depend only on base entities)
    private final Map<Integer, Nation> nations; // depends on Regions

    // Level 2 dependencies
    private final Map<Integer, Customer> customers; // depends on Nations
    private final Map<Integer, Supplier> suppliers; // depends on Nations

    // Level 3 dependencies
    private final Map<Integer, PartSupplier> partSuppliers; // depends on Parts & Suppliers
    private final Map<Integer, Order> orders; // depends on Customers

    // Level 4 dependencies (highest level)
    private final Map<Integer, LineItem> lineItems; // depends on Orders & Suppliers

    public TpchRoot() {
        this.regions = new HashMap<>();
        this.parts = new HashMap<>();
        this.nations = new HashMap<>();
        this.customers = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.partSuppliers = new HashMap<>();
        this.orders = new HashMap<>();
        this.lineItems = new HashMap<>();
    }

    

    public void setRegions(Map<Integer, Region> regions){
        this.regions.clear();
        this.regions.putAll(regions);
    }

    public Map<Integer, Region> getRegions() {
        return regions;
    }

    public void setParts(Map<Integer, Part> parts){
        this.parts.clear();
        this.parts.putAll(parts);
    }

    public Map<Integer, Part> getParts() {
        return parts;
    }

    public void setNations(Map<Integer, Nation> nations) {
        this.nations.clear();
        this.nations.putAll(nations);
    }

    public Map<Integer, Nation> getNations() {
        return nations;
    }

    public void setCustomers(Map<Integer, Customer> customers) {
        this.customers.clear();
        this.customers.putAll(customers);
    }

    public Map<Integer, Customer> getCustomers() {
        return customers;
    }

    public void setSuppliers(Map<Integer, Supplier> suppliers) {
        this.suppliers.clear();
        this.suppliers.putAll(suppliers);
    }

    public Map<Integer, Supplier> getSuppliers() {
        return suppliers;
    }

    public void setPartSuppliers(Map<Integer, PartSupplier> partSuppliers) {
        this.partSuppliers.clear();
        this.partSuppliers.putAll(partSuppliers);
    }
    public Map<Integer, PartSupplier> getPartSuppliers() {
        return partSuppliers;
    }

    public void setOrders(Map<Integer, Order> orders) {
        this.orders.clear();
        this.orders.putAll(orders);
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void setLineItems(Map<Integer, LineItem> lineItems) {
        this.lineItems.clear();
        this.lineItems.putAll(lineItems);
    }

    public Map<Integer, LineItem> getLineItems() {
        return lineItems;
    }

}
