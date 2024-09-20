package app.dao;

import app.dto.GenreDTO;
import app.entity.Genre;
import app.entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GenreDAO
{
    private EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public void saveGenresToDB(List<GenreDTO> genreDTOList)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            for (GenreDTO genreDTO : genreDTOList)
            {
                Genre genre = new Genre();
                genre.setId(genreDTO.getId());
                genre.setName(genreDTO.getName());
                em.persist(genre);
            }
            em.getTransaction().commit();
        }
    }

    public List<Genre> getGenres()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        }
    }

    public List<Movie> getMoviesByGenre(Long genreId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            List<Movie> movies = em.createQuery("SELECT m FROM Movie m JOIN m.genreList g WHERE g.id = :genreId", Movie.class)
                    .setParameter("genreId", genreId)
                    .getResultList();
            // Initialize genreList to avoid LazyInitializationException
            for (Movie movie : movies)
            {
                movie.getGenreList().size(); // Access the genreList to initialize it
            }
            return movies;
        }
    }

    public void printMoviesByAGenre(Long genreId)
    {
        List<Movie> movies = getMoviesByGenre(genreId);
        StringBuilder moviesPrint = new StringBuilder();
        moviesPrint.append("\n");
        for (Movie movie : movies)
        {
            moviesPrint.append("\n");
            moviesPrint.append("Title: ").append(movie.getTitle());
            moviesPrint.append("\n");
            moviesPrint.append("Genres: ");
            for (Genre genre : movie.getGenreList())
            {
                moviesPrint.append(genre.getName() + "-" + genre.getId()).append(", ");
            }
            moviesPrint.append("\n");
        }
        if (movies.isEmpty())
        {
            System.out.println("No Movies found for Genre ID " + genreId);
        }
        System.out.println(moviesPrint);
    }
}
