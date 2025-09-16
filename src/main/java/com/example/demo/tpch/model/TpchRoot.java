package com.example.demo.tpch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.store.storage.types.StorageManager;
import org.springframework.stereotype.Component;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.PartSupplier;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.Supplier;

@Component
public class TpchRoot implements Serializable {
    private static final long serialVersionUID = 20250910002L;

    // Transient reference to Storage Manager for explicit storing
    private transient StorageManager storageManager;


    // Base entities (no dependencies)
    private final Map<Long, Region> regions;
    private final Map<Long, Part> parts;

    // Level 1 dependencies (depend only on base entities)
    private final Map<Long, Nation> nations; // depends on Regions

    // Level 2 dependencies
    private final Map<Long, Customer> customers; // depends on Nations
    private final Map<Long, Supplier> suppliers; // depends on Nations

    // Level 3 dependencies
    private List<PartSupplier> partSuppliers; // depends on Parts & Suppliers
    private final Map<Long, Order> orders; // depends on Customers

    // Level 4 dependencies (highest level)
    private Map<LineItem.Key, LineItem> lineItems; // depends on Orders & Suppliers

    public TpchRoot() {
        this.regions = new HashMap<>();
        this.parts = new HashMap<>();
        this.nations = new HashMap<>();
        this.customers = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.partSuppliers = new ArrayList<>();
        this.orders = new HashMap<>();
        this.lineItems = new HashMap<>();
    }

      // Inject the storage manager after construction
    public void setStorageManager(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    // Helper method to store changes
    private void storeIfManaged(Object object) {
        if (storageManager != null) {
            storageManager.store(object);
        }
    }

    

    public void setRegions(Map<Long, Region> regions){
        this.regions.clear();
        this.regions.putAll(regions);
        storeIfManaged(this.regions);
    }

    public Map<Long, Region> getRegions() {
        return regions;
    }

    public void setParts(Map<Long, Part> parts){
        this.parts.clear();
        this.parts.putAll(parts);
        storeIfManaged(this.parts);

    }

    public Map<Long, Part> getParts() {
        return parts;
    }

    public void setNations(Map<Long, Nation> nations) {
        this.nations.clear();
        this.nations.putAll(nations);
        storeIfManaged(this.nations);
    }

    public Map<Long, Nation> getNations() {
        return nations;
    }

    public void setCustomers(Map<Long, Customer> customers) {
        this.customers.clear();
        this.customers.putAll(customers);
        storeIfManaged(this.customers);
    }

    public Map<Long, Customer> getCustomers() {
        return customers;
    }

    public void setSuppliers(Map<Long, Supplier> suppliers) {
        this.suppliers.clear();
        this.suppliers.putAll(suppliers);
        storeIfManaged(this.suppliers);
    }

    public Map<Long, Supplier> getSuppliers() {
        return suppliers;
    }

    public void setPartSuppliers(List<PartSupplier> partSuppliers) {
        this.partSuppliers.clear();
        this.partSuppliers.addAll(partSuppliers);
        storeIfManaged(this.partSuppliers);
    }

    public List<PartSupplier> getPartSuppliers() {
        return partSuppliers;
    }

    public void setOrders(Map<Long, Order> orders) {
        this.orders.clear();
        this.orders.putAll(orders);
        storeIfManaged(this.orders);
    }

    public Map<Long, Order> getOrders() {
        return orders;
    }

    public void setLineItems(Map<LineItem.Key, LineItem> lineItems) {
        this.lineItems.putAll(lineItems);
        storeIfManaged(this.lineItems);
    }

      public void addLineItem(LineItem lineItem) {
        lineItems.put(lineItem.getKey(), lineItem);
        storeIfManaged(this.lineItems);
    }

    public Map<LineItem.Key, LineItem> getLineItems() {
        return lineItems;
    }



    public Integer countAll() {
        return regions.size() + parts.size() + nations.size() + customers.size() + suppliers.size()
                + partSuppliers.size() + orders.size() + lineItems.size();
     }

     // Method to force store all collections (useful after bulk operations)
    public void storeAll() {
        if (storageManager != null) {
            storageManager.store(this);
            storageManager.store(regions);
            storageManager.store(parts);
            storageManager.store(nations);
            storageManager.store(customers);
            storageManager.store(suppliers);
            storageManager.store(partSuppliers);
            storageManager.store(orders);
            storageManager.store(lineItems);
        }
    }

  
}
