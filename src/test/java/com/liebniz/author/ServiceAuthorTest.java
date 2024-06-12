package com.liebniz.author;

import com.liebniz.model.Author;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServiceAuthorTest {

    @Test
    void findAllAuthors() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            Author author1 = new Author();
            author1.setFirstname("Firstname Author 1");
            author1.setLastname("Lastname Author 1");

            Author author2 = new Author();
            author2.setFirstname("Firstname Author 2");
            author2.setLastname("Lastname Author 2");

            Author author3 = new Author();
            author3.setFirstname("Firstname Author 3");
            author3.setLastname("Lastname Author 3");


        }
    }
}