package com.example.demo.jokes.eclipsestore.service;

import java.util.List;

public interface JokesServices {
    /**
     * Returns a joke with the specified ID.
     *
     * @param id The ID of the joke.
     * @return The joke with the specified ID.
     */
    String oneJoke(Integer id);

    /**
     * Returns all jokes.
     *
     * @return A list of all jokes.
     */
    List<String> allJokes();

    /**
     * Adds a new joke.
     *
     * @param joke The joke to add.
     * @return The ID of the added joke.
     */
    Integer addNewJoke(String joke);

    /**
     * Loads predefined jokes.
     */
    void loadPredefinedJokes();

    /**
     * Inserts a new joke.
     *
     * @param joke The joke to insert.
     * @return The ID of the inserted joke.
     */
    Integer insertNewJoke(String joke);

    /**
     * Search for jokes containing a specific keyword.
     * @param keyword The keyword to search for in jokes.
     * @return A list of jokes containing the specified keyword.
    */
    List<String> searchJokes(String keyword);
}
