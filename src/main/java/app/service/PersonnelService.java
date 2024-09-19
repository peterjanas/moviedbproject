package app.service;

import app.dao.PersonnelDAO;
import app.dto.CastMemberDTO;
import app.dto.CrewMemberDTO;
import app.dto.PersonnelDTO;
import app.entity.Movie;
import app.entity.Personnel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonnelService
{
    private static final String API_KEY = System.getenv("api_key");
    private PersonnelDAO personnelDAO;

    public PersonnelService(PersonnelDAO personnelDAO) {
        this.personnelDAO = personnelDAO;
    }

    /*public String getPersonnel(int movieId) throws IOException, InterruptedException, URISyntaxException
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI("https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + System.getenv("api_key") + "&language=en-US"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        PersonnelDTO personnel = objectMapper.readValue(json, PersonnelDTO.class);

        // Print out details of each cast member
        StringBuilder output = new StringBuilder("Movie ID: " + personnel.getId() + "\nCast Members:\n");
        for (CastMemberDTO castMember : personnel.getCast())
        {
            output.append("ID: ").append(castMember.getId())
                    .append(", Name: ").append(castMember.getName())
                    .append(", Gender: ").append(castMember.getGender())
                    .append(", Department: ").append(castMember.getKnownForDepartment())
                    .append("\n");
        }

        // Filter crew members to only include directors and print their details
        List<CrewMemberDTO> directors = personnel.getCrew().stream()
                .filter(crewMember -> "Directing".equals(crewMember.getKnownForDepartment()))
                .collect(Collectors.toList());

        output.append("\nDirectors:\n");
        for (CrewMemberDTO director : directors)
        {
            output.append("ID: ").append(director.getId())
                    .append(", Name: ").append(director.getName())
                    .append(", Gender: ").append(director.getGender())
                    .append(", Department: ").append(director.getKnownForDepartment())
                    .append("\n");
        }

        return output.toString();
    }*/

    public void fetchAndSaveCastAndCrew(Long movieId) throws IOException, InterruptedException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=" + API_KEY;
                      //https://api.themoviedb.org/3/movie/139/credits?api_key=dde2919024bba45ec01ad23775425c1d&language=en-US
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        List<Personnel> personnelList = new ArrayList<>();
        personnelList.addAll(extractPersonnel(rootNode, "cast"));
        personnelList.addAll(extractPersonnel(rootNode, "crew"));
        personnelDAO.savePersonnel(personnelList);
    }

    private List<Personnel> extractPersonnel(JsonNode rootNode, String roleType) {
        List<Personnel> personnelList = new ArrayList<>();
        JsonNode nodes = rootNode.path(roleType);

        for (JsonNode node : nodes) {
            Personnel personnel = new Personnel();
            personnel.setId(node.path("id").asLong());
            personnel.setName(node.path("name").asText());
            personnel.setRoleType(roleType);
            personnelList.add(personnel);
        }
        return personnelList;
    }
}
