package com.liebniz.book;

import com.liebniz.model.Book;
import com.liebniz.model.dto.BookDtoForm;
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
        assertEquals(foundBook.getIsbn(), "123-456-789-01");
        assertEquals(foundBook.getEdition(), "1st Edition");
    }

    @Test
    void testSaveBookSuccess() {
        Book book = new Book();
        book.setTitle("SavedBook Title");
        book.setIsbn("123-456-789-04");
        book.setEdition("1st Edition");

        System.out.println(book.getId());

        Book returnedBook = this.bookService.saveBook(book);

        assertEquals(returnedBook.getId(), 4);
        assertEquals(returnedBook.getTitle(), "SavedBook Title");
        assertEquals(returnedBook.getIsbn(), "123-456-789-04");
        assertEquals(returnedBook.getEdition(), "1st Edition");
    }

    @Test
    void testUpdateBookSuccess() {

        BookDtoForm bookDtoForm = new BookDtoForm("Updated Title 1 Book 1", "123-456-789-01", "2nd Edition");

        Book updatedBook = this.bookService.updateBook(bookDtoForm, 1L);
        assertEquals(updatedBook.getTitle(), bookDtoForm.title());
        assertEquals(updatedBook.getIsbn(), bookDtoForm.isbn());
        assertEquals(updatedBook.getEdition(), bookDtoForm.edition());
    }

    @Test
    void testDeleteBookById() {
        this.bookService.deleteBookById(1L);

        int bookTableSize = this.bookService.findAllBooks().size();

        assertEquals(bookTableSize, 2);
    }
}