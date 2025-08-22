package com.example.demo.tpch.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.PartSupplier;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.TpchEntity;
import com.example.demo.tpch.entities.TpchEntityFactory;

public class TplLoader {

        static <T extends TpchEntity<T>> Map<Long, T> loadAsMap(String filePath,
                        Supplier<T> supplier, Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {

                return load(filePath, supplier, maps).stream()
                                .collect(Collectors.toMap(t -> (Long) t.getKey(), Function.identity()));
        }

        
        static <T extends TpchEntity<T>> List<T> load(String filePath, Supplier<T> supplier) {
                return load(filePath, supplier, null);
        }

        static <T extends TpchEntity<T>> List<T> load(String filePath, Supplier<T> supplier,
                        Map<String, Map<Long, ? extends TpchEntity<?>>> maps) {
                List<T> entities = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                                T entity = TpchEntityFactory.fromLine(line, supplier, maps);
                                entities.add(entity);
                        }
                } catch (IOException e) {
                        // Log the exception and return an empty list
                        System.err.println("Error loading file: " + filePath);
                        e.printStackTrace();
                }
                return entities;
        }

        @org.springframework.beans.factory.annotation.Value("${tpch.tpl.data-directory}")
        private String tpchTplDataDirectory = "C:\\Users\\ove.lindstrom\\Projects\\Playground\\demo\\src\\main\\resources\\tpch-data\\";

        // Maps
        Map<Long, Region> regions = new HashMap<>();
        Map<Long, Nation> nations = new HashMap<>();
        Map<Long, Customer> customers = new HashMap<>();
        Map<Long, com.example.demo.tpch.entities.Supplier> suppliers = new HashMap<>();
        Map<Long, Part> parts = new HashMap<>();
        List<PartSupplier> partSuppliers = new ArrayList<>();
        Map<Long, Order> orders = new HashMap<>();
        Map<LineItem.Key, LineItem> lineItems = new HashMap<>();

        // Convenience methods for data loading (respecting dependency order)
        public void loadData() {
                // Load in dependency order
                /*
                 * Load order:
                 * Region - from region.tbl
                 * Nation - from nation.tbl
                 * * Depends on Region
                 * Customer - from customer.tbl
                 * * Depends on Nation
                 * Supplier - from supplier.tbl
                 * Part - from part.tbl
                 * PartSupplier - from partsupp.tbl
                 * Order - from orders.tbl
                 * LineItem - from lineitem.tbl
                 */
                loadRegions();
                loadParts();
                loadNations(); // after regions
                loadCustomers(); // after nations
                loadSuppliers(); // after nations
                loadPartSuppliers(); // after parts and suppliers
                loadOrders(); // after customers
                loadLineItems(); // after orders and suppliers
        }

        private void loadRegions() {
                // Load Regions

                regions = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "region.tbl",
                                Region::new, null);

        }

        private void loadParts() {
                // Load part data
                parts = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "part.tbl",
                                Part::new, null);
        }

        private void loadNations() {
                // Load nation data (with region references)
                nations = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "nation.tbl",
                                Nation::new, Map.of("regions", regions));
        }

        private void loadCustomers() {
                // Load customer data (with nation references)
                customers = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "customer.tbl",
                                Customer::new, Map.of("nations", nations));
        }

        private void loadSuppliers() {
                // Load supplier data (with nation references)
                suppliers = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "supplier.tbl",
                                com.example.demo.tpch.entities.Supplier::new, Map.of("nations", nations));
        }

        private void loadPartSuppliers() {
                // Load part-supplier relationships
                partSuppliers = TplLoader.load(
                                tpchTplDataDirectory + "partsupp.tbl",
                                PartSupplier::new, Map.of("parts", parts, "suppliers", suppliers));
        }

        private void loadOrders() {
                // Load order data (with customer references)
                orders = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "orders.tbl",
                                Order::new, Map.of("customers", customers));
        }

        private void loadLineItems() {
                // Load line item data (with order and supplier references)
                lineItems = TplLoader.load(
                                tpchTplDataDirectory + "lineitem.tbl",
                                LineItem::new, Map.of("orders", orders, "suppliers", suppliers, "parts", parts))
                                .stream()
                                .collect(Collectors.toMap(li -> (LineItem.Key) li.getKey(), Function.identity()));
        }

        public static void main(String[] args) {
                TplLoader loader = new TplLoader();
                loader.loadData();

                // Print out the current memory
                Runtime runtime = Runtime.getRuntime();
                System.out.println("Used memory: " + (runtime.totalMemory() - runtime.freeMemory()) / 1024 + " KB");
                System.out.println("Total memory: " + runtime.totalMemory() / 1024 + " KB");
                System.out.println("Max memory: " + runtime.maxMemory() / 1024 + " KB");

        }

}
