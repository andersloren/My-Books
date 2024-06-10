package com.liebniz.book;

import com.liebniz.persistence.CustomPersistenceUnitInfo;
import com.liebniz.persistence.MySQLConnection;
import com.liebniz.system.CustomProperties;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServiceBookTest {

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
    void findAllBooks() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();

            Book book1 = new Book();
            book1.setTitle("Title Book 1");
            book1.setEdition("Edition 1 Book 1");

            Book book2 = new Book();
            book2.setTitle("Title Book 2");
            book2.setEdition("Edition 2 Book 2");

            Book book3 = new Book();
            book3.setTitle("Title Book 3");
            book3.setEdition("Edition 3 Book 3");

            em.persist(book1);
            em.persist(book2);
            em.persist(book3);

            em.flush();

            String jpql = "SELECT a FROM Book a";
            TypedQuery<Book> typedQuery = em.createQuery(jpql, Book.class);


            List<Book> allBooks = typedQuery.getResultList();

            assertEquals(3, allBooks.size());

            em.getTransaction().commit();
        }
    }
}