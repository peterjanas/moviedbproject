package app.dao;

import app.entity.Movie;
import app.entity.Personnel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
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

    public void savePersonnel(List<Personnel> personnelList)
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            for (Personnel personnel : personnelList)
            {
                Personnel existingPersonnel = em.find(Personnel.class, personnel.getId());
                if (existingPersonnel == null)
                {
                    em.persist(personnel);
                } else
                {
                    existingPersonnel.setName(personnel.getName());
                    existingPersonnel.setRoleType(personnel.getRoleType());
                    em.merge(existingPersonnel);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e)
        {
            if (em.getTransaction().isActive())
            {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
        } finally
        {
            em.close();
        }
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

    public void updatePerson(Long personnelId, String newName)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get personnel where id is the same as the parameter id
            TypedQuery<Personnel> verifyIdQuery = em.createQuery("SELECT p FROM Personnel p WHERE p.id = :id", Personnel.class);
            verifyIdQuery.setParameter("id", personnelId);

            // Verify the personnel exists
            Personnel personnel = verifyIdQuery.getSingleResult();

            // If the personnel exists, update the name
            if (personnel != null)
            {
                Query updateQuery = em.createQuery("UPDATE Personnel p SET p.name = :name WHERE p.id = :id");
                updateQuery.setParameter("name", newName);
                updateQuery.setParameter("id", personnelId);
                updateQuery.executeUpdate();
                em.getTransaction().commit();
            } else
            {
                System.out.println("Personnel not found");
                em.getTransaction().rollback();
            }
        }
    }

    public void deletePerson(Long personnelId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get personnel where id is the same as the parameter id
            TypedQuery<Personnel> verifyIdQuery = em.createQuery("SELECT p FROM Personnel p WHERE p.id = :id", Personnel.class);
            verifyIdQuery.setParameter("id", personnelId);

            // Verify the personnel exists
            Personnel personnel = verifyIdQuery.getSingleResult();

            // If the personnel exists, delete the personnel
            if (personnel != null)
            {
                Query deleteQuery = em.createQuery("DELETE FROM Personnel p WHERE p.id = :id");
                deleteQuery.setParameter("id", personnelId);
                deleteQuery.executeUpdate();
                em.getTransaction().commit();
            } else
            {
                System.out.println("Personnel not found");
                em.getTransaction().rollback();
            }
        }
    }

    public void deletePersonnelName(Long personnelId)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // Get personnel where id is the same as the parameter id
            TypedQuery<Personnel> verifyIdQuery = em.createQuery("SELECT p FROM Personnel p WHERE p.id = :id", Personnel.class);
            verifyIdQuery.setParameter("id", personnelId);

            // Verify the personnel exists
            Personnel personnel = verifyIdQuery.getSingleResult();

            // If the personnel exists, set the name to NULL
            if (personnel != null)
            {
                Query deleteNameQuery = em.createQuery("UPDATE Personnel p SET p.name = NULL WHERE p.id = :id");
                deleteNameQuery.setParameter("id", personnelId);
                deleteNameQuery.executeUpdate();
                em.getTransaction().commit();
            } else
            {
                System.out.println("Personnel not found");
                em.getTransaction().rollback();
            }
        }
    }
}

