package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonnelDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("cast")
    private List<CastMemberDTO> cast;  // List of cast members

    @JsonProperty("crew")
    private List<CrewMemberDTO> crew;  // List of crew members
}
