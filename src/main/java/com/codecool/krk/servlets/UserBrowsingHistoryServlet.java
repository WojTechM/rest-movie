package com.codecool.krk.servlets;

import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.User;
import com.codecool.krk.model.View;
import com.codecool.krk.repositories.MovieRepository;
import com.codecool.krk.repositories.Repository;
import com.codecool.krk.repositories.UserRepository;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserBrowsingHistoryServlet extends HttpServlet {
    private final int MOVIES_TO_LOAD = 5;
    private Repository<Movie> movieRepository = new MovieRepository();
    private Repository<User> userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            sendHistory(response, id);
        }
    }

    private void sendHistory(HttpServletResponse response, long id) throws IOException {
        User user = userRepository.get(id);
        List<View> views = user.getViews();
        List<String> movieJsons = new ArrayList<>();
        for (int i = 0; i < views.size() && i < MOVIES_TO_LOAD; i++) {
            movieJsons.add(movieRepository.get(views.get(i).getMovieId()).toJson());
        }
        response.getWriter().write(new Gson().toJson(movieJsons));
    }
}
