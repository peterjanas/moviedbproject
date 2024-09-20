package app;

import app.config.HibernateConfig;
import app.dao.MovieDAO;
import app.dao.PersonnelDAO;
import app.entity.Personnel;
import app.service.MovieService;
import app.service.PersonnelService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class Main
{
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
    {
        EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManagerFactory("moviedb");
        MovieDAO movieDAO = new MovieDAO(entityManagerFactory);
        PersonnelDAO personnelDAO = new PersonnelDAO(entityManagerFactory);
        PersonnelService personnelService = new PersonnelService(personnelDAO);
        MovieService movieService = new MovieService(movieDAO, personnelService);


        //movieService.fetchAndSaveAllMoviesAndPersonnel();// method for fill movies and cast/crew






        //movieDAO.printMoviesByActor(2361843L);
        //movieDAO.printActorsInMovie(833339L);







    }
}