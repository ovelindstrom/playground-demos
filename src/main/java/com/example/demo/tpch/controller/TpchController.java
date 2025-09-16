package com.example.demo.tpch.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.MediaType;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.Supplier;
import com.example.demo.tpch.loader.TplLoader;
import com.example.demo.tpch.model.TpchRoot;
import com.example.demo.tpch.storage.TpchStorage;

@RestController
@RequestMapping("/tpch")
public class TpchController {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TpchController.class);

    @Autowired
    private TpchStorage tpchStorage;

    @Autowired
    private TpchRoot tpchRoot;

    @GetMapping("/init")
    public String init() {
        logger.info("### Starting TPL data loading...");
        TplLoader loader = new TplLoader(tpchStorage);
        loader.loadData();

        // Print out the current memory
        Runtime runtime = Runtime.getRuntime();
        logger.info("Used memory: " + (runtime.totalMemory() - runtime.freeMemory()) / 1024 + " KB");
        logger.info("Total memory: " + runtime.totalMemory() / 1024 + " KB");
        logger.info("Max memory: " + runtime.maxMemory() / 1024 + " KB");

        return "Initialization complete, rooted {} times.";
    }

    @GetMapping(value = "/regions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Region> getRegions() {
        var regions = tpchStorage.getRegions();
        logger.info("Returning {} regions", regions.size());
        return regions;
    }

    // Make GET endpoints for other entities as needed
    @GetMapping(value = "/parts", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Part> getParts() {
        var parts = tpchStorage.getParts();
        logger.info("Returning {} parts", parts.size());
        return parts;
    }

    @GetMapping(value = "/nations", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Nation> getNations() {
        var nations = tpchStorage.getNations();
        logger.info("Returning {} nations", nations.size());
        return nations;
    }

    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Customer> getCustomers() {
        var customers = tpchStorage.getCustomers();
        logger.info("Returning {} customers", customers.size());
        return customers;
    }

    // Get Customer By Number
    @GetMapping(value = "/customers/{customerNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomerByNumber(@PathVariable Long customerNumber) {
        var customer = tpchStorage.getCustomers().get(customerNumber);
        logger.info("Returning customer with number {}: {}", customerNumber, customer);
        return customer;
    }

    @GetMapping(value = "/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Supplier> getSuppliers() {
        var suppliers = tpchStorage.getSuppliers();
        logger.info("Returning {} suppliers", suppliers.size());
        return suppliers;
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Order> getOrders() {
        var orders = tpchStorage.getOrders();
        logger.info("Returning {} orders", orders.size());
        return orders;
    }

    @GetMapping(value = "/lineitems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<LineItem.Key, LineItem> getLineItems() {
        var lineItems = tpchStorage.getLineItems();
        logger.info("Returning {} line items", lineItems.size());
        return lineItems;
    }

    // Get total count or items
    @GetMapping(value = "/counts", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCounts() {
        return String.format(
                "Regions: %d, Parts: %d, Nations: %d, Customers: %d, Suppliers: %d, Orders: %d, LineItems: %d, [Total: %d]",
                tpchStorage.getRegions().size(),
                tpchStorage.getParts().size(),
                tpchStorage.getNations().size(),
                tpchStorage.getCustomers().size(),
                tpchStorage.getSuppliers().size(),
                tpchStorage.getOrders().size(),
                tpchStorage.getLineItems().size(),
                tpchRoot.countAll());

    }

}
