package com.codecool.krk.recommendations;

import com.codecool.krk.model.User;

import java.util.List;

public interface Recommendation {
    List<String> findRecommendation(User user);
}
