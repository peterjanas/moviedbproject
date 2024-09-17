package app.dao;

import app.entity.Movie;
import app.entity.Personnel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonnelDAO implements IDAO<Personnel>
{
    private EntityManagerFactory emf;

    public PersonnelDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public Personnel getById(Long Id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Personnel.class, Id);
        }
    }

    @Override
    public Set<Personnel> getAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Personnel> query = em.createQuery("SELECT p FROM Personnel p", Personnel.class);
            List<Personnel> personnel = query.getResultList();
            return personnel.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void create(Personnel personnel)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(personnel);
            em.getTransaction().commit();
        }
    }

    @Override
    public void update(Personnel personnel)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(personnel);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Personnel personnel)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(personnel);
            em.getTransaction().commit();
        }
    }

    public void deletePersonnelName(String personnelName, Long personnelId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Long verifyPersonnelId;
            verifyPersonnelId = personnelId;
            if(!Objects.equals(verifyPersonnelId, personnelId))
            {
                System.out.println("Movie ID does not match the movie title");
            } else {
                TypedQuery<Movie> query = em.createQuery("DELETE FROM Personnel m WHERE m.name = :name", Movie.class);
                query.setParameter("name", personnelName);
            }
        }
    }
}
