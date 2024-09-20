package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class MovieResponseDTO
{
    private int page;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_results")
    private int totalResults;
    private List<MovieDTO> results;  // This assumes the MovieDTO matches the fields of each movie object in the JSON

    // Constructors

    public MovieResponseDTO()
    {
    }

    public MovieResponseDTO(int page, int totalPages, int totalResults, List<MovieDTO> results)
    {
        this.page = page;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
        this.results = results;
    }

    public List<MovieDTO> getMovies()
    {
        return results;
    }

    public void setMovies(List<MovieDTO> results)
    {
        this.results = results;
    }
}

