package ru.clevertec.ecl.repository.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.util.hibernate.JPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserApiRepository implements UserRepository{
    private final JPA jpa;

    public UserApiRepository(JPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<User> read(Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        Query query = entityManager.createQuery("SELECT user FROM User As user WHERE user.id = :id");
        query.setParameter("id", id);
        List<User> resultList = query.getResultList();
        return resultList;
    }
}
