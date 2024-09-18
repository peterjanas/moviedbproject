package app.service;

import app.dto.MovieDTO;
import app.dto.MovieResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieService
{
    private static final String API_KEY = System.getenv("api_key");

    private List<MovieDTO> movieList = new ArrayList<>();


    public void getAllMovies() throws IOException, InterruptedException, URISyntaxException
    {
        HttpClient client = HttpClient.newHttpClient();
        int currentPage = 1;
        int totalPages;

        do
        {
            // Send request for the current page
            HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&with_original_language=da&primary_release_date.gte=2019-01-01" + "&primary_release_date.lte=2024-12-31&sort_by=popularity.desc&page=" + currentPage)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                String json = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                MovieResponseDTO movieResponse = objectMapper.readValue(json, MovieResponseDTO.class);

                // Add fetched movies to the list
                for (MovieDTO movie : movieResponse.getMovies())
                {
                    movieList.add(movie);
                }

                // Print page details
                System.out.println("You are on page: " + movieResponse.getPage() + "\n" + "Total pages: " + movieResponse.getTotalPages() + "\n" + "Total results: " + movieResponse.getTotalResults() + "\n");

                // Print movies
                StringBuilder output = new StringBuilder();
                for (MovieDTO movie : movieResponse.getMovies())
                {
                    output.append("Genres: ").append(movie.getGenres()).append("\n")
                            .append("Title: ").append(movie.getTitle()).append("\n")
                            .append("Overview: ").append(movie.getOverview()).append("\n")
                            .append("Original Language: ").append(movie.getOriginalLanguage()).append("\n")
                            .append("Release Date: ").append(movie.getReleaseDate()).append("\n")
                            .append("Rating: ").append(movie.getRating()).append("\n");
                    output.append("\n");
                }

                System.out.println(output);

                // Move to the next page
                totalPages = movieResponse.getTotalPages();
                currentPage++;
            } else
            {
                System.out.println("GET request failed. Status code: " + response.statusCode());
                break;
            }
        } while (currentPage <= totalPages);
        // Print the size of the movie list
        System.out.println("Total movies fetched: " + movieList.size());
    }
}
