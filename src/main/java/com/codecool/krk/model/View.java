package com.codecool.krk.model;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long movieId;
    private long rating;

    public View() {

    }

    public View(long movieId, long rating) {
        this.movieId = movieId;
        this.rating = rating;
    }

    public long getMovieId() {
        return this.movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}