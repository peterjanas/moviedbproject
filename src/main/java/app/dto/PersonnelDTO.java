package app.dto;

import app.entity.MoviePersonnel;
import app.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PersonnelDTO
{
    private Long id;
    private String name;
    private int roleID;
    private Role role;
    private Set<MoviePersonnel> moviePersonnel = new HashSet<>();
}
