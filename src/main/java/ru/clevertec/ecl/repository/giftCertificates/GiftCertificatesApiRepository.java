package ru.clevertec.ecl.repository.giftCertificates;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.GiftCertificates;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.util.hibernate.HibernateI;

import java.util.List;

@Repository
public class GiftCertificatesApiRepository implements GiftCertificatesRepository{

    private final HibernateI hibernateI;

    public GiftCertificatesApiRepository(HibernateI hibernateI) {
        this.hibernateI = hibernateI;
    }

    @Override
    public long create(GiftCertificates giftCertificates) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
       try (Session session = sessionFactory.openSession()) {
           session.beginTransaction();
           session.save(giftCertificates);
           session.getTransaction().commit();
           return 0;
       }
    }

    @Override
    public GiftCertificates read(long id) throws Exception {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            GiftCertificates certificates = session.get(GiftCertificates.class, id);
            String name = certificates.getTag().getName();
            System.out.println("name = " + name);

            return certificates;
        }
    }

    @Override
    public boolean update(GiftCertificates giftCertificates, Long id) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            GiftCertificates certificates = session.get(GiftCertificates.class, id);
            certificates.setName(giftCertificates.getName());
            certificates.setPrice(giftCertificates.getPrice());
            certificates.setDuration(giftCertificates.getDuration());
            certificates.setLast_update_date(giftCertificates.getLast_update_date());
            certificates.setTag(new Tag(giftCertificates.getTag().getName()));
            session.update(certificates);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean delete(Long id) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            GiftCertificates giftCertificates = session.get(GiftCertificates.class, id);
            session.delete(giftCertificates);
            return true;
        }
    }

    @Override
    public List<Object[]> readAll(String tagName, String orderBy, String orderType) {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            Query<Object[]> query = session.createQuery("SELECT gc, t.name FROM GiftCertificates gc " +
                    "JOIN gc.tag t where (:tagName IS NULL OR t.name = :tagName) " +
                    "ORDER BY " + orderBy + " " + orderType, Object[].class);
            query.setParameter("tagName", tagName);
            return query.getResultList();
        }
    }
}
