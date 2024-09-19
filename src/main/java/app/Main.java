package app;

import app.config.HibernateConfig;
import app.dao.MovieDAO;
import app.dto.MovieDTO;
import app.service.MovieService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main
{
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedb");

//Masih test
//        MovieDAO movieDAO = new MovieDAO(emf);
//        System.out.println(movieDAO.getTop10LowestRatedMovies());

// Personnel Test
//        PersonnelService personnelService = new PersonnelService();
//        System.out.println(personnelService.getPersonnel(646097));

//Movie Test
//        MovieService movieService = new MovieService();
//        movieService.getAllMoviesAndTheirPersonnel();
//        MovieDAO movieDAO = new MovieDAO(emf);
//        List<MovieDTO> movieList = movieService.getAllMoviesForDatabase();
//        movieDAO.saveMoviesToDb(movieList);
    }
}