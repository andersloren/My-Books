package com.liebniz.book;

import com.liebniz.model.Author;

import java.util.Set;

public record BookDtoForm(
        String title,
        String isbn,
        String edition,
        Set<Author> authors
) {
}
