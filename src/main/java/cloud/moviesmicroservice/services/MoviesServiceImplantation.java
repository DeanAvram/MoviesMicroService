package cloud.moviesmicroservice.services;

import cloud.moviesmicroservice.boundaries.MovieBoundary;
import cloud.moviesmicroservice.entities.MovieEntity;
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
        return this.movies
                .findAll()
                .map(this::movieToBoundary);
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
        return this.movies.findById(id)
                .flatMap(movies::delete)
                .then();
    }

    @Override
    public Mono<Void> deleteAllMovies() {
        return this.movies.deleteAll();
    }

    private MovieBoundary movieToBoundary(MovieEntity movieEntity) {
        MovieBoundary rv = new MovieBoundary();

        rv.setId(movieEntity.getId());
        rv.setTitle(movieEntity.getTitle());
        rv.setYear(movieEntity.getYear());
        rv.setGenres(movieEntity.getGenres());
        rv.setLanguage(movieEntity.getLanguage());
        rv.setLength(movieEntity.getLength());
        rv.setDirector(movieEntity.getDirector());

        return rv;
    }

    private MovieEntity movieToEntity (MovieBoundary movieBoundary){
        MovieEntity rv = new MovieEntity();

        rv.setId(movieBoundary.getId());
        rv.setTitle(movieBoundary.getTitle());
        rv.setYear(movieBoundary.getYear());
        rv.setGenres(movieBoundary.getGenres());
        rv.setLanguage(movieBoundary.getLanguage());
        rv.setLength(movieBoundary.getLength());
        rv.setDirector(movieBoundary.getDirector());

        return rv;
    }


}
