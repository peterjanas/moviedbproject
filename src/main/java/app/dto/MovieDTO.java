package app.dto;

import app.entity.MoviePersonnel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class MovieDTO
{

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("genres")
    private Set<String> genres = new HashSet<>();
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("vote_average")
    private double rating;
    //private Set<MoviePersonnel> moviePersonnel = new HashSet<>();


}
