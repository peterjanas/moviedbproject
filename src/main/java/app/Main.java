package app;

import app.config.HibernateConfig;
import app.dao.GenreDAO;
import app.dao.MovieDAO;
import app.dao.PersonnelDAO;
import app.dto.GenreDTO;
import app.entity.Movie;
import app.service.GenreService;
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
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedb");
        MovieDAO movieDAO = new MovieDAO(emf);
        PersonnelDAO personnelDAO = new PersonnelDAO(emf);
        PersonnelService personnelService = new PersonnelService(personnelDAO);
        MovieService movieService = new MovieService(movieDAO, personnelService);


//
//
//        System.out.println(personnelService.getPersonnel(533535));
//        System.out.println(personnelService.getPersonnel(990691));
//
//

        GenreDAO genreDAO = new GenreDAO(emf);
        GenreService genreService = new GenreService();
        List<GenreDTO> genreDTOList = genreService.getGenresToDB();
        genreDAO.saveGenresToDB(genreDTOList);

        movieService.fetchAndSaveAllMoviesAndPersonnel(); // method for fill movies
//        personnelService.fetchAndSaveCastAndCrew(833339L);





        //movieDAO.printMoviesByActor(2361843L);
        //movieDAO.printActorsInMovie(833339L);


//        System.out.println(genreService.getGenres());





    }
}