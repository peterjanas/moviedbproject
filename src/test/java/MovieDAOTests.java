import app.config.HibernateConfig;
import app.dao.*;
import app.entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MovieDAOTests {

    static EntityManagerFactory emf;
    static MovieDAO movieDAO;

    @BeforeAll
    static void setupAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        movieDAO = new MovieDAO(emf);
    }

    @BeforeEach
    public void setUp() {
        movieDAO = new MovieDAO(emf);

        try(EntityManager em = emf.createEntityManager()) {
            // Create and persist some movies movie
            Movie movie = new Movie(1L,"Inception", "A mind-bending thriller", 8.8);
            Movie movie2 = new Movie(2L,"The Dark Knight", "A dark superhero movie", 9.0);
            Movie movie3 = new Movie(3L,"Interstellar", "A space odyssey", 8.6);

            movieDAO.create(movie);
            movieDAO.create(movie2);
            movieDAO.create(movie3);
        }
    }

    @AfterEach
    public void tearDownEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void testGetById() {

        // Retrieve the movie by id
        Movie retrievedMovie = movieDAO.getById(1L);

        // Assert the result
        assertNotNull(retrievedMovie);
        assertEquals("Inception", retrievedMovie.getTitle());
        assertEquals("A mind-bending thriller", retrievedMovie.getOverview());
        assertEquals(8.8, retrievedMovie.getRating());
    }

    @Test
    public void testGetAll() {

        // Retrieve all movies
        Set<Movie> movies = movieDAO.getAll();

        // Assert the result
        assertNotNull(movies);
        assertEquals(3, movies.size());
    }

    @Test
    public void testUpdateMovie() {
        // Retrieve movie by id
        Movie movie = movieDAO.getById(1L);

        // Update the movie
        movieDAO.updateMovie(movie.getId(), "Inception 2", 1.0, LocalDate.of(2021, 9, 9));

        // Retrieve the updated movie and verify the changes
        Movie updatedMovie = movieDAO.getById(1L);
        assertNotNull(updatedMovie);
        assertEquals("Inception 2", updatedMovie.getTitle());
        assertEquals("A mind-bending thriller", updatedMovie.getOverview());
        assertEquals(1.0, updatedMovie.getRating());
        assertEquals(LocalDate.of(2021,9,9), updatedMovie.getReleaseDate());
    }

    @Test
    public void testDeleteMovie() {
        // Retrieve movie by id
        Movie movie = movieDAO.getById(1L);
        assertNotNull(movie);
        assertEquals("Inception", movie.getTitle());

        // Delete the movie
        movieDAO.deleteMovie("Inception", 1L);

        // Retrieve all movies
        Set<Movie> movies = movieDAO.getAll();

        // Assert the result
        assertNotNull(movies);
        assertEquals(2, movies.size());
    }

    @Test
    public void testDeleteMovieTitle() {
        // Retrieve movie by id
        Movie movie = movieDAO.getById(1L);
        assertNotNull(movie);
        assertEquals("Inception", movie.getTitle());

        // Delete the movie title
        movieDAO.deleteMovieTitle("Inception", 1L);

        // Assert the result
        assertNull(movieDAO.getById(1L).getTitle());
    }

    @Test
    public void testAverageRating()
    {
        // Verify initial state
        Set<Movie> movies = movieDAO.getAll();
        assertNotNull(movies);
        assertEquals(3, movies.size());

        // Calculate the average rating
        double averageRating = movieDAO.averageRating();

        // Assert the result
        double expectedAverage = (8.8 + 9.0 + 8.6) / 3;
        assertEquals(expectedAverage, averageRating, 0.1);
    }

    @Test
    public void testTop10HighestRatedMovies()
    {
        // Add more movies
        Movie movie4 = new Movie(4L,"The Shawshank Redemption", "Prison drama", 9.3);
        Movie movie5 = new Movie(5L,"The Godfather", "Mafia drama", 9.2);
        Movie movie6 = new Movie(6L,"Goodfellas", "Mafia drama", 2);
        Movie movie7 = new Movie(7L,"The Dark Knight Rises", "Superhero movie", 8.4);
        Movie movie8 = new Movie(8L,"The Matrix", "Sci-fi action", 3);
        Movie movie9 = new Movie(9L,"The Lord of the Rings: The Return of the King", "Fantasy adventure", 8.9);
        Movie movie10 = new Movie(10L,"The Lord of the Rings: The Fellowship of the Ring", "Fantasy adventure", 8.8);
        Movie movie11 = new Movie(11L,"The Lord of the Rings: The Two Towers", "Fantasy adventure", 8.7);
        Movie movie12 = new Movie(12L,"The Godfather: Part II", "Mafia drama", 9.0);

        movieDAO.create(movie4);
        movieDAO.create(movie5);
        movieDAO.create(movie6);
        movieDAO.create(movie7);
        movieDAO.create(movie8);
        movieDAO.create(movie9);
        movieDAO.create(movie10);
        movieDAO.create(movie11);
        movieDAO.create(movie12);

        // Retrieve top 10 highest rated movies
        List<Movie> top10Movies = movieDAO.getTop10HighestRatedMovies();

        // Assert the result
        assertNotNull(top10Movies);
        assertEquals(10, top10Movies.size());
        assertEquals("The Shawshank Redemption", top10Movies.get(0).getTitle());
        assertEquals("The Godfather", top10Movies.get(1).getTitle());
    }

    @Test
    public void testGetMovieByTitle()
    {
        // Retrieve movie by title
        List<Movie> retrievedMovies = movieDAO.getMovieByTitle("The Dark ");

        // Assert the result
        assertNotNull(retrievedMovies);
        assertTrue(retrievedMovies.stream().anyMatch(movie -> "The Dark Knight".equals(movie.getTitle())));
    }
}