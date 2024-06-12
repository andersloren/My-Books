package com.liebniz;

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

public class TestMetamodel {

    @Test
    void testBookMetamodel() {
        CustomPersistenceUnitInfo customPersistenceUnitInfo = new CustomPersistenceUnitInfo("test");

        List<String> entities = new ArrayList<>();
        entities.add("Author");
        entities.add("Book");

        System.out.println("Entities in List: ");
        entities.forEach(System.out::println);

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());

        Metamodel mm = emf.getMetamodel();

        Set<ManagedType<?>> managedTypes = mm.getManagedTypes();
        System.out.println("ManagedTypes size: " + managedTypes.size());
        assertEquals(managedTypes.size(), 2);

        int counter = 0;

        for (ManagedType<?> managedType : managedTypes) {
            int periodPlace = managedType.getJavaType().getTypeName().lastIndexOf(".") + 1;
            int stringLength = managedType.getJavaType().getTypeName().length();
            if (managedType.getJavaType().getTypeName().substring(periodPlace, stringLength).equals("Author")) {

                SingularAttribute<?, ?> firstnameAttribute = managedType.getSingularAttribute("firstname");
                System.out.println(entities.get(counter) + " firstnameAttribute type: " + firstnameAttribute.getJavaType());
                assertEquals(
                        firstnameAttribute.getJavaType(),
                        String.class
                );
                assertEquals(
                        firstnameAttribute.getPersistentAttributeType(),
                        Attribute.PersistentAttributeType.BASIC
                );
                SingularAttribute<?, ?> lastnameAttribute = managedType.getSingularAttribute("lastname");
                System.out.println(entities.get(counter) + " lastnameAttribute type: " + lastnameAttribute.getJavaType());
                assertEquals(
                        firstnameAttribute.getJavaType(),
                        String.class
                );
                assertEquals(
                        lastnameAttribute.getPersistentAttributeType(),
                        Attribute.PersistentAttributeType.BASIC
                );
            }
            if (managedType.getJavaType().getTypeName().substring(periodPlace, stringLength).equals("Book")) {

                SingularAttribute<?, ?> titleAttribute = managedType.getSingularAttribute("title");
                System.out.println(entities.get(counter) + " titleAttribute type: " + titleAttribute.getJavaType());
                assertEquals(
                        titleAttribute.getJavaType(),
                        String.class
                );
                assertEquals(
                        titleAttribute.getPersistentAttributeType(),
                        Attribute.PersistentAttributeType.BASIC
                );
                SingularAttribute<?, ?> isbnAttribute = managedType.getSingularAttribute("isbn");
                System.out.println(entities.get(counter) + " isbnAttribute type: " + isbnAttribute.getJavaType());
                assertEquals(
                        isbnAttribute.getJavaType(),
                        String.class
                );
                assertEquals(
                        isbnAttribute.getPersistentAttributeType(),
                        Attribute.PersistentAttributeType.BASIC
                );
                SingularAttribute<?, ?> editionAttribute = managedType.getSingularAttribute("edition");
                System.out.println(entities.get(counter) + " editionAttribute type: " + editionAttribute.getJavaType());
                assertEquals(
                        editionAttribute.getJavaType(),
                        String.class
                );
                assertEquals(
                        editionAttribute.getPersistentAttributeType(),
                        Attribute.PersistentAttributeType.BASIC
                );
            }
            counter++;
        }
    }
}
