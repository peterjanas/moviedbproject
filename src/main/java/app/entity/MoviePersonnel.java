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
    private Long movieID;
    private Long personnelID;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Personnel personnel;

    public MoviePersonnel(Long movieID, Long personnelID, Movie movie, Personnel personnel)
    {
        this.movieID = movieID;
        this.personnelID = personnelID;
        this.movie = movie;
        this.personnel = personnel;
    }
}
