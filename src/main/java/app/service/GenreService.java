package app.service;

import app.dao.GenreDAO;
import app.dto.GenreDTO;
import app.dto.GenresResponseDTO;
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


public class GenreService
{
    private static final String API_KEY = System.getenv("api_key");
    private GenreDAO genreDAO;

    public GenreService(GenreDAO genreDAO)
    {
        this.genreDAO = genreDAO;
    }

    public void getGenresToDB() throws IOException, InterruptedException, URISyntaxException
    {
        HttpClient client = HttpClient.newHttpClient();
        List<GenreDTO> genreList = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=en")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200)
        {
            String json = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            GenresResponseDTO genresResponse = objectMapper.readValue(json, GenresResponseDTO.class);

            genreList.addAll(genresResponse.getGenres());
            genreDAO.saveGenresToDB(genreList);

        } else
        {
            System.out.println("GET request failed. Status code: " + response.statusCode());
        }

    }


    public String getGenres() throws IOException, InterruptedException, URISyntaxException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=en")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        String json = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        GenresResponseDTO genresResponse = objectMapper.readValue(json, GenresResponseDTO.class);

        StringBuilder output = new StringBuilder();
        for (GenreDTO genre : genresResponse.getGenres())
        {
            output
                    .append("Genre ID: ").append(genre.getId())
                    .append(", Genre Name: ").append(genre.getName()).append("\n");
        }
        return output.toString();
    }
}