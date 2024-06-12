package com.liebniz.persistence;

import com.liebniz.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomEntityManagerFactoryTest {

    @Test
    void testCustomEntityManagerFactoryReturnsFunctionalEntityManager() {
        CustomPersistenceUnitInfo unitInfo = new CustomPersistenceUnitInfo("test");

        try (CustomEntityManagerFactory customEmf = new CustomEntityManagerFactory(unitInfo)) {

            try (EntityManager em = customEmf.createEntityManager()) {
                em.getTransaction().begin();

                Book book = new Book();
                book.setTitle("New Book");
                book.setEdition("1st Edition");

                em.persist(book);

                em.flush();

                String jpql = "SELECT b FROM Book b WHERE b.title = :title";
                TypedQuery<Book> tq = em.createQuery(jpql, Book.class);
                tq.setParameter("title", "New Book");

                Book foundBook = tq.getSingleResult();

                assertEquals(book, foundBook);

                em.getTransaction().commit();
            }
        }
    }
}