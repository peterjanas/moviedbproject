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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id") //måske
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "personnel_id") //måske
    private Personnel personnel;

    public MoviePersonnel(Movie movie, Personnel personnel)
    {
        this.movie = movie;
        this.personnel = personnel;
    }
}
