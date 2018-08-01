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

    private String title;
    private float duration;

    @ElementCollection
    @ManyToMany
    private List<Pornstar> pornstars;

    @ElementCollection
    @Enumerated
    private List<ECategory> categories;
    private String imgUrl;

    public Movie() {}

    public Movie(String title, float duration, List<Pornstar> pornstars, List<ECategory> categories) {
        this.title = title;
        this.duration = duration;
        this.pornstars = pornstars;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
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

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
