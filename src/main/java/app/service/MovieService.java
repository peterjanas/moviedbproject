package app.service;

import app.dao.MovieDAO;
import app.dto.MovieResponseDTO;
import app.dto.MovieDTO;
import app.entity.Movie;
import app.service.PersonnelService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieService
{
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";
    private final MovieDAO movieDAO;
    private Map<Integer, String> genreMap = new HashMap<>();
    private PersonnelService personnelService;


    public MovieService(MovieDAO movieDAO, PersonnelService personnelService) throws IOException, InterruptedException
    {
        this.movieDAO = movieDAO;
        this.personnelService = personnelService;
    }

    public void fetchAndSaveAllMoviesAndPersonnel() throws IOException, InterruptedException {
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        int page = 1;
        int totalPages;

        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        do {
            String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY +
                    "&with_origin_country=DK&primary_release_date.gte=" +
                    fiveYearsAgo + "&primary_release_date.lte=" + LocalDate.now() +
                    "&sort_by=popularity.desc&page=" + page;

            HttpResponse<String> response = client.send(HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build(), HttpResponse.BodyHandlers.ofString());
            MovieResponseDTO movieResponse = objectMapper.readValue(response.body(), MovieResponseDTO.class);
            List<Movie> movies = convertToMovies(movieResponse);
            movieDAO.saveAll(movies);

            // Fetch and save personnel for each movie
            for (Movie movie : movies) {
                personnelService.fetchAndSaveCastAndCrew(movie.getId());
            }

            totalPages = movieResponse.getTotalPages();
            page++;
        } while (page <= totalPages);
    }

    private List<Movie> convertToMovies(MovieResponseDTO movieResponse)
    {
        return movieResponse.getMovies().stream().map(dto ->
        {
            Movie movie = new Movie();
            movie.setId(dto.getId());
            movie.setTitle(dto.getTitle());
            movie.setOverview(dto.getOverview());
            movie.setOriginalLanguage(dto.getOriginalLanguage());
            movie.setReleaseDate(dto.getReleaseDate());
            movie.setRating(dto.getRating());
            movie.setPopularity(dto.getPopularity());
            Set<String> genreNames = dto.getGenreIds().stream()
                    .map(id -> genreMap.getOrDefault(id, "Unknown Genre"))
                    .collect(Collectors.toSet());
            movie.setGenres(genreNames);
            return movie;
        }).collect(Collectors.toList());
    }
}

