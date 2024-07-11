package cloud.moviesmicroservice.controllers;

import cloud.moviesmicroservice.boundaries.MovieBoundary;
import cloud.moviesmicroservice.services.MoviesService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MoviesRSocketController {

    private MoviesService moviesService;

    public MoviesRSocketController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    // rsc --stream --route=get-all-movies --debug tcp://localhost:7001
    @MessageMapping("get-all-movies")
    public Flux<MovieBoundary> getAllMovies() {
        return this.moviesService.getAllMovies();
    }

    // rsc --request --route=create-new-movie --data={\"id\":\"tt0110913\",\"title\":\"Pulp Fiction\",\"year\":1994,\"genres\":[\"Drama\",\"Crime\"],\"language\":\"English\",\"length\":154,\"directors\":[\"Quentin Tarantino\"],\"organization\":\"mirmax\"} --debug tcp://localhost:7001
    @MessageMapping("create-new-movie")
    public Mono<MovieBoundary> createMovie(@Payload MovieBoundary movie) {
        return moviesService.createMovie(movie);
    }

    // rsc --fnf --route=delete-all-movies --debug tcp://localhost:7001
    @MessageMapping("delete-all-movies")
    public Mono<Void> deleteAllMovies() {
        return this.moviesService.deleteAllMovies();
    }




}
