package app.dao;


import app.entity.Movie;
import app.entity.Personnel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    public void saveAll(List<Movie> movies) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            int count = 0;
            for (Movie movie : movies) {
                em.persist(movie);
                if (++count % 20 == 0) { // Flush and clear in batches of 20
                    em.flush();
                    em.clear();
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save movies", e);
        } finally {
            em.close();
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
            if (!Objects.equals(verifyMovieId, movieId))
            {
                System.out.println("Movie ID does not match the movie title");
            } else
            {
                TypedQuery<Movie> query = em.createQuery("DELETE FROM Movie m WHERE m.title = :title", Movie.class);
                query.setParameter("title", movieTitle);
            }
        }
    }


}
