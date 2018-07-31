package com.codecool.krk.model;

import com.codecool.krk.enums.ECategory;
import com.google.gson.Gson;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private float duration;

    @ElementCollection
    @ManyToMany
    private List<Pornstar> pornstars;

    @ElementCollection
    @Enumerated
    private List<ECategory> categories;


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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
