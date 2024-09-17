package app.service;

import app.dto.MovieDTO;
import app.dto.MovieResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService
{
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/";
    private static final String  BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";


    public static String getMovieById(int id) throws IOException, InterruptedException
    {
        String url = BASE_URL_MOVIE + id + "?api_key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = response.body();
        MovieDTO movie = objectMapper.readValue(json, MovieDTO.class);

        return movie.toString();
    }

    public static void getByRating(double rating1, double rating2) throws IOException, InterruptedException
    {
        String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = response.body();

        // Deserialize the response into the wrapper DTO
        MovieResponseDTO movieResponse = objectMapper.readValue(json, MovieResponseDTO.class);

        // Extract the list of movies from the wrapper DTO
        List<MovieDTO> movies = movieResponse.getMovies();

        // Filter and print movies within the specified rating range
        for (MovieDTO movie : movies) {
            double rating = movie.getRating(); // Assuming there is a getRating() method in MovieDTO
            if (rating >= rating1 && rating <= rating2) {
                System.out.println(movie);
            }
        }
    }

    public static String getSortedByReleaseDate(LocalDate releaseDate) throws IOException, InterruptedException
    {
        String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = response.body();

        MovieResponseDTO movieResponse = objectMapper.readValue(json, MovieResponseDTO.class);

        List<MovieDTO> movies = movieResponse.getMovies();

        return movies.stream()
                .sorted(Comparator.comparing(MovieDTO::getReleaseDate))
                .map(MovieDTO::toString)
                .collect(Collectors.joining("\n"));
    }
}
