package com.example.demo.jokes.eclipsestore.storage;

import org.eclipse.store.integrations.spring.boot.types.configuration.EclipseStoreProperties;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageFoundationFactory;
import org.eclipse.store.integrations.spring.boot.types.factories.EmbeddedStorageManagerFactory;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MultipleStorageConfiguration {

    private final EmbeddedStorageFoundationFactory foundationFactory;
    private final EmbeddedStorageManagerFactory  managerFactory;

    public MultipleStorageConfiguration(EmbeddedStorageFoundationFactory foundationFactory,
                                        EmbeddedStorageManagerFactory  managerFactory) {
        this.foundationFactory = foundationFactory;
        this.managerFactory = managerFactory;
    }  

    @Bean("jokes")
    @ConfigurationProperties("org.eclipse.store.jokes")
    EclipseStoreProperties jokesStoresProperties() {
        return new EclipseStoreProperties();
    }

    @Bean
    @Qualifier("jokes")
    EmbeddedStorageManager jokesStore(@Qualifier("jokes") final EclipseStoreProperties jokesStoresProperties) {
        return managerFactory.createStorage(
            foundationFactory.createStorageFoundation(jokesStoresProperties),
            jokesStoresProperties.isAutoStart()
        );
    }

}
