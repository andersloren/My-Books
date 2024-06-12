package com.liebniz.author;

import com.liebniz.book.BookService;
import com.liebniz.model.Author;
import com.liebniz.model.Book;
import com.liebniz.model.dto.AuthorDtoForm;
import com.liebniz.model.dto.BookDtoForm;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import com.liebniz.persistence.CustomSQLConnection;
import com.liebniz.system.CustomProperties;
import com.liebniz.system.exception.CustomObjectNotFoundException;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    @Inject
    private AuthorService authorService;

    @BeforeEach
    void setUp() throws SQLException {
        CustomProperties customProps = CustomProperties.loadProperties("sql");

        try (Statement statement = CustomSQLConnection.getConnection().createStatement()) {
            statement.execute(customProps.getProperty("setForeignKeyChecksToZero"));
            statement.execute(customProps.getProperty("dropAuthorsBooksTestTable"));
            statement.execute(customProps.getProperty("dropAuthorsTestTable"));
            statement.execute(customProps.getProperty("dropBooksTestTable"));
            statement.execute(customProps.getProperty("createBooksTable"));
            statement.execute(customProps.getProperty("createAuthorsTable"));
            statement.execute(customProps.getProperty("createAuthorsBooksTable"));
            statement.execute(customProps.getProperty("setForeignKeyChecksToOne"));
        }

        Book book1 = new Book();
        book1.setTitle("Title Book 1");
        book1.setIsbn("123-456-789-01");
        book1.setEdition("1st Edition");

        Book book2 = new Book();
        book2.setTitle("Title Book 2");
        book2.setIsbn("123-456-789-02");
        book2.setEdition("2dn Edition");

        Book book3 = new Book();
        book3.setTitle("Title Book 3");
        book3.setIsbn("123-456-789-03");
        book3.setEdition("3d Edition");

        Author author1 = new Author();
        author1.setFirstname("Firstname Author 1");
        author1.setLastname("Lastname Author 1");

        Author author2 = new Author();
        author2.setFirstname("Firstname Author 2");
        author2.setLastname("Lastname Author 2");

        Author author3 = new Author();
        author3.setFirstname("Firstname Author 3");
        author3.setLastname("Lastname Author 3");

        book1.getAuthors().add(author1);
        book2.getAuthors().add(author1);
        book2.getAuthors().add(author2);
        book3.getAuthors().add(author1);
        book3.getAuthors().add(author2);
        book3.getAuthors().add(author3);

        author1.getBooks().add(book1);
        author1.getBooks().add(book2);
        author1.getBooks().add(book3);
        author2.getBooks().add(book2);
        author2.getBooks().add(book3);
        author3.getBooks().add(book3);

        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                em.persist(book1);
                em.persist(book2);
                em.persist(book3);

                em.persist(author1);
                em.persist(author2);
                em.persist(author3);

                em.getTransaction().commit();
            }
        }

        authorService = new AuthorService();
    }
    
    @Test
    void testBeforeAll() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Book book1 = em.find(Book.class, 1L);
            assertEquals(book1.getId(), 1L);
            Book book2 = em.find(Book.class, 2L);
            assertEquals(book2.getId(), 2L);
            Book book3 = em.find(Book.class, 3L);
            assertEquals(book3.getId(), 3L);

            Author author1 = em.find(Author.class, 1L);
            assertEquals(author1.getId(), 1L);
            Author author2 = em.find(Author.class, 2L);
            assertEquals(author2.getId(), 2L);
            Author author3 = em.find(Author.class, 3L);
            assertEquals(author3.getId(), 3L);

            em.getTransaction().commit();
        }
    }

    @Test
    void testFindAllAuthors() {
        List<Author> allAuthors = this.authorService.findAllAuthors();

        assertEquals(allAuthors.size(), 3);
    }

    @Test
    void testFindAuthorByIdSuccess() {
        Author foundAuthor = this.authorService.findAuthorById(1L);

        assertEquals(foundAuthor.getId(), 1L);
        assertEquals(foundAuthor.getFirstname(), "Firstname Author 1");
        assertEquals(foundAuthor.getLastname(), "Lastname Author 1");
    }

    @Test
    void testFindAuthorInvalidId() {
        Throwable thrown = assertThrows(CustomObjectNotFoundException.class,
                () -> this.authorService.findAuthorById(10L));

        assertThat(thrown)
                .isInstanceOf(CustomObjectNotFoundException.class)
                .hasMessage("Could not find author with Id 10");

    }

    @Test
    void testSaveAuthorSuccess() {
        Author author = new Author();
        author.setFirstname("SavedAuthor Firstname");
        author.setLastname("SavedAuthor Lastname");

        System.out.println(author.getId());

        Author returnedAuthor = this.authorService.saveAuthor(author);

        assertEquals(returnedAuthor.getId(), 4);
        assertEquals(returnedAuthor.getFirstname(), "SavedAuthor Firstname");
        assertEquals(returnedAuthor.getLastname(), "SavedAuthor Lastname");
    }

    @Test
    void testUpdateAuthorSuccess() {

        AuthorDtoForm authorDtoForm = new AuthorDtoForm("New Author Firstname", "New Author Lastname");

        Author updatedAuthor = this.authorService.updateAuthor(authorDtoForm, 1L);
        assertEquals(updatedAuthor.getFirstname(), "New Author Firstname");
        assertEquals(updatedAuthor.getLastname(), "New Author Lastname");
    }

    @Test
    void testDeleteAuthorById() {
        this.authorService.deleteAuthorById(1L);

        int authorTableSize = this.authorService.findAllAuthors().size();

        assertEquals(authorTableSize, 2);
    }
}