package app.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class MovieDTO
{
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
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
