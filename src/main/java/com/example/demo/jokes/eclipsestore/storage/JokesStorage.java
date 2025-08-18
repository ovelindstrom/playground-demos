package com.example.demo.jokes.eclipsestore.storage;

import java.util.List;

public interface JokesStorage {
    /**
     * Returns a joke with the specified ID.
     *
     * @param id The ID of the joke.
     * @return The joke with the specified ID.
     */
    String oneJoke(Integer id);

    /**
     * Returns all jokes in the storage.
     *
     * @return A list of all jokes.
     */
    List<String> allJokes();

    /**
     * Adds a new joke to the storage.
     *
     * @param joke The joke to add.
     * @return The ID of the added joke.
     */
    Integer addNewJoke(String joke);

    /**
     * Adds multiple jokes to the storage.
     *
     * @param jokes The jokes to add.
     */
    void addJokes(List<String> jokes);

    /**
     * Saves all jokes to the storage.
     *
     * @param jokes The jokes to save.
     * @return The number of jokes saved.
     */
    Integer saveAllJokes(List<String> jokes);
}
