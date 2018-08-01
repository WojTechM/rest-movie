package com.codecool.krk.model;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;

    @ElementCollection
    @OneToMany
    private List<View> views;

    @Transient
    private Map<Movie, Long> ratings = new HashMap<>();

    public User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.setRatings();
    }

    public User(String login, String password, List<View> views) {
        this.login = login;
        this.password = password;
        this.views = views;
        this.setRatings();
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

    public List<View> getViews() {
        return this.views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    public long getRating(Movie movie) {
        return this.ratings.get(movie);
    }

    private void setRatings() {
        for (View view : this.views) {
            this.ratings.put(view.getMovie(), view.getRating());
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
