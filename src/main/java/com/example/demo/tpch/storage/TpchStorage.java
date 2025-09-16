package com.example.demo.tpch.storage;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.PartSupplier;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.Supplier;
import java.util.Map;
import java.util.List;

public interface TpchStorage {


    // Regions
    void putRegions(Map<Long, Region> regions);
    Map<Long, Region> getRegions();

    // Parts
    void putParts(Map<Long, Part> parts);
    Map<Long, Part> getParts();

    // Nations
    void putNations(Map<Long, Nation> nations);
    Map<Long, Nation> getNations();

    // Customers
    void putCustomers(Map<Long, Customer> customers);
    Map<Long, Customer> getCustomers();

    // Suppliers
    void putSuppliers(Map<Long, Supplier> suppliers);
    Map<Long, Supplier> getSuppliers();

    // PartSuppliers
    void putPartSuppliers(List<PartSupplier> partSuppliers);
    List<PartSupplier> getPartSuppliers();

    // Orders
    void putOrders(Map<Long, Order> orders);
    Map<Long, Order> getOrders();

    // LineItems
    void putLineItems(Map<LineItem.Key, LineItem> lineItems);
    void addLineItem(LineItem lineItem);
    Map<LineItem.Key, LineItem> getLineItems();

}
