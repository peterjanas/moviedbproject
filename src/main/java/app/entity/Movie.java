package app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor

public class Movie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    @Column(length = 1000) // Increase the length to accommodate longer overviews
    private String overview;
    @ElementCollection
    private Set<String> genres;
    private String originalLanguage;
    private LocalDate releaseDate;
    private double rating;
    private double popularity;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST)
    private Set<MoviePersonnel> moviePersonnel = new HashSet<>();

    public Movie(String title, String overview, Set<String> genre, String originalLanguage, LocalDate releaseDate, double rating, double popularity  ,Set<MoviePersonnel> moviePersonnel)
    {
        this.title = title;
        this.overview = overview;
        this.genres = genre;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.popularity = popularity;
        this.moviePersonnel = moviePersonnel;
    }

    public Movie(String title, String overview, double rating)
    {
        this.title = title;
        this.overview = overview;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                '}';
    }
}
