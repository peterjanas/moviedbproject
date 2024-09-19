package app;

import app.service.MovieService;
import app.service.PersonnelService;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main
{
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
    {

//      EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedb");
























//
//      // Masih test
//      MovieDAO movieDAO = new MovieDAO(emf);
//      System.out.println(movieDAO.getTop10LowestRatedMovies());
//


        PersonnelService personnelService = new PersonnelService();
        System.out.println(personnelService.getPersonnel(646097));


    }
}