package com.codecool.krk.model;

public class View {

    private long userId;
    private long movieId;
    private long rating;

    public View(long userId, long movieId, long rating) {
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMovieId() {
        return movieId;
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
}
