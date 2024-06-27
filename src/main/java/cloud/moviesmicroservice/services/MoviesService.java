package cloud.moviesmicroservice.services;


import cloud.moviesmicroservice.boundaries.MovieBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MoviesService {

    public Mono<MovieBoundary> createMovie(MovieBoundary movie);
    public Flux<MovieBoundary> getAllMovies();
    public Flux<MovieBoundary> getMoviesByCriteria(String criteria, String value);
    public Mono<Void> updateMovie(String id, String email, MovieBoundary movie);
    public Mono<Void> deleteMovie(String id, String email);
    public Mono<Void> deleteAllMovies();


}
