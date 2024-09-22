package app.service;

import app.dao.PersonnelDAO;

import app.entity.Personnel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;



public class PersonnelService
{
    private static final String API_KEY = System.getenv("api_key");
    private PersonnelDAO personnelDAO;

    public PersonnelService(PersonnelDAO personnelDAO)
    {
        this.personnelDAO = personnelDAO;
    }

    public void fetchAndSaveCastAndCrew(Long movieId) throws IOException, InterruptedException
    {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        List<Personnel> personnelList = extractPersonnel(rootNode, "cast");
        personnelList.addAll(extractPersonnel(rootNode, "crew"));

        // Call DAO to save personnel and link them to the movie
        personnelDAO.savePersonnelAndLinkToMovie(personnelList, movieId);
    }

    private List<Personnel> extractPersonnel(JsonNode rootNode, String roleType)
    {
        List<Personnel> personnelList = new ArrayList<>();
        JsonNode nodes = rootNode.path(roleType);

        for (JsonNode node : nodes)
        {
            Personnel personnel = new Personnel();
            personnel.setId(node.path("id").asLong());
            personnel.setName(node.path("name").asText());
            personnel.setRoleType(roleType);
            personnelList.add(personnel);
        }
        return personnelList;
    }
}