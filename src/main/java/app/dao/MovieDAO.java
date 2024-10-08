package app.dao;


import app.entity.Genre;
import app.entity.Movie;
import app.entity.Personnel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.Iterator;
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

    public void saveAll(List<Movie> movies)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            int count = 0;
            for (Movie movie : movies)
            {
                movie.getGenreIds().forEach(genreId ->
                {
                    Genre genre = em.find(Genre.class, genreId);
                    if (genre != null)
                    {
                        movie.addGenre(genre);
                    }
                });
                em.persist(movie);
                if (++count % 20 == 0)
                { // Flush and clear in batches of 20
                    em.flush();
                    em.clear();
                }
            }
            em.getTransaction().commit();
        } catch (Exception e)
        {
            if (em.getTransaction().isActive())
            {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to save movies", e);
        } finally
        {
            em.close();
        }
    }

    public void updateMovie(Long movieId, String newTitle, Double newRating, LocalDate newReleaseDate)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get movie where id is the same as the parameter id
            TypedQuery<Movie> verifyIdQuery = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class);
            verifyIdQuery.setParameter("id", movieId);

            // Verify the movie exists
            Movie movie = verifyIdQuery.getSingleResult();

            // If the movie exists, update the attributes
            if (movie != null)
            {
                Query updateQuery = em.createQuery("UPDATE Movie m SET m.title = :title, m.rating = :rating, m.releaseDate = :releaseDate WHERE m.id = :id");
                updateQuery.setParameter("title", newTitle);
                updateQuery.setParameter("rating", newRating);
                updateQuery.setParameter("releaseDate", newReleaseDate);
                updateQuery.setParameter("id", movieId);
                updateQuery.executeUpdate();
                em.getTransaction().commit();
            } else
            {
                System.out.println("Movie not found");
                em.getTransaction().rollback();
            }
        }
    }

    public void deleteMovie(String movieTitle, Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get movie where id is the same as the parameter id
            TypedQuery<Movie> verifyIdQuery = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class);
            verifyIdQuery.setParameter("id", movieId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getTitle();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            // If the id and title from the parameter matches the database, delete the movie
            if (Objects.equals(verifyMovieId, movieId) && Objects.equals(verifyMovieTitle, movieTitle))
            {
                Query deleteTitleQuery = em.createQuery("DELETE FROM Movie m WHERE m.id = :id");
                deleteTitleQuery.setParameter("id", movieId);
                deleteTitleQuery.executeUpdate();
                em.getTransaction().commit();
            } else
            {
                System.out.println("Movie ID does not match the title of the Movie");
                em.getTransaction().rollback();
            }
        }
    }

    public void deleteMovieTitle(String movieTitle, Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get movie where id is the same as the parameter id
            TypedQuery<Movie> verifyIdQuery = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class);
            verifyIdQuery.setParameter("id", movieId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getTitle();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            // If the id and title from the parameter matches the database, delete the movie title
            if (Objects.equals(verifyMovieId, movieId) && Objects.equals(verifyMovieTitle, movieTitle))
            {
                Query deleteTitleQuery = em.createQuery("UPDATE Movie m SET m.title = NULL WHERE m.id = :id");
                deleteTitleQuery.setParameter("id", movieId);
                deleteTitleQuery.executeUpdate();
                em.getTransaction().commit();
            } else
            {
                System.out.println("Movie ID does not match the title of the Movie");
                em.getTransaction().rollback();
            }
        }
    }

    public void deleteMovieReleaseDate(String movieTitle, Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get movie where id is the same as the parameter id
            TypedQuery<Movie> verifyIdQuery = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id", Movie.class);
            verifyIdQuery.setParameter("id", movieId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getTitle();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            // If the id and title from the parameter matches the database, delete the movie title
            if (Objects.equals(verifyMovieId, movieId) && Objects.equals(verifyMovieTitle, movieTitle))
            {
                Query deleteReleaseYearQuery = em.createQuery("UPDATE Movie m SET m.releaseDate = NULL WHERE m.id = :id");
                deleteReleaseYearQuery.setParameter("id", movieId);
                deleteReleaseYearQuery.executeUpdate();
                em.getTransaction().commit();
            } else
            {
                System.out.println("Movie ID does not match the title of the Movie");
                em.getTransaction().rollback();
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
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext())
        {
            Movie m = iterator.next();
            if (m.getRating() == 0.0)
            {
                iterator.remove();
            }
        }
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


    public List<Movie> getMovieByTitle(String title)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m LEFT JOIN FETCH m.genreList WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))",
                    Movie.class
            );
            query.setParameter("title", title);
            List<Movie> movies = query.getResultList();
            return query.getResultList();
        }
    }


    public List<Personnel> findActorsByMovieId(Long movieId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p FROM Personnel p JOIN p.movies m WHERE m.id = :movieId AND p.roleType = 'cast'", Personnel.class)
                    .setParameter("movieId", movieId)
                    .getResultList();
        }
    }

    public void printActorsInMovie(Long movieId)
    {
        List<Personnel> actors = findActorsByMovieId(movieId);
        System.out.println("Actors in Movie ID " + movieId + ":");
        for (Personnel actor : actors)
        {
            System.out.println("Actor ID: " + actor.getId() + ", Name: " + actor.getName());
        }
        if (actors.isEmpty())
        {
            System.out.println("No actors found for Movie ID " + movieId);
        }
    }

    public List<Movie> findMoviesByActorId(Long actorId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Movie m JOIN m.personnelList p WHERE p.id = :actorId AND p.roleType = 'cast'", Movie.class)
                    .setParameter("actorId", actorId)
                    .getResultList();
        }
    }

    public void printMoviesByActor(Long actorId)
    {
        List<Movie> movies = findMoviesByActorId(actorId);
        System.out.println("Movies featuring Actor ID " + actorId + ":");
        for (Movie movie : movies)
        {
            System.out.println("Movie ID: " + movie.getId() + ", Title: " + movie.getTitle());
        }
        if (movies.isEmpty())
        {
            System.out.println("No movies found featuring Actor ID " + actorId);
        }
    }
}
