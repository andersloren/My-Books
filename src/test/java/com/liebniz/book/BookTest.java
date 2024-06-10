package com.liebniz.book;

import com.liebniz.persistence.CustomEntityManagerFactory;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import com.liebniz.persistence.MySQLConnection;
import com.liebniz.system.CustomProperties;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookTest {

    @BeforeEach
    void setUp() throws SQLException {
        CustomProperties customProps = CustomProperties.loadProperties("sql");

        try (Statement statement = MySQLConnection.getConnection().createStatement()) {
            statement.execute(customProps.getProperty("setForeignKeyChecksToZero"));
            statement.execute(customProps.getProperty("dropAuthorsBooksTestTable"));
            statement.execute(customProps.getProperty("dropAuthorsTestTable"));
            statement.execute(customProps.getProperty("dropBooksTestTable"));
            statement.execute(customProps.getProperty("createBooksTable"));
            statement.execute(customProps.getProperty("createAuthorsTable"));
            statement.execute(customProps.getProperty("createAuthorsBooksTable"));
            statement.execute(customProps.getProperty("setForeignKeyChecksToOne"));
        }
    }

    @Test
    void testSave() {
        CustomPersistenceUnitInfo unitInfo = new CustomPersistenceUnitInfo("test");

        try (CustomEntityManagerFactory customEmf = new CustomEntityManagerFactory(unitInfo)) {

            try (EntityManager em = customEmf.createEntityManager()) {

                em.getTransaction().begin();

                Book book = new Book();
                book.setTitle("TestSave Book");
                book.setEdition("1st Edition");

                em.persist(book);

                em.flush();

                String jpql = "SELECT b FROM Book b WHERE b.title = :title";
                TypedQuery<Book> tq = em.createQuery(jpql, Book.class);
                tq.setParameter("title", "TestSave Book");

                Book foundBook = tq.getSingleResult();

                assertEquals(book, foundBook);

                em.getTransaction().commit();
            }
        }
    }
}