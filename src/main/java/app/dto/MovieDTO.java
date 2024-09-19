package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)

@Data
public class MovieDTO
{
    @JsonProperty("id")
    private Long id;


    @JsonProperty("genre_ids")
    private Set<Integer> genres;
    @JsonProperty("original_title")
    private String title;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("genre_ids")
    private Set<Integer> genreIds = new HashSet<>();

    @JsonIgnore // This tells Jackson to ignore this field during serialization and deserialization
    private Set<String> genres = new HashSet<>();

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("popularity")
    private double popularity;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("vote_average")
    private double rating;

}
