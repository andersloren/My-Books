package com.liebniz.book;

import com.liebniz.author.Author;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.List;
import java.util.Map;

public class ServiceBook {

    private final EntityManagerFactory emf;

    public ServiceBook(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Book> findAllBooks() {

        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("dev");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            String jpql = "SELECT a FROM Book a";
            TypedQuery<Book> typedQuery = em.createQuery(jpql, Book.class);


            List<Book> allBooks = typedQuery.getResultList();

            em.getTransaction().commit();

            return allBooks;
        }
    }

/*    public Book findBookById() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("dev");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            String jpql = "SELECT a FROM Book a";
            TypedQuery<Book> typedQuery = em.createQuery(jpql, Book.class);


            List<Book> allBooks = typedQuery.getResultList();

            em.getTransaction().commit();

            return allBooks;
        }
    }*/
}
