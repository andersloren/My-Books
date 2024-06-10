package com.liebniz.persistence;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.metamodel.Metamodel;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.Map;

public class CustomEntityManagerFactory implements EntityManagerFactory {

    private final CustomPersistenceUnitInfo customPersistenceUnitInfo;

    public CustomEntityManagerFactory(CustomPersistenceUnitInfo customPersistenceUnitInfo) {
        this.customPersistenceUnitInfo = customPersistenceUnitInfo;
    }

    @Override
    public EntityManager createEntityManager() {
        EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(customPersistenceUnitInfo, Map.of());
        return emf.createEntityManager();
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return null;
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        return null;
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public Cache getCache() {
        return null;
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return null;
    }

    @Override
    public void addNamedQuery(String s, Query query) {

    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public <T> void addNamedEntityGraph(String s, EntityGraph<T> entityGraph) {

    }
}
