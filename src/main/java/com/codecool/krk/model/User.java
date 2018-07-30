package com.codecool.krk.model;

import java.util.Map;

public class User {
    private long id;
    private String login;
    private String password;
    private Map<Movie, Integer> ratings;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Movie, Integer> getRatings() {
        return this.ratings;
    }

    public void setRatings(Map<Movie, Integer> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Movie movie, Integer rating) {
        this.ratings.put(movie, rating);
    }

    public int getRating(Movie movie) {
        return this.ratings.get(movie);
    }
}
