package app.dao;

import app.dto.GenreDTO;
import app.entity.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GenreDAO
{
    private EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public void saveGenresToDB(List<GenreDTO> genreDTOList)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            for (GenreDTO genreDTO : genreDTOList)
            {
                Genre genre = new Genre();
                genre.setId(genreDTO.getId());
                genre.setName(genreDTO.getName());
                em.persist(genre);
            }
            em.getTransaction().commit();
        }
    }

}
