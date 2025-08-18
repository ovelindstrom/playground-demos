package com.example.demo.jokes.eclipsestore.storage;

import org.eclipse.serializer.persistence.types.Storer;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Read;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.demo.jokes.eclipsestore.model.JokesRoot;

import java.util.ArrayList;
import java.util.List;


@Component
public class JokesStorageImpl implements JokesStorage
{
    private final EmbeddedStorageManager storageManager;

    public JokesStorageImpl(@Qualifier("jokes") EmbeddedStorageManager storageManager)
    {
        this.storageManager = storageManager;
    }


    @Override
    @Read
    public String oneJoke(Integer id)
    {
        String joke;
        JokesRoot root = (JokesRoot) storageManager.root();
        if (id > root.getJokes().size())
        {
            throw new IllegalArgumentException("No jokes with this id");
        }
        joke = root.getJokes().get(id);
        return joke;
    }

    @Override
    @Read
    public List<String> allJokes()
    {
        JokesRoot root = (JokesRoot) storageManager.root();
        return new ArrayList<>(root.getJokes()); // Create new List... never return original one.
    }

    @Override
    @Write
    public Integer addNewJoke(String joke)
    {
        JokesRoot root = (JokesRoot) storageManager.root();
        root.getJokes().add(joke);
        storageManager.store(root.getJokes());
        return root.getJokes().size();
    }

    @Override
    @Write
    public void addJokes(List<String> jokes)
    {
        JokesRoot root = (JokesRoot) storageManager.root();
        root.getJokes().addAll(jokes);
        storageManager.store(root.getJokes());
    }

    @Override
    @Write
    public Integer saveAllJokes(List<String> jokes)
    {
        JokesRoot root = (JokesRoot) storageManager.root();
        root.setJokes(jokes);
        Storer eagerStorer = storageManager.createEagerStorer();
        eagerStorer.store(root);
        eagerStorer.commit();
        return root.getJokes().size();
    }
}