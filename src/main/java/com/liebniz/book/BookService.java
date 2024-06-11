package com.liebniz.book;

import com.liebniz.persistence.CustomPersistenceUnitInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.List;
import java.util.Map;

@RequestScoped
public class BookService {


    public BookService() {
    }

    public List<Book> findAllBooks() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                String jpql = "SELECT a FROM Book a";
                TypedQuery<Book> typedQuery = em.createQuery(jpql, Book.class);

                List<Book> allBooks = typedQuery.getResultList();

                return allBooks;
            }
        }
    }

    public Book findBookById(long id) {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                return em.find(Book.class, id);
            }
        }
    }

    public Book saveBook(Book book) {
        Book returnedBook = new Book();
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                em.persist(book);
                returnedBook = book;

                em.getTransaction().commit();


            }
            return returnedBook;
        }
    }

    public Book updateBook(BookDtoForm bookDtoForm, long bookId) {
        if (bookDtoForm == null) throw new NullPointerException("Cannot update empty Book");

        Book updatedBook;

        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                Book foundBook = em.find(Book.class, bookId);

                if (bookDtoForm.title() != null) {
                    foundBook.setTitle(bookDtoForm.title());
                }
                if (bookDtoForm.isbn() != null) {
                    foundBook.setIsbn(bookDtoForm.title());
                }
                if (bookDtoForm.edition() != null) {
                    foundBook.setEdition(bookDtoForm.edition());
                }
                // TODO: 11/06/2024 Uncomment this once updateAuthor is done!
//                if (bookDtoForm.authors() != null) {
//                    for (Author author : bookDtoForm.authors()) {
//                        foundBook.addAuthor(author);
//                        author.addBook(foundBook);
//                        this.authorService.updateAuthor(bookDtoForm.authors(), bookId);
//                    }
//                }
                updatedBook = foundBook;
                em.persist(foundBook);
                em.getTransaction().commit();
            }
        }
        return updatedBook;
    }

    public void deleteBookById(long bookId) {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                Book foundBook = em.find(Book.class, bookId);
                em.remove(foundBook);

                em.getTransaction().commit();
            }
        }
    }
}