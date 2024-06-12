package com.liebniz.author;

import com.liebniz.model.Author;
import com.liebniz.model.dto.AuthorDtoForm;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import com.liebniz.system.exception.CustomObjectNotFoundException;
import jakarta.persistence.*;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthorService {

    public AuthorService() {
    }

    public List<Author> findAllAuthors() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                String jpql = "SELECT a FROM Author a";
                TypedQuery<Author> typedQuery = em.createQuery(jpql, Author.class);

                List<Author> allAuthors = typedQuery.getResultList();

                return allAuthors;
            }
        }
    }

    public Author findAuthorById(long authorId) {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                try {
                    Author author = em.find(Author.class, authorId);
                    if (author == null) {
                        throw new CustomObjectNotFoundException("author", authorId);
                    }
                    return author;
                } catch (PersistenceException e) {
                    throw new RuntimeException("Error finding author", e);
                }
            }
        }
    }

    public Author saveAuthor(Author book) {
        Author returnedAuthor = new Author();
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                em.persist(book);
                returnedAuthor = book;

                em.getTransaction().commit();


            }
            return returnedAuthor;
        }
    }

    public void changeBooksForAuthor(long bookId) {

    }

    public Author updateAuthor(AuthorDtoForm authorDtoForm, long bookId) {
        if (authorDtoForm == null) throw new NullPointerException("Cannot update empty Author");

        Author updatedAuthor;

        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                Author foundAuthor = em.find(Author.class, bookId);

                foundAuthor.setFirstname(authorDtoForm.firstname());
                foundAuthor.setLastname(authorDtoForm.lastname());

                // TODO: 11/06/2024 Uncomment this once updateAuthor is done!
//                if (bookDtoForm.authors() != null) {
//                    for (Author author : bookDtoForm.authors()) {
//                        foundAuthor.addAuthor(author);
//                        author.addAuthor(foundAuthor);
//                        this.authorService.updateAuthor(bookDtoForm.authors(), bookId);
//                    }
//                }

                updatedAuthor = foundAuthor;
                em.persist(foundAuthor);

                em.getTransaction().commit();
            }
        }
        return updatedAuthor;
    }

//    public void

    public void deleteAuthorById(long bookId) {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");
        try (EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of())) {

            try (EntityManager em = emf.createEntityManager()) {
                em.getTransaction().begin();

                Author foundAuthor = em.find(Author.class, bookId);
                em.remove(foundAuthor);

                em.getTransaction().commit();
            }
        }
    }
}
