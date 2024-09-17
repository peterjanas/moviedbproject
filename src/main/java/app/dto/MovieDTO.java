package app.dto;

import app.entity.MoviePersonnel;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class MovieDTO
{

    private String title;
    private String overview;
    private Set<String> genres = new HashSet<>();
    private String originalLanguage;
    private LocalDate releaseDate;
    private double rating;
    private Set<MoviePersonnel> moviePersonnel = new HashSet<>();


}
