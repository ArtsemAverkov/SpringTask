package ru.clevertec.ecl.util.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;


@Component
public interface HibernateI {
    SessionFactory getSessionFactory();
}
