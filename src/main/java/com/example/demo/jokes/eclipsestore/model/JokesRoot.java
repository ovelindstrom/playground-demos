package com.example.demo.jokes.eclipsestore.model;

import java.util.ArrayList;
import java.util.List;

public class JokesRoot
{
    private List<String> jokes = new ArrayList<>();

    public List<String> getJokes()
    {
        return jokes;
    }

    public void setJokes(List<String> jokes)
    {
        this.jokes = jokes;
    }
}
