package ru.clevertec.ecl.util.hibernate;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public interface JPA {
    public EntityManager getEntityManager();
}
