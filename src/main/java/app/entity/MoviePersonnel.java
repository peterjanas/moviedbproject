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
    private Long castMemberID;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Personnel personnel;

    public MoviePersonnel(Long movieID, Long castMemberID, Movie movie, Personnel personnel)
    {
        this.movieID = movieID;
        this.castMemberID = castMemberID;
        this.movie = movie;
        this.personnel = personnel;
    }
}
