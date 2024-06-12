package com.liebniz.book;

import com.liebniz.persistence.CustomSQLConnection;
import com.liebniz.system.CustomProperties;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.sql.Statement;

class BookTest {

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
    }
}