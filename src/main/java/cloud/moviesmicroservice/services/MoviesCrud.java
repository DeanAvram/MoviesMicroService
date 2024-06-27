package cloud.moviesmicroservice.services;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import cloud.moviesmicroservice.entities.MovieEntity;

public interface MoviesCrud extends ReactiveMongoRepository<MovieEntity, String>{

}
