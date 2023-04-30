package ru.clevertec.ecl.repository.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.util.hibernate.HibernateI;

@Repository
public class UserApiRepository implements UserRepository{
    private final HibernateI hibernateI;

    public UserApiRepository(HibernateI hibernateI) {
        this.hibernateI = hibernateI;
    }

    @Override
    public User read(Long id) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()){
            return session.get(User.class, id);
        }
    }
}
