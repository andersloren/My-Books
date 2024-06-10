package com.liebniz.association;

import com.liebniz.author.Author;
import com.liebniz.book.Book;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import com.liebniz.persistence.MySQLConnection;
import com.liebniz.system.CustomProperties;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class BookAuthorTest {

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
    void testFindBookAuthorAssociation() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Author author = new Author();
            author.setFirstname("Firstname");
            author.setLastname("Lastname");

            Book book = new Book();
            book.setTitle("Title");
            book.setEdition("1st Edition");

            book.getAuthors().add(author);
            author.getBooks().add(book);

            em.persist(book);
            em.persist(author);

            String jpql = "SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId";
            TypedQuery<Book> tq = em.createQuery(jpql, Book.class);
            tq.setParameter("authorId", 1);
            Book foundBook = tq.getSingleResult();

            Assertions.assertEquals(book, foundBook);

            em.getTransaction().commit();
        }
    }
}
