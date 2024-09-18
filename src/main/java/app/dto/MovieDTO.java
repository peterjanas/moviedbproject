package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)

@Data
public class MovieDTO
{
    @JsonProperty("genre_ids")
    private Set<Integer> genres;
    @JsonProperty("original_title")
    private String title;
    private String overview;
    private double popularity;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("vote_average")
    private double rating;
}
