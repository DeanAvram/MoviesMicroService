package cloud.moviesmicroservice.entities;

import cloud.moviesmicroservice.exception.BadRequestException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Year;
import java.util.Date;
import java.util.Set;

@Document(collection = "MOVIES")
public class MovieEntity {

    @Id
    private String id;
    private String title;
    private int year;
    private Set<String> genres;
    private String language;
    private int length;
    private String director;

    public MovieEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1800 || year > Year.now().getValue())
            //assume there are no movies before 1800 and no movies from the future
            throw new BadRequestException("Year must be a valid year.");
        this.year = year;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length <= 0)
            throw new BadRequestException("Length must be a positive number.");
        this.length = length;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
