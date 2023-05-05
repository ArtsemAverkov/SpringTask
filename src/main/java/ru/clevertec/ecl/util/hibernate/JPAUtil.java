package ru.clevertec.ecl.util.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Component
@Primary
public class JPAUtil implements JPA{

    static final EntityManager ENTITY_MANAGER =  buildEntityManager();

    private static EntityManager buildEntityManager(){
        EntityManagerFactory employeesTesting =
                Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = employeesTesting.createEntityManager();
        return entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return ENTITY_MANAGER;
    }
}
