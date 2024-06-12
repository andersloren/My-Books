package com.liebniz.book;

import com.liebniz.author.Author;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import com.liebniz.persistence.CustomSQLConnection;
import com.liebniz.system.CustomProperties;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookServiceTest {

    @Inject
    private BookService bookService;

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
        book1.setEdition("Edition 1 Book 1");

        Book book2 = new Book();
        book2.setTitle("Title Book 2");
        book2.setEdition("Edition 2 Book 2");

        Book book3 = new Book();
        book3.setTitle("Title Book 3");
        book3.setEdition("Edition 3 Book 3");

        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {
            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                em.persist(book1);
                em.persist(book2);
                em.persist(book3);

                em.getTransaction().commit();
            }
        }

        bookService = new BookService();

    }

    @Test
    void findAllBooks() {
        List<Book> allBooks = this.bookService.findAllBooks();

        assertEquals(allBooks.size(), 3);
    }

    @Test
    void testFindBookByIdSuccess() {
        Book foundBook = this.bookService.findBookById(1L);

        assertEquals(foundBook.getId(), 1L);
        assertEquals(foundBook.getTitle(), "Title Book 1");
        assertEquals(foundBook.getEdition(), "Edition 1 Book 1");
    }

    @Test
    void testSaveBookSuccess() {
        Book book = new Book();
        book.setTitle("SavedBook Title");
        book.setEdition("SavedBook Edition");

        System.out.println(book.getId());

        Book returnedBook = this.bookService.saveBook(book);

        assertEquals(returnedBook.getId(), 4);
        assertEquals(returnedBook.getTitle(), "SavedBook Title");
        assertEquals(returnedBook.getEdition(), "SavedBook Edition");
    }

    @Test
    void testSaveBookInvalidIsNull() {
        Book book = new Book();
        book.setTitle(null);

        this.bookService.saveBook(book);
    }

    @Test
    void testUpdateBookSuccess() {

        Author author = new Author();
        author.setFirstname("Firstname");
        author.setLastname("Lastname");

        Book book = new Book();
        book.setTitle("Title of new book");
        book.setEdition("Edition of new book");

        Book savedBook = this.bookService.saveBook(book);

        assertEquals(savedBook.getTitle(), book.getTitle());
        assertEquals(savedBook.getEdition(), book.getEdition());

        BookDtoForm bookDtoForm = new BookDtoForm("Updated title", "123-456-789-012", "Updated Edition", Set.of(author));

        System.out.println(author);

        Book updatedBook = this.bookService.updateBook(bookDtoForm, 4L);
        assertEquals(updatedBook.getTitle(), bookDtoForm.title());
        assertEquals(updatedBook.getEdition(), bookDtoForm.edition());
    }

    @Test
    void testDeleteBookById() {
        this.bookService.deleteBookById(1L);

        int bookTableSize = this.bookService.findAllBooks().size();

        assertEquals(bookTableSize, 2);
    }
}