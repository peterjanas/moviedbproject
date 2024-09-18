package app;

import app.config.HibernateConfig;
import app.dao.MovieDAO;
import app.entity.Movie;
import app.service.GenreService;
import app.service.MovieService;
import app.service.PersonnelService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;


public class Main
{
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
    {
      EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedb");






















      // Masih test
      Movie m1 = new Movie("Intersteller", "Very good movie with splendid actors", 9.7);
      Movie m2 = new Movie("Great Gatsby", "Spendid movie with good actors", 8.3);
      Movie m3 = new Movie("Meh movie", "Meh movie with meh actors", 3.2);

      MovieDAO movieDAO = new MovieDAO(emf);

      System.out.println(movieDAO.averageRating());

        MovieService movieService = new MovieService();
//        movieService.getMovies(5);

    }
}