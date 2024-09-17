package app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class MoviePersonnel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Personnel personnel;

    public MoviePersonnel(Movie movie, Personnel personnel)
    {
        this.movie = movie;
        this.personnel = personnel;
    }
}
