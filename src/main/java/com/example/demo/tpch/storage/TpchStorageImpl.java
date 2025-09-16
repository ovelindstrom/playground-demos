package com.example.demo.tpch.storage;

import java.util.List;
import java.util.Map;

import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.eclipse.store.storage.types.StorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.LineItem.Key;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.PartSupplier;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.Supplier;
import com.example.demo.tpch.model.TpchRoot;

@Service
public class TpchStorageImpl implements TpchStorage{

    private static final Logger logger = LoggerFactory.getLogger(TpchStorageImpl.class);

    @Autowired
    private StorageManager storageManager;
    @Autowired
    private TpchRoot root;


   
    @Override
    @Write
    public void putRegions(Map<Long, Region> regions) {
        logger.info("Storing regions to root: {}", regions.size());
        root.setRegions(regions);

    }



    @Override
    @Read
    public Map<Long, Region> getRegions() {
        return root.getRegions();
    }



    @Override
    @Write
    public void putParts(Map<Long, Part> parts) {
        logger.info("Storing parts to root: {}", parts.size());
        root.setParts(parts);
    }



    @Override
    @Read
    public Map<Long, Part> getParts() {
        return root.getParts();
    }



    @Override
    @Write
    public void putNations(Map<Long, Nation> nations) {
        logger.info("Storing nations to root: {}", nations.size());
        root.setNations(nations);
    }



    @Override
    @Read
    public Map<Long, Nation> getNations() {
        return root.getNations();
    }



    @Override
    @Write
    public void putCustomers(Map<Long, Customer> customers) {
        logger.info("Storing customers to root: {}", customers.size());
        root.setCustomers(customers);
    }



    @Override
    @Read
    public Map<Long, Customer> getCustomers() {
        return root.getCustomers();
    }



    @Override
    @Write
    public void putSuppliers(Map<Long, Supplier> suppliers) {
        logger.info("Storing suppliers to root: {}", suppliers.size());
        root.setSuppliers(suppliers);
    }



    @Override
    @Read
    public Map<Long, Supplier> getSuppliers() {
        return root.getSuppliers();
    }



    @Override
    @Write
    public void putPartSuppliers(List<PartSupplier> partSuppliers) {
        logger.info("Storing part suppliers to root: {}", partSuppliers.size());
        root.setPartSuppliers(partSuppliers);
        long items = storageManager.storeRoot();
        logger.info("Stored part suppliers: {}", items);
    }



    @Override
    @Read
    public List<PartSupplier> getPartSuppliers() {
        return root.getPartSuppliers();
    }



    @Override
    @Write
    public void putOrders(Map<Long, Order> orders) {
        logger.info("Storing orders to root: {}", orders.size());
        root.setOrders(orders);
        long items = storageManager.storeRoot();
        logger.info("Stored orders: {}", items);
    }



    @Override
    @Read
    public Map<Long, Order> getOrders() {
        return root.getOrders();
    }



    @Override
    @Write
    public void putLineItems(Map<Key, LineItem> lineItems) {
        logger.info("Storing line items to root: {}", lineItems.size());
        root.setLineItems(lineItems);
        long items = storageManager.storeRoot();
        logger.info("Stored line items: {}", items);
    }



    @Override
    @Read
    public Map<Key, LineItem> getLineItems() {
        return root.getLineItems();
    }



    @Override
    public void addLineItem(LineItem lineItem) {
        root.addLineItem(lineItem);
        storageManager.storeRoot();
    }

    

}
