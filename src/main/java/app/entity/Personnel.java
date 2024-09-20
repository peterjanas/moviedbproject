package app.entity;

import app.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Personnel
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String roleType;

    @ManyToMany
    private Set<Movie> movieList = new HashSet<>();

    public Personnel(String name, String roleType)
    {
        this.name = name;
        this.roleType = roleType;
    }
}
