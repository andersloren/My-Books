package com.liebniz.book;

import com.liebniz.model.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookOperationsTest {

    @Test
    void testBookTitleIsNull() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Book book = new Book();
            book.setTitle(null);

            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            System.out.println("Violations size: " + violations.size());
            violations.forEach(System.out::println);
            assertEquals(1, violations.size());

            ConstraintViolation<Book> violation = violations.iterator().next();
            String failedPropertyName = violation.getPropertyPath().iterator().next().getName();

            System.out.println("Violation field: " + failedPropertyName);
            assertEquals(failedPropertyName, "title");

            System.out.println("Violation message: " + violation.getMessage());
            assertEquals(violation.getMessage(), "Invalid Title. Please provide a title.");
        }
    }

    @Test
    void testBookTitleIsEmpty() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Book book = new Book();
            book.setTitle("");

            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            System.out.println("Violations size: " + violations.size());
            assertEquals(1, violations.size());

            ConstraintViolation<Book> violation = violations.iterator().next();
            String failedPropertyName = violation.getPropertyPath().iterator().next().getName();

            System.out.println("Violation field: " + failedPropertyName);
            assertEquals(failedPropertyName, "title");

            System.out.println("Violation message: " + violation.getMessage());
            assertEquals(violation.getMessage(), "Invalid Title. Please provide a title.");
        }
    }

    @Test
    void testBookTitleMaxSize() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Book book = new Book();
            book.setTitle("a".repeat(256));

            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            System.out.println("Violations size: " + violations.size());
            assertEquals(1, violations.size());

            ConstraintViolation<Book> violation = violations.iterator().next();
            String failedPropertyName = violation.getPropertyPath().iterator().next().getName();

            System.out.println("Violation field: " + failedPropertyName);
            assertEquals(failedPropertyName, "title");

            System.out.println("Violation message: " + violation.getMessage());
            assertEquals(violation.getMessage(), "Book title must be between 2 and 255 characters");
        }
    }

    @Test
    void testBookIsbnMinSize() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Book book = new Book();
            book.setTitle("Title");
            book.setIsbn("a".repeat(13));

            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            System.out.println("Violations size: " + violations.size());
            assertEquals(1, violations.size());

            ConstraintViolation<Book> violation = violations.iterator().next();
            String failedPropertyName = violation.getPropertyPath().iterator().next().getName();

            System.out.println("Violation field: " + failedPropertyName);
            assertEquals(failedPropertyName, "isbn");

            System.out.println("Violation message: " + violation.getMessage());
            assertEquals(violation.getMessage(), "ISBN must be between 14 and 17 characters");
        }
    }

    @Test
    void testBookIsbnMaxSize() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Book book = new Book();
            book.setTitle("Title");
            book.setIsbn("a".repeat(18));

            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            System.out.println("Violations size: " + violations.size());
            assertEquals(1, violations.size());

            ConstraintViolation<Book> violation = violations.iterator().next();
            String failedPropertyName = violation.getPropertyPath().iterator().next().getName();

            System.out.println("Violation field: " + failedPropertyName);
            assertEquals(failedPropertyName, "isbn");

            System.out.println("Violation message: " + violation.getMessage());
            assertEquals(violation.getMessage(), "ISBN must be between 14 and 17 characters");
        }
    }

    @Test
    void testBookEditionMinSize() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Book book = new Book();
            book.setTitle("Title");
            book.setEdition("a".repeat(2));

            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            System.out.println("Violations size: " + violations.size());
            assertEquals(1, violations.size());

            ConstraintViolation<Book> violation = violations.iterator().next();
            String failedPropertyName = violation.getPropertyPath().iterator().next().getName();

            System.out.println("Violation field: " + failedPropertyName);
            assertEquals(failedPropertyName, "edition");

            System.out.println("Violation message: " + violation.getMessage());
            assertEquals(violation.getMessage(), "Book edition must be between 3 and 20 characters");
        }
    }

    @Test
    void testBookEditionMaxSize() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Book book = new Book();
            book.setTitle("Title");
            book.setEdition("a".repeat(21));

            Set<ConstraintViolation<Book>> violations = validator.validate(book);
            System.out.println("Violations size: " + violations.size());
            assertEquals(1, violations.size());

            ConstraintViolation<Book> violation = violations.iterator().next();
            String failedPropertyName = violation.getPropertyPath().iterator().next().getName();

            System.out.println("Violation field: " + failedPropertyName);
            assertEquals(failedPropertyName, "edition");

            System.out.println("Violation message: " + violation.getMessage());
            assertEquals(violation.getMessage(), "Book edition must be between 3 and 20 characters");
        }
    }
}