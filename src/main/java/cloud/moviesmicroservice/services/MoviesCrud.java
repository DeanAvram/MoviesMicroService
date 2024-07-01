package cloud.moviesmicroservice.services;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import cloud.moviesmicroservice.entities.MovieEntity;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface MoviesCrud extends ReactiveMongoRepository<MovieEntity, String>{

    public Flux<MovieEntity> findAllByTitleContainsIgnoreCase(@Param("value") String title);
    public Flux<MovieEntity> findAllByGenresContainingIgnoreCase(@Param("value") String genre);
    public Flux<MovieEntity> findAllByLengthGreaterThan(@Param("value") int length);
    public Flux<MovieEntity> findAllByLengthIsLessThan(@Param("value") int length);
}
