package com.example.demo.jokes.eclipsestore.service;
import org.eclipse.store.integrations.spring.boot.types.concurrent.Write;
import org.springframework.stereotype.Service;

import com.example.demo.jokes.eclipsestore.storage.JokesStorage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JokesServicesImpl implements JokesServices
{
    private final JokesStorage jokesStorage;


    @Override
    public String oneJoke(Integer id)
    {
        String joke;
        joke = jokesStorage.oneJoke(Objects.requireNonNullElse(id, 0));
        return joke;
    }

    public JokesServicesImpl(JokesStorage jokesStorage)
    {
        this.jokesStorage = jokesStorage;
    }

    @Override
    public List<String> allJokes()
    {
        return jokesStorage.allJokes();
    }

    @Override
    public Integer addNewJoke(String joke)
    {
        return jokesStorage.addNewJoke(joke);
    }

    @SuppressWarnings("resource")
    @Override
    public void loadPredefinedJokes()
    {
        List<String> jokes = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jokes.txt");
        assert inputStream != null;
        jokes = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());
        List<String> existingJokes = jokesStorage.allJokes();
        if (existingJokes.containsAll(jokes))
        {
            return;
        }
        jokesStorage.addJokes(jokes);
    }

    @Override
    @Write
    public Integer insertNewJoke(String joke)
    {
        List<String> jokes = jokesStorage.allJokes();
        jokes.add(joke);
        return jokesStorage.saveAllJokes(jokes);
    }

    @Override
    public List<String> searchJokes(String keyword) {
        return jokesStorage.allJokes().stream()
                .filter(joke -> joke.toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}