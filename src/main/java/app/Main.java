package app;

import app.config.HibernateConfig;
import app.dao.GenreDAO;
import app.dao.MovieDAO;
import app.dao.PersonnelDAO;
import app.service.GenreService;
import app.service.MovieService;
import app.service.PersonnelService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main
{
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedb");
        MovieDAO movieDAO = new MovieDAO(emf);
        PersonnelDAO personnelDAO = new PersonnelDAO(emf);
        PersonnelService personnelService = new PersonnelService(personnelDAO);
        MovieService movieService = new MovieService(movieDAO, personnelService);
        GenreDAO genreDAO = new GenreDAO(emf);
        GenreService genreService = new GenreService(genreDAO);

        //genreService.getGenresToDB(); //Method for getting genres
        //movieService.fetchAndSaveAllMoviesAndPersonnel(); // method for fill movies and personnal to database

        //movieDAO.printMoviesByActor(4662258L); //method to find all movies an actor has been in.
        //movieDAO.printActorsInMovie(1276696L); // method to find all actors in a movie.
        //genreDAO.printMoviesByAGenre(18L); // method to find all movies by genre
        //System.out.println(genreDAO.getGenres()); // method to get all genres from database
    }
}