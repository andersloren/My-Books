package com.liebniz.book;

import com.liebniz.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.SingularAttribute;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBookMetamodel {

    @Test
    void testBookMetamodel() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");

        List<String> entities = new ArrayList<>();
        entities.add("Author");
        entities.add("Book");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        Metamodel mm = emf.getMetamodel();

        Set<ManagedType<?>> managedTypes = mm.getManagedTypes();
        assertEquals(managedTypes.size(), 2);

        int counter = 0;

        for (ManagedType<?> managedType : managedTypes) {
            int periodPlace = managedType.getJavaType().getTypeName().lastIndexOf(".") + 1;
            int stringLength = managedType.getJavaType().getTypeName().length();
            if (managedType.getJavaType().getTypeName().substring(periodPlace, stringLength).equals("Author")) {

                SingularAttribute<?, ?> firstnameAttribute = managedType.getSingularAttribute("firstname");
                assertEquals(
                        firstnameAttribute.getJavaType(),
                        String.class
                );
                assertEquals(
                        firstnameAttribute.getPersistentAttributeType(),
                        Attribute.PersistentAttributeType.BASIC
                );
                SingularAttribute<?, ?> lastnameAttribute = managedType.getSingularAttribute("lastname");
                assertEquals(
                        firstnameAttribute.getJavaType(),
                        String.class
                );
                assertEquals(
                        lastnameAttribute.getPersistentAttributeType(),
                        Attribute.PersistentAttributeType.BASIC
                );
            }
        }
    }
}
