package ru.clevertec.ecl.repository.tag;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.util.hibernate.HibernateI;

import java.util.List;

@Repository
public class TagApiRepository implements TagRepository{

    private final HibernateI hibernateI;

    public TagApiRepository(HibernateI hibernateI) {
        this.hibernateI = hibernateI;
    }

    @Override
    public long create(Tag tag) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(tag);
            transaction.commit();
            return tag.getId();
        }
    }

    @Override
    public Tag read(long id) throws Exception {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Tag tag = session.get(Tag.class, id);
            return tag;
        }
    }

    @Override
    public boolean update(Tag tag, Long id) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Tag tags = session.get(Tag.class, id);
            tags.setName(tag.getName());
            session.update(tags);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean delete(Long id) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Tag tags = session.get(Tag.class, id);
            session.delete(tags);
            return true;
        }
    }

    @Override
    public List<Object> readAll() {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<Object> selectTFromTagT = session.createQuery("SELECT t FROM Tag t", Object.class);
            return selectTFromTagT.getResultList();
        }
    }
}
