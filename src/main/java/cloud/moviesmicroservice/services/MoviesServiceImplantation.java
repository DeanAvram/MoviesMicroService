package cloud.moviesmicroservice.services;

import cloud.moviesmicroservice.boundaries.MovieBoundary;
import cloud.moviesmicroservice.entities.MovieEntity;
import cloud.moviesmicroservice.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

@Service
public class MoviesServiceImplantation implements MoviesService {

    private MoviesCrud movies;
    private WebClient usersWebClient;

    @Value("${remote.users.service.url: http://localhost:8080/users}")
    public void setUsersWebClientWebClient(String remoteServiceUrl){
        //System.err.println("%%%" + remoteServiceUrl);
        this.usersWebClient = WebClient.create(remoteServiceUrl);
    }


    public MoviesServiceImplantation(MoviesCrud movies) {
        this.movies = movies;
    }


    @Override
    public Mono<MovieBoundary> createMovie(MovieBoundary movie) {
        Mono<MovieEntity> entity = this.movies.findById(movie.getId());
        return entity.hasElement()
                .flatMap(
                        exists -> {
                            if (exists)
                                return Mono.error(new BadRequestException("Movie with id: " + movie.getId() + " already exists"));
                            return Mono.just(movie);
                        })
                .map(this::movieToEntity)
                .flatMap(this.movies::save)
                .map(this::movieToBoundary);
    }

    @Override
    public Flux<MovieBoundary> getAllMovies() {
        return this.movies
                .findAll()
                .map(this::movieToBoundary);
    }


    @Override
    public Flux<MovieBoundary> getMoviesByCriteria(String criteria, String value) {
        Flux<MovieEntity> movies;
        try {
            movies = switch (criteria) {
                case("id") -> this.movies.findById(value).flux();
                case ("title") -> this.movies.findAllByTitleContainsIgnoreCase(value);
                case ("genre") -> this.movies.findAllByGenresContainingIgnoreCase(value);
                case ("maxLength") -> this.movies.findAllByLengthIsLessThan(Integer.parseInt(value));
                case ("minLength") -> this.movies.findAllByLengthGreaterThan(Integer.parseInt(value));
                default -> throw new BadRequestException("Invalid criteria: " + criteria);
            };
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("Invalid value for criteria: " + criteria + ". Value must be an integer.");
        }
        return movies.map(this::movieToBoundary);
    }

    @Override
    public Mono<Void> updateMovie(String id, String email, String password, MovieBoundary movie) {
        return isMovieExists(email, password)
                .then(this.movies.findById(id))
                .flatMap(movieEntity -> {
                    if (movie.getTitle() != null)
                        movieEntity.setTitle(movie.getTitle());
                    if (movie.getYear() != 0)
                        movieEntity.setYear(movie.getYear());
                    if (movie.getGenres() != null)
                        movieEntity.setGenres(movie.getGenres());
                    if (movie.getLanguage() != null)
                        movieEntity.setLanguage(movie.getLanguage());
                    movieEntity.setLength(movie.getLength());
                    if (movie.getDirectors() != null)
                        movieEntity.setDirectors(movie.getDirectors());
                    if (movie.getOrganization() != null)
                        movieEntity.setOrganization(movie.getOrganization());
                    return this.movies.save(movieEntity);
                })
                .then();
    }

    @Override
    public Mono<Void> deleteMovie(String id, String email, String password) {
        return isMovieExists(email, password)
                .then(this.movies.findById(id))
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
        rv.setDirectors(movieEntity.getDirectors());
        rv.setOrganization(movieEntity.getOrganization());

        return rv;
    }

    private MovieEntity movieToEntity(MovieBoundary movieBoundary) {
        MovieEntity rv = new MovieEntity();

        if (movieBoundary.getId() == null)
            throw new BadRequestException("Movie id cannot be null.");
        rv.setId(movieBoundary.getId());
        if (movieBoundary.getTitle() == null)
            throw new BadRequestException("Movie title cannot be null.");
        rv.setTitle(movieBoundary.getTitle());
        rv.setYear(movieBoundary.getYear());
        rv.setGenres(movieBoundary.getGenres());
        rv.setLanguage(movieBoundary.getLanguage());
        rv.setLength(movieBoundary.getLength());
        rv.setDirectors(movieBoundary.getDirectors());
        rv.setOrganization(movieBoundary.getOrganization());

        return rv;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(\\.[A-Za-z]{2,})?$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    private Mono<Boolean> isMovieExists(String email, String password) {
        if (!isValidEmail(email))
            return Mono.error(new BadRequestException("Invalid email format."));
        return this.usersWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{email}")
                        .queryParam("password", password)
                        .build(email))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new BadRequestException("Can not find or authenticate user: " + email) ))
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful());
    }


}
