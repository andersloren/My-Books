package com.liebniz.book;

import com.liebniz.author.Author;
import com.liebniz.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBookMetamodel {

    @Test
    void testBookMetamodel() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");

        Set<String> entities = new HashSet<>();
        entities.add("Author");
        entities.add("Book");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        Metamodel mm = emf.getMetamodel();

        Set<ManagedType<?>> managedTypes = mm.getManagedTypes();
        assertEquals(managedTypes.size(), 2);

        for (ManagedType<?> managedType : managedTypes) {
            int periodPlace = managedType.getJavaType().getTypeName().lastIndexOf(".") + 1;
            int stringLength = managedType.getJavaType().getTypeName().length();
            System.out.println(managedType.getJavaType().getTypeName().substring(periodPlace, stringLength));
            assertTrue(entities.contains(managedType.getJavaType().getTypeName().substring(periodPlace, stringLength)));
        }
    }
}
