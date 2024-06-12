package com.liebniz.author;

import com.liebniz.model.Author;
import com.liebniz.persistence.CustomEntityManagerFactory;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ServiceAuthor {

    public List<Author> findAllAuthors() {

        CustomPersistenceUnitInfo unitInfo = new CustomPersistenceUnitInfo("test");

        try (CustomEntityManagerFactory customEmf = new CustomEntityManagerFactory(unitInfo)) {

            try (EntityManager em = customEmf.createEntityManager()) {
            }
        }

        return null;
    }
}
