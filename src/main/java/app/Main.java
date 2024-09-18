package app;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main
{
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
    {

      EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedb");

























      // Masih test
      MovieDAO movieDAO = new MovieDAO(emf);
      System.out.println(movieDAO.getTop10LowestRatedMovies());








      MovieService movieService = new MovieService();
//        movieService.getMovies(5);



    }
}