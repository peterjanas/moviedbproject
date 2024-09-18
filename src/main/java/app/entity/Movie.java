package app.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(length = 1000)
    private String title;
    @Column(length = 1000)
    private String overview;
    @ElementCollection
    private Set<String> genres = new HashSet<>();
    private String originalLanguage;
    private double popularity;
    private LocalDate releaseDate;
    private double rating;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST)
    private Set<MoviePersonnel> moviePersonnel = new HashSet<>();

    public Movie(String title, String overview, Set<String> genre, String originalLanguage, LocalDate releaseDate, double rating, Set<MoviePersonnel> moviePersonnel)
    {
        this.title = title;
        this.overview = overview;
        this.genres = genre;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.moviePersonnel = moviePersonnel;
    }

    public Movie(Long id, String title, String overview, Set<String> genres, String originalLanguage, LocalDate releaseDate, double rating)
    {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.genres = genres;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public Movie(Long id, String title, String overview, Set<String> genres, String originalLanguage, double popularity, LocalDate releaseDate, double rating)
    {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.genres = genres;
        this.originalLanguage = originalLanguage;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }
}
