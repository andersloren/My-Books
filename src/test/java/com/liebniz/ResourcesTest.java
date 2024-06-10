package com.liebniz;

import com.liebniz.persistence.MySQLConnection;
import com.liebniz.system.CustomProperties;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

public class ResourcesTest {

    @Test
    void testSQLScript() throws SQLException {

        CustomProperties customProps = CustomProperties.loadProperties("test");

        try (Statement statement = MySQLConnection.getConnection().createStatement()) {
            statement.execute(customProps.getProperty("setForeignKeyChecksToZero"));
            statement.execute(customProps.getProperty("dropAuthorsTestTable"));
            statement.execute(customProps.getProperty("dropBooksTestTable"));
            statement.execute(customProps.getProperty("createBooksTable"));
            statement.execute(customProps.getProperty("createAuthorsTable"));
            statement.execute(customProps.getProperty("setForeignKeyChecksToOne"));
        }
    }
}
