package com.liebniz.model;

import com.liebniz.model.Author;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
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

//    @NotNull(message = "Invalid Title. Please provide a title.")
    @NotEmpty(message = "Invalid Title. Please provide a title.")
    @Size(
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

    @Size(
            min = 3,
            max = 20,
            message = "Book edition must be between 3 and 20 characters"
    )
    private String edition;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Set<Author> authors = new HashSet<>();

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Custom Methods
     */

    public void addAuthor(Author author) {
        if (author == null) throw new NullPointerException("Can't add null Author");
        if (!author.getBooks().isEmpty())
            throw new IllegalStateException("This author is already assigned to a different Book");

        getAuthors().add(author);
        author.addBook(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }

    /**
     * toString()
     */

    // TODO: 11/06/2024 Remove this later when going into production 
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", authors=" + authors +
                ", edition='" + edition + '\'' +
                '}';
    }
}
