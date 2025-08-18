package com.example.demo.jokes.eclipsestore.initializer;

import org.eclipse.serializer.reference.LazyReferenceManager;
import org.eclipse.serializer.reference.Lazy;
import org.eclipse.store.integrations.spring.boot.types.initializers.StorageContextInitializer;
import org.springframework.stereotype.Component;

@Component
public class StorageContextInitializerImpl implements StorageContextInitializer
{
    @Override
    public void initialize() {
        LazyReferenceManager.set(LazyReferenceManager.New(
                Lazy.Checker(
                        1_000_000, // timeout of lazy access
                        0.75                       // memory quota
                )));
    }

}
