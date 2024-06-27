package cloud.moviesmicroservice.services;

import cloud.moviesmicroservice.boundaries.MovieBoundary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoviesServiceImplantation implements MoviesService{

    private MoviesCrud movies;

    public MoviesServiceImplantation(MoviesCrud movies) {
        this.movies = movies;
    }

    @Override
    public Mono<MovieBoundary> createMovie(MovieBoundary movie) {
        return null;
    }

    @Override
    public Flux<MovieBoundary> getAllMovies() {
        return null;
    }

    @Override
    public Flux<MovieBoundary> getMoviesByCriteria(String criteria, String value) {
        return null;
    }

    @Override
    public Mono<Void> updateMovie(String id, String email, MovieBoundary movie) {
        return null;
    }

    @Override
    public Mono<Void> deleteMovie(String id, String email) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllMovies() {
        return null;
    }
}
