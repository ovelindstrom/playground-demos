package com.example.demo.tpch.storage;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageFoundationFactory;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageManagerFactory;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.demo.tpch.model.TpchRoot;

import jakarta.annotation.PreDestroy;

@Configuration
public class EclipseStoreConfig {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EclipseStoreConfig.class);

    private StorageManager storageManager;
    private TpchRoot tpchRoot;

    private final EmbeddedStorageFoundationFactory foundationFactory;
    private final EmbeddedStorageManagerFactory managerFactory;

    public EclipseStoreConfig(EmbeddedStorageFoundationFactory foundationFactory,
            EmbeddedStorageManagerFactory managerFactory) {
        this.foundationFactory = foundationFactory;
        this.managerFactory = managerFactory;
    }

    @Bean("tpch_config")
    @ConfigurationProperties("org.eclipse.store.tpch")
    EclipseStoreProperties tpchStoresProperties() {
        return new EclipseStoreProperties();
    }

    @Bean
    @Primary
    @Qualifier("tpch_storage")
    EmbeddedStorageManager tpchStore(@Qualifier("tpch_config") final EclipseStoreProperties tpchStoresProperties) {
        EmbeddedStorageManager storage = managerFactory.createStorage(
                foundationFactory.createStorageFoundation(tpchStoresProperties),
                tpchStoresProperties.isAutoStart());

        // Create and load the root object
        if (storage.root() == null) {
            logger.info("### Create a new instance of TpchRoot and store it");
            tpchRoot = new TpchRoot();
            storage.setRoot(tpchRoot);
            storage.storeRoot();
        } else {
            // Load the existing root
            tpchRoot = (TpchRoot) storage.root();
            tpchRoot.getRegions().size(); // Force loading the root
            logger.info("### TpchRoot already exists in storage");
        }
        tpchRoot.setStorageManager(storage);

        return storage;
    }

    @Bean
    @Primary
    public TpchRoot tpchRoot() {
        return tpchRoot;
    }


    @PreDestroy
    public void cleanup() {
        if (storageManager != null) {
            storageManager.shutdown();
        }
    }

}
