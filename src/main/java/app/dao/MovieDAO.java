package app.dao;


import app.entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<Movie>
{

    private EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public Movie getById(Long Id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Movie.class, Id);
        }
    }

    public Movie getByTitle(String title)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Movie.class, title);
        }
    }

    @Override
    public Set<Movie> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
            List<Movie> movies = query.getResultList();
            return movies.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void create(Movie movie)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        }

    }

    @Override
    public void update(Movie movie)
    {

    }

    @Override
    public void delete(Movie movie)
    {

    }

    public void updateMovie(String movieTitle, Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            // Get movie where id is the same as the paramater id
            TypedQuery<Movie> verifyIdQuery = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class);
            verifyIdQuery.setParameter("id", movieId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getTitle();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            if (Objects.equals(verifyMovieId, movieId) && Objects.equals(verifyMovieTitle, movieTitle))
            {
                TypedQuery<Movie> deleteTitleQuery = em.createQuery("UPDATE FROM Movie m SET m.title = :title", Movie.class);
                deleteTitleQuery.setParameter("title", movieTitle);
            } else
            {
                System.out.println("Movie ID does not match the title of the Movie");
            }
        }
    }

    public void deleteMovie(String movieTitle, Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            // Get movie where id is the same as the paramater id
            TypedQuery<Movie> verifyIdQuery = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class);
            verifyIdQuery.setParameter("id", movieId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getTitle();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            if (Objects.equals(verifyMovieId, movieId) && Objects.equals(verifyMovieTitle, movieTitle))
            {
                TypedQuery<Movie> deleteTitleQuery = em.createQuery("DELETE FROM Movie m WHERE m.id = :id", Movie.class);
                deleteTitleQuery.setParameter("id", movieId);
            } else
            {
                System.out.println("Movie ID does not match the title of the Movie");
            }
        }
    }

    public void deleteMovieTitle(String movieTitle, Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get movie where id is the same as the paramater id
            TypedQuery<Movie> verifyIdQuery = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class);
            verifyIdQuery.setParameter("id", movieId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getTitle();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            // If the id and title from the parameter matches the database, delete the movie title
            if (Objects.equals(verifyMovieId, movieId) && Objects.equals(verifyMovieTitle, movieTitle))
            {
                TypedQuery<Movie> deleteTitleQuery = em.createQuery("UPDATE Movie m SET m.title = NULL WHERE m.id = :id", Movie.class);
                deleteTitleQuery.setParameter("id", movieId);
            } else
            {
                System.out.println("Personnel ID does not match the name of the person");

            }
        }
    }


    public double averageRating()
    {
        Set<Movie> movies = getAll();
        return movies.stream()
                .mapToDouble(Movie::getRating)
                .average().orElse(0);
    }

    public List<Movie> getTop10HighestRatedMovies()
    {
        Set<Movie> movies = getAll();
        return movies.stream()
                .sorted((movie1, movie2) -> Double.compare(movie2.getRating(), movie1.getRating()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Movie> getTop10LowestRatedMovies()
    {
        Set<Movie> movies = getAll();
        return movies.stream()
                .sorted((movie1, movie2) -> Double.compare(movie1.getRating(), movie2.getRating()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Movie> getTop10PopularMovies()
    {
        Set<Movie> movies = getAll();
        return movies.stream()
                .sorted((movie1, movie2) -> Double.compare(movie2.getPopularity(), movie1.getPopularity()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
