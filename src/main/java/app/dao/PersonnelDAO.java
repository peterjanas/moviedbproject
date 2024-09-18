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

    }

    @Override
    public void delete(Personnel personnel)
    {

    }

    public void updatePerson(String personnelName, Long personnelId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            // Get movie where id is the same as the paramater id
            TypedQuery<Personnel> verifyIdQuery = em.createQuery("SELECT p FROM Personnel p WHERE p.id = :id", Personnel.class);
            verifyIdQuery.setParameter("id", personnelId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getName();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            if (Objects.equals(verifyMovieId, personnelId) && Objects.equals(verifyMovieTitle, personnelName))
            {
                TypedQuery<Personnel> deleteTitleQuery = em.createQuery("UPDATE FROM Personnel p SET p.name = :name", Personnel.class);
                deleteTitleQuery.setParameter("name", personnelName);
            } else
            {
                System.out.println("Personnel ID does not match the name of the person");
            }
        }
    }

    public void deletePerson(String personnelName, Long personnelId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            // Get movie where id is the same as the paramater id
            TypedQuery<Personnel> verifyIdQuery = em.createQuery("SELECT p FROM Personnel p WHERE p.id = :id", Personnel.class);
            verifyIdQuery.setParameter("id", personnelId);

            // String that verifies the title of the movie
            String verifyMovieTitle = verifyIdQuery.getSingleResult().getName();
            // Long that verifies the id of the movie
            Long verifyMovieId = verifyIdQuery.getSingleResult().getId();

            if (Objects.equals(verifyMovieId, personnelId) && Objects.equals(verifyMovieTitle, personnelName))
            {
                TypedQuery<Personnel> deleteTitleQuery = em.createQuery("Delete FROM Personnel p WHERE p.id = :id", Personnel.class);
                deleteTitleQuery.setParameter("id", personnelId);
            } else
            {
                System.out.println("Personnel ID does not match the name of the person");
            }
        }
    }

    public void deletePersonnelName(String personnelName, Long personnelId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get person where id is the same as the paramater id
            TypedQuery<Personnel> verifyIdQuery = em.createQuery("SELECT p FROM Personnel p WHERE p.id = :id", Personnel.class);
            verifyIdQuery.setParameter("id", personnelId);

            // String that verifies the name of the person
            String verifyPersonnelName = verifyIdQuery.getSingleResult().getName();
            // Long that verifies the id of the person
            Long verifyPersonnelId = verifyIdQuery.getSingleResult().getId();

            // If the id and name from the parameter matches the database, delete the persons name
            if (Objects.equals(verifyPersonnelId, personnelId) && Objects.equals(verifyPersonnelName, personnelName))
            {
                TypedQuery<Personnel> deletePersonnelquery = em.createQuery("UPDATE Personnel p SET p.name = NULL WHERE p.id = :id", Personnel.class);
                deletePersonnelquery.setParameter("id", personnelId);
            } else
            {
                System.out.println("Personnel ID does not match the name of the person");
            }
        }
    }
}

