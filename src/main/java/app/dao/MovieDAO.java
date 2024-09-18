package app.dao;


import app.entity.Movie;
import app.entity.Personnel;
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
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(movie);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Movie movieTitle)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(getById(movieTitle.getId()));
            em.getTransaction().commit();
        }

    }

    public void deleteMovieTitle(String movieTitle, Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Long verifyMovieId;
            verifyMovieId = movieId;
            if(!Objects.equals(verifyMovieId, movieId))
            {
                System.out.println("Movie ID does not match the movie title");
            } else {
                TypedQuery<Movie> query = em.createQuery("DELETE FROM Movie m WHERE m.title = :title", Movie.class);
                query.setParameter("title", movieTitle);
            }
        }
    }

    public double averageRating()
    {
        Set<Movie> movies = getAll();

        return movies.stream().mapToDouble(Movie::getRating).average().orElse(0);

    }
}
