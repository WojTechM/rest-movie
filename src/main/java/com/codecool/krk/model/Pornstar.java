package com.codecool.krk.model;


import com.codecool.krk.enums.ESex;
import com.google.gson.Gson;

import javax.persistence.*;

@Entity
public class Pornstar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String nickName;
    private long age;
    private long weight;
    private long height;

    @Enumerated
    private ESex sex;

    public Pornstar(String firstName, String lastName, String nickName, long age, long weight, long height, ESex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public ESex getSex() {
        return sex;
    }

    public void setSex(ESex sex) {
        this.sex = sex;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}