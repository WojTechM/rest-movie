package com.codecool.krk.recommendations;

import com.codecool.krk.model.User;

import java.util.Map;

public interface Recommendation {
    Map<String, Double> findRecommendation(User user);
}
