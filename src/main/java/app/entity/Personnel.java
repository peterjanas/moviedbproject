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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Long roleId;
    private Role role;
    @OneToMany(mappedBy = "personnel", cascade = CascadeType.PERSIST)
    private Set<MoviePersonnel> moviePersonnel = new HashSet<>();

    public Personnel(String name, Long roleId, Role role, Set<MoviePersonnel> moviePersonnel)
    {
        this.name = name;
        this.roleId = roleId;
        this.role = role;
        this.moviePersonnel = moviePersonnel;
    }
}
