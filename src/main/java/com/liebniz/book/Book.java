package com.liebniz.book;

import com.liebniz.author.Author;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(
            min = 2,
            max = 255,
            message = "Book title must be between 2 and 255 characters"
    )
    private String title;

    @Size(
            min = 14,
            max = 17,
            message = "ISBN must be between 14 and 17 characters"
    )
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

    @NotNull
    @Size(
            min = 2,
            max = 20,
            message = "Book edition must be between 2 and 20 characters"
    )
    private String edition;

    /**
     * Getter and Setters
     */

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    /**
     * Custom Methods
     */

    public void addAuthor(Author author) {
        if (author == null) throw new NullPointerException("Can't add null Author");
        if (author.getBooks() != null)
            throw new IllegalStateException("This author is already assigned to a different Book");

        getAuthors().add(author);
        author.addBook(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }
}
