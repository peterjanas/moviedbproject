package app.dao;


import app.entity.Movie;
import app.entity.Personnel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
public class MovieDAO implements IDAO<Movie>
{

    private EntityManagerFactory emf;

    @Override
    public Movie getById(Integer Id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Movie.class, Id);
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
    public void delete(Movie movie)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(movie);
            em.getTransaction().commit();
        }
    }
}
