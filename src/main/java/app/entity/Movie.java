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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String overview;
    @ElementCollection
    private Set<String> genre = new HashSet<>();
    private String originalLanguage;
    private LocalDate releaseDate;
    private double rating;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST)
    private Set<MoviePersonnel> moviePersonnel = new HashSet<>();

    public Movie(String title, String overview, Set<String> genre, String originalLanguage, LocalDate releaseDate, double rating, Set<MoviePersonnel> moviePersonnel)
    {
        this.title = title;
        this.overview = overview;
        this.genre = genre;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.moviePersonnel = moviePersonnel;
    }
}
