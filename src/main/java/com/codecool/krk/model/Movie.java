package com.codecool.krk.model;

import com.codecool.krk.enums.ECategory;

import java.util.List;

public class Movie {

    private long id;
    private List<Pornstar> pornstars;
    private List<ECategory> categories;
    private float duration;
    private String imgUrl;


    public Movie(List<Pornstar> pornstars, List<ECategory> categories, float duration) {
        this.pornstars = pornstars;
        this.categories = categories;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public List<Pornstar> getPornstars() {
        return pornstars;
    }

    public void setPornstars(List<Pornstar> pornstars) {
        this.pornstars = pornstars;
    }

    public List<ECategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ECategory> categories) {
        this.categories = categories;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
