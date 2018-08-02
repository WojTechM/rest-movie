package com.codecool.krk.recommendations;

import com.codecool.krk.enums.ECategory;
import com.codecool.krk.model.Movie;
import com.codecool.krk.model.Pornstar;
import com.codecool.krk.model.User;
import com.codecool.krk.model.View;
import com.codecool.krk.repositories.MovieRepository;
import com.codecool.krk.repositories.Repository;

import java.util.*;

public class DummyRecommendation implements Recommendation {
    private Set<Pornstar> favoritePornstars = new HashSet<>();
    private Set<ECategory> favoriteCategory = new HashSet<>();

    @Override
    public List<String> findRecommendation(User user) {
        List<String> recommendations = new ArrayList<>();
        List<Movie> moviesDatabase = getMoviesData();
        getFavorites(user);

        for(Movie movie : moviesDatabase) {
            double compatibility = calculateCompatibilityPercentage(movie);
            if (compatibility >= 50 && !(user.sawMovie(movie))) {
                recommendations.add(movie.toJson());
            }
        }

        return recommendations;
    }

    private void getFavorites(User user) {
        Repository<Movie> movieRepository = new MovieRepository();
        List<View> userViews = user.getViews();

        for(View view : userViews) {
            long movieId = view.getMovieId();
            long movieRating = view.getRating();

            if(movieRating > 7) {
                Movie movie = movieRepository.get(movieId);
                favoriteCategory.addAll(movie.getCategories());
                favoritePornstars.addAll(movie.getPornstars());
            }
        }
    }

    private List<Movie> getMoviesData() {
        Repository<Movie> movieRepository = new MovieRepository();
        return movieRepository.getAll();
    }

    private double calculateCompatibilityPercentage(Movie movie) {
        int count = 0;
        int sum = favoriteCategory.size() + favoritePornstars.size();

        for(Pornstar pornstar : movie.getPornstars()) {
            if(favoritePornstars.contains(pornstar)) {
                count++;
            }
        }

        for(ECategory category : movie.getCategories()) {
            if(favoriteCategory.contains(category)) {
                count++;
            }
        }

        return count*100.0/sum;
    }
}
