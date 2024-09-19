package app.dto;

import app.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonnelDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("known_for_department")
    private String knownForDepartment;

    @JsonProperty("gender")
    private int gender;

    @JsonProperty("job")
    private String job;

    public String getGenderDescription() {
        return Gender.getDescriptionById(gender);
    }
}
