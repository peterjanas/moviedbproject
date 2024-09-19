package app.service;
import app.dto.PersonnelDTO;
import app.dto.PersonnelDTOResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class PersonnelService
{

    public String getPersonnel(int movieId) throws IOException, InterruptedException, URISyntaxException
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
        PersonnelDTOResponse personnel = objectMapper.readValue(json, PersonnelDTOResponse.class);

        // Print out details of each cast member
        StringBuilder output = new StringBuilder("Movie ID: " + personnel.getId() + "\nCast Members:\n");
        for (PersonnelDTO personnelDTO : personnel.getCast())
        {
            output.append("ID: ").append(personnelDTO.getId())
                    .append(", Name: ").append(personnelDTO.getName())
                    .append(", Gender: ").append(personnelDTO.getGenderDescription())
                    .append(", Department: ").append(personnelDTO.getKnownForDepartment())
                    .append("\n");
        }

        // Filter crew members to only include directors and print their details
        List<PersonnelDTO> directors = personnel.getCrew().stream()
                .filter(crewMember -> "Director".equals(crewMember.getJob()))
                .collect(Collectors.toList());

        output.append("\nDirectors:\n");
        for (PersonnelDTO director : directors)
        {
            output.append("ID: ").append(director.getId())
                    .append(", Name: ").append(director.getName())
                    .append(", Gender: ").append(director.getGenderDescription())
                    .append(", Department: ").append(director.getKnownForDepartment())
                    .append(", Job: ").append(director.getJob())
                    .append("\n");
        }

        return output.toString();
    }
}
