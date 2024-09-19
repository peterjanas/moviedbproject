package app;

import app.config.HibernateConfig;
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
        EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManagerFactory("moviedb");
        MovieDAO movieDAO = new MovieDAO(entityManagerFactory);
        PersonnelDAO personnelDAO = new PersonnelDAO(entityManagerFactory);
        MovieService movieService = new MovieService(movieDAO);
        PersonnelService personnelService = new PersonnelService(personnelDAO);

       personnelService.fetchAndSaveCastAndCrew(833339L);
//        System.out.println(personnelService.getPersonnel(533535));
//        System.out.println(personnelService.getPersonnel(990691));


        //movieService.fetchAndSaveDanishMovies(); // method for fill movies






    }
}