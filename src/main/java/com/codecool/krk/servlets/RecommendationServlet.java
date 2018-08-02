package com.codecool.krk.servlets;

import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.User;
import com.codecool.krk.recommendations.DummyRecommendation;
import com.codecool.krk.recommendations.Recommendation;
import com.codecool.krk.repositories.Repository;
import com.codecool.krk.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RecommendationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            sendRecommendation(response, id);
        }
    }

    private void sendRecommendation(HttpServletResponse response, long id) throws IOException {
        Recommendation recomendator = new DummyRecommendation();
        Repository<User> userRepository = new UserRepository();
        User user = userRepository.get(id);
        Map<String, Double> recommendation =  recomendator.findRecommendation(user);
        response.getWriter().write(recommendation.toString());
    }
}
