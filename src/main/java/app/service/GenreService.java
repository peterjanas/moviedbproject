package app.service;

import app.dto.GenreDTO;
import app.dto.GenresResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class GenreService
{

    public void getMoviesByGenre(int genreId)
    {
        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://api.themoviedb.org/3/discover/movie?api_key=" + System.getenv("api_key") +"&with_original_language=da&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-12-31&sort_by=popularity.desc&with_genres=" + genreId)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                System.out.println(response.body());
            } else
            {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public String getGenres() throws IOException, InterruptedException, URISyntaxException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://api.themoviedb.org/3/genre/movie/list?api_key=" + System.getenv("api_key") + "&language=en")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        GenresResponseDTO genresResponse = objectMapper.readValue(json, GenresResponseDTO.class);

        StringBuilder output = new StringBuilder();
        for (GenreDTO genre : genresResponse.getGenres())
        {
            output.append("Genre ID: ").append(genre.getId()).append(", Genre Name: ").append(genre.getName()).append("\n");
        }
        return output.toString();
    }
}