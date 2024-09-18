package app;

import app.config.HibernateConfig;
import app.dao.MovieDAO;
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
        EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManagerFactory("moviedb");

        PersonnelService personnelService = new PersonnelService();
//        System.out.println(personnelService.getPersonnel(533535));
//        System.out.println(personnelService.getPersonnel(990691));

        MovieDAO movieDAO = new MovieDAO(entityManagerFactory);
        MovieService movieService = new MovieService(movieDAO);
        //movieService.fetchGenreMappings();
        movieService.fetchAndSaveDanishMovies();
        GenreService genreService = new GenreService();
//        System.out.println(genreService.getGenres());

//        genreService.getMoviesByGenre(28);



    }
}