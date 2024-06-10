package com.liebniz.author;

import com.liebniz.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.List;
import java.util.Map;

public class ServiceAuthor {

    public List<Author> findAllAuthors() {

        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("dev");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            String jpql = "SELECT a FROM Author a";
            TypedQuery<Author> typedQuery = em.createQuery(jpql, Author.class);


            List<Author> allAuthors = typedQuery.getResultList();

            em.getTransaction().commit();

            return allAuthors;
        }
    }
}
