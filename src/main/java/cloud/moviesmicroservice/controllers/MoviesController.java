package cloud.moviesmicroservice.controllers;

import cloud.moviesmicroservice.boundaries.MovieBoundary;
import cloud.moviesmicroservice.exception.BadRequestException;
import cloud.moviesmicroservice.services.MoviesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<MovieBoundary> createMovie(MovieBoundary movie) {
        return moviesService.createMovie(movie);
    }


    @GetMapping(
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE}
    )
    public Flux<MovieBoundary> getMovies(@RequestParam(value = "criteria", required = false) String criteria,
                                         @RequestParam(value = "value", required = false) String value) {
        if (criteria == null && value == null)
            return this.moviesService.getAllMovies();
        if (criteria != null && value != null)
            return this.moviesService.getMoviesByCriteria(criteria, value);
        throw new BadRequestException("Provide both criteria and value or none.");

    }

    @PutMapping(
            path = "/{movieId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Mono<Void> updateMovie(@PathVariable(name = "movieId") String movieId, @RequestParam(name = "email") String email, @RequestBody MovieBoundary movie) {
        return moviesService.updateMovie(movieId, email, movie);
    }

    @DeleteMapping(
            path = "/{movieId}"
    )
    public Mono<Void> deleteMovie(@PathVariable(name = "movieId") String movieId, @RequestParam(name = "email") String email) {
        return moviesService.deleteMovie(movieId, email);
    }

    @DeleteMapping
    public Mono<Void> deleteAllMovies() {
        return moviesService.deleteAllMovies();
    }

}
