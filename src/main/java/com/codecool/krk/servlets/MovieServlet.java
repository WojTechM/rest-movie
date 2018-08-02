package com.codecool.krk.servlets;

import com.codecool.krk.helpers.URIparser;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.repositories.MovieRepository;
import com.codecool.krk.repositories.Repository;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MovieServlet extends HttpServlet {
    private Repository<Movie> movieRepository = new MovieRepository();
    private Repository<Pornstar> pornstarRepository = new Repository<>(Pornstar.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            sendSingleJson(response, id);
        } else {
            sendAll(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Movie movie = loadMovieFromRequest(request);
        for (Pornstar ps : movie.getPornstars()) {
            if (pornstarRepository.get(ps.getId()) == null) {
                pornstarRepository.add(ps);
            } else {
                pornstarRepository.update(ps);
            }
        }
        movieRepository.add(movie);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Movie movie = loadMovieFromRequest(request);
        movieRepository.update(movie);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean uriHasIdentifier = URIparser.hasIdentifier(request.getRequestURI());

        Movie movie;
        if (uriHasIdentifier) {
            long id = Long.valueOf(URIparser.parseIdentifier(request.getRequestURI()));
            movie = movieRepository.get(id);
        } else {
            movie = loadMovieFromRequest(request);
        }

        movieRepository.delete(movie);
    }

    private void sendSingleJson(HttpServletResponse response, long id) throws IOException {
        String movieJson;
        try {
            movieJson = movieRepository.get(id).toJson();
        } catch (NullPointerException e) {
            movieJson = "Entity not found";
        }
        response.getWriter().write(movieJson);
    }

    private void sendAll(HttpServletResponse response) throws IOException {
        List<Movie> movies = movieRepository.getAll();

        Gson gson = new Gson();
        String json = gson.toJson(movies);
        response.getWriter().write(json);
    }

    private Movie loadMovieFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        request.getReader().lines().forEach(jsonLine -> jsonBuilder.append(jsonLine));
        return new Gson().fromJson(jsonBuilder.toString(), Movie.class);
    }
}
