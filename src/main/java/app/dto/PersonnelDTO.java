package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonnelDTO
{
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    private String roleType;  // "cast" or "crew"
}
