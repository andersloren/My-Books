package com.liebniz.author;

import com.liebniz.persistence.CustomPersistenceUnitInfo;

import java.util.List;

public class ServiceAuthor {

    public List<Author> findAllAuthors() {

        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("production");


    }
}
