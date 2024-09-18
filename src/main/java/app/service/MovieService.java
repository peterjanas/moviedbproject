package app.service;

import app.dao.MovieDAO;
import app.dto.MovieDTO;
import app.dto.MovieResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import app.dto.MovieDTO;
import app.dto.MovieResponseDTO;
import app.entity.Movie;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService
{
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";
    private final MovieDAO movieDAO;
    private Map<Integer, String> genreMap = new HashMap<>(); // Genre ID to Name mapping


    public MovieService(MovieDAO movieDAO) throws IOException, InterruptedException
    {
        this.movieDAO = movieDAO;
        fetchGenreMappings(); // Load genre mappings at initialization
    }

    public void fetchAndSaveDanishMovies() throws IOException, InterruptedException
    {
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        int page = 1;
        int totalPages = 1; // Initially set to 1, will be updated from API response

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        while (page <= totalPages)
        {
            String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY + "&with_original_language=da&primary_release_date.gte=" + fiveYearsAgo + "&primary_release_date.lte=" + LocalDate.now() + "&sort_by=popularity.desc&page=" + page;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            MovieResponseDTO movieResponse = objectMapper.readValue(response.body(), MovieResponseDTO.class);
            totalPages = movieResponse.getTotalPages();

            List<Movie> moviesToSave = movieResponse.getMovies().stream().map(dto ->
            {
                Movie movie = new Movie();
                movie.setId(dto.getId());
                movie.setTitle(dto.getTitle());
                movie.setOverview(dto.getOverview());
                movie.setOriginalLanguage(dto.getOriginalLanguage());
                movie.setReleaseDate(dto.getReleaseDate());
                movie.setRating(dto.getRating());
                Set<String> genreNames = dto.getGenreIds().stream()
                        .map(id -> genreMap.getOrDefault(id, "Unknown Genre"))
                        .collect(Collectors.toSet());
                movie.setGenres(genreNames); // Assuming Movie entity has a Set<String> for genres
                return movie;
            }).collect(Collectors.toList());

            movieDAO.saveAll(moviesToSave); // Assuming MovieDAO has a method to save all movies at once
            page++;
        }
    }
        /*
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        int page = 1;
        int totalPages = 1; // This will be updated from API response
        List<Movie> moviesToSave = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        while (page <= totalPages)
        {
            String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY + "&with_original_language=da&primary_release_date.gte=" + fiveYearsAgo + "&primary_release_date.lte=" + LocalDate.now() + "&sort_by=popularity.desc&page=" + page;


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            MovieResponseDTO movieResponse = objectMapper.readValue(response.body(), MovieResponseDTO.class);
            totalPages = movieResponse.getTotalPages();

            List<MovieDTO> movies = movieResponse.getMovies().stream().map(movieDto ->
            {
                Set<String> genreNames = movieDto.getGenreIds().stream()
                        .map(id -> genreMap.getOrDefault(id, "Unknown Genre"))
                        .collect(Collectors.toSet());
                movieDto.setGenres(genreNames);// Ensure this method sets genre names instead of IDs
                return movieDto;
            }).collect(Collectors.toList());

            moviesToSave.addAll(movieResponse.getMovies().stream()
                    .map(dto -> convertToEntity(dto))
                    .collect(Collectors.toList()));

            //List<MovieDTO> movies = movieResponse.getMovies();
            if (movies.isEmpty())
            {
                System.out.println("No movies found on page " + page);
            } else
            {
                for (MovieDTO movie : movies)
                {
                    System.out.println("Movie Id: " + movie.getId());
                    System.out.println("Movie Genre:" + movie.getGenres());
                    System.out.println("Movie Title: " + movie.getTitle());
                    System.out.println("Release Date: " + movie.getReleaseDate());
                    System.out.println("Original Language: " + movie.getOriginalLanguage());
                    System.out.println("Popularity: " + movie.getPopularity());
                    System.out.println("Overview: " + movie.getOverview());
                    System.out.println("Rating: " + movie.getRating());
                    System.out.println("--------");
                }
            }
            page++;
        }

        //movieRepository.saveAll(moviesToSave); // Save all fetched movies in one go
        //System.out.println(moviesToSave);
        System.out.println("done");
    }*/


    public void fetchGenreMappings() throws IOException, InterruptedException
    {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=en-US";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode genres = root.path("genres");
        for (JsonNode genre : genres)
        {
            int id = genre.path("id").asInt();
            String name = genre.path("name").asText();
            genreMap.put(id, name);
        }
    }
}

