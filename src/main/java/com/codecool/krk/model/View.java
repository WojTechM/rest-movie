package com.codecool.krk.model;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Movie movie;
    private long rating;

    public View() {}

    public View(Movie movie, long rating) {
        this.movie = movie;
        this.rating = rating;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
