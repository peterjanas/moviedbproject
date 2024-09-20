package app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
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

    private String originalLanguage;
    private double popularity;
    private LocalDate releaseDate;
    private double rating;
    @ManyToMany
    @JoinTable(
            name = "personnel_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "personnel_id")
    )
    private Set<Personnel> personnelList = new HashSet<>();

    @ManyToMany(mappedBy = "movieList", cascade = CascadeType.PERSIST)
    private Set<Genre> genreList = new HashSet<>();

    @Transient
    List<Integer> genreIds = new ArrayList<>();

    @Transient
    List<Integer> personnalIds = new ArrayList<>();

    public void addGenre(Genre genre)
    {
        genreList.add(genre);
        genre.getMovieList().add(this);
    }

    public void addPersonnel(Personnel personnel)
    {
        personnelList.add(personnel);
        personnel.getMovies().add(this);
    }


    public Movie(String title, String overview, String originalLanguage, LocalDate releaseDate, double rating, double popularity, Set<Personnel> personnelList)
    {
        this.title = title;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.popularity = popularity;
        this.personnelList = personnelList;
    }

    public Movie(Long id, String title, String overview, String originalLanguage, LocalDate releaseDate, double rating)
    {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public Movie(Long id, String title, String overview, String originalLanguage, double popularity, LocalDate releaseDate, double rating)
    {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public Movie(String title, String overview, double rating)
    {
        this.title = title;
        this.overview = overview;
        this.rating = rating;
    }

    @Override
    public String toString()
    {
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
