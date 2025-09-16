package com.example.demo.tpch.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.tpch.entities.Customer;
import com.example.demo.tpch.entities.LineItem;
import com.example.demo.tpch.entities.Nation;
import com.example.demo.tpch.entities.Order;
import com.example.demo.tpch.entities.Part;
import com.example.demo.tpch.entities.PartSupplier;
import com.example.demo.tpch.entities.Region;
import com.example.demo.tpch.entities.TpchEntity;
import com.example.demo.tpch.entities.TpchEntityFactory;
import com.example.demo.tpch.storage.TpchStorage;

public class TplLoader {

        private static final Logger logger = LoggerFactory.getLogger(TplLoader.class);

        private TpchStorage tpchStorage;

        public TplLoader(TpchStorage tpchStorage) {
                this.tpchStorage = tpchStorage;
        }

        // This metod can return any entity type as a map
        static <T extends TpchEntity<T>> Map<Long, T> loadAsMap(String filePath, TpchEntityFactory.EntityType type) {

                return load(filePath, type)
                                .stream()
                                .collect(Collectors.toMap(
                                                t -> (Long) t.getKey(),
                                                t -> (T) t));
        }

        static <T extends TpchEntity<T>> List<T> load(String filePath, TpchEntityFactory.EntityType type) {
                List<T> entities = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                                T entity = (T) TpchEntityFactory.fromLine(type, line);
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
        private String tpchTplDataDirectory = "/Users/ovelindstrom/Projects/playground-demos/src/main/resources/tpch-data/";

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
                logger.info("Starting TPL data loading from directory: " + tpchTplDataDirectory);
                //loadRegions();
                //loadParts();
                //loadNations(); // after regions
                //loadCustomers(); // after nations
                //loadSuppliers(); // after nations
                //loadPartSuppliers(); // after parts and suppliers
                //loadOrders(); // after customers
                loadLineItems(); // after orders and suppliers

        }

        private void loadRegions() {
                // Load Regions
                logger.info("Loading regions from: " + tpchTplDataDirectory + "region.tbl");

                regions = TplLoader.loadAsMap(tpchTplDataDirectory + "region.tbl",
                                TpchEntityFactory.EntityType.REGION);

                // Log that is is loaded and how many
                logger.info("Loaded " + regions.size() + " regions.");
                logger.info("Finished loading regions.");
                // Update root
                tpchStorage.putRegions(regions);

        }

        private void loadParts() {
                // Load part data
                logger.info("Loading parts from: " + tpchTplDataDirectory + "part.tbl");
                parts = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "part.tbl",
                                TpchEntityFactory.EntityType.PART);
                logger.info("Loaded " + parts.size() + " parts.");
                logger.info("Finished loading parts.");
                // Update root
                tpchStorage.putParts(parts);
        }

        private void loadNations() {
                // Load nation data (with region references)
                logger.info("Loading nations from: " + tpchTplDataDirectory + "nation.tbl");
                nations = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "nation.tbl",
                                TpchEntityFactory.EntityType.NATION);
                logger.info("Loaded " + nations.size() + " nations.");
                logger.info("Finished loading nations.");
                // Update root
                tpchStorage.putNations(nations);
        }

        private void loadCustomers() {
                // Load customer data (with nation references)
                logger.info("Loading customers from: " + tpchTplDataDirectory + "customer.tbl");
                customers = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "customer.tbl",
                                TpchEntityFactory.EntityType.CUSTOMER);
                logger.info("Loaded " + customers.size() + " customers.");
                logger.info("Finished loading customers.");
                // Update root
                tpchStorage.putCustomers(customers);
        }

        private void loadSuppliers() {
                // Load supplier data (with nation references)
                logger.info("Loading suppliers from: " + tpchTplDataDirectory + "supplier.tbl");
                suppliers = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "supplier.tbl",
                                TpchEntityFactory.EntityType.SUPPLIER);
                logger.info("Loaded " + suppliers.size() + " suppliers.");
                logger.info("Finished loading suppliers.");
                // Update root
                tpchStorage.putSuppliers(suppliers);
        }

        private void loadPartSuppliers() {
                // Load part-supplier relationships
                logger.info("Loading part-suppliers from: " + tpchTplDataDirectory + "partsupp.tbl");

                partSuppliers = TplLoader.load(
                                tpchTplDataDirectory + "partsupp.tbl",
                                TpchEntityFactory.EntityType.PART_SUPPLIER);

                logger.info("Loaded " + partSuppliers.size() + " part-suppliers.");
                logger.info("Finished loading part-suppliers.");

                // Update root
                tpchStorage.putPartSuppliers(partSuppliers);
        }

        private void loadOrders() {
                // Load order data (with customer references)
                logger.info("Loading orders from: " + tpchTplDataDirectory + "orders.tbl");
                orders = TplLoader.loadAsMap(
                                tpchTplDataDirectory + "orders.tbl",
                                TpchEntityFactory.EntityType.ORDER);
                logger.info("Loaded " + orders.size() + " orders.");
                logger.info("Finished loading orders.");
                // Update root
                tpchStorage.putOrders(orders);
        }

        private void loadLineItems() {
                // Load line item data (with order and supplier references)
                logger.info("Loading line items from: " + tpchTplDataDirectory + "lineitem.tbl");
                List<LineItem> lineItemsList = TplLoader.load(
                                tpchTplDataDirectory + "lineitem.tbl",
                                TpchEntityFactory.EntityType.LINE_ITEM);

                int batchSize = 10000;
                List<LineItem> batch = new ArrayList<>(batchSize);
                int count = 1;
                for (LineItem li : lineItemsList) {
                        batch.add(li);
                        if (batch.size() == batchSize) {
                                logger.info("Saving LineItem batch " + count);

                                Map<LineItem.Key, LineItem> batchMap = batch.stream()
                                                .collect(Collectors.toMap(l -> (LineItem.Key) l.getKey(),
                                                                Function.identity()));
                                tpchStorage.putLineItems(batchMap);
                                batch.clear();
                                count++;
                        }
                        
                }
                if (!batch.isEmpty()) {
                        logger.info("Saving final LineItem batch ");
                        Map<LineItem.Key, LineItem> batchMap = batch.stream()
                                        .collect(Collectors.toMap(l -> (LineItem.Key) l.getKey(), Function.identity()));
                        tpchStorage.putLineItems(batchMap);
                }

                logger.info("Loaded " + lineItems.size() + " line items.");
                logger.info("Finished loading line items.");
                lineItemsList.clear();
                lineItemsList = null;
                System.gc();

        }

}
