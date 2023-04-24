package ru.clevertec.ecl.repository.giftCertificates;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.GiftCertificates;
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
            return session.get(GiftCertificates.class, id);
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
    public List<GiftCertificates> readAll() {
        SessionFactory sessionFactory = hibernateI.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<GiftCertificates> query =
                    session.createQuery("FROM GiftCertificates", GiftCertificates.class);
            return query.getResultList();
        }
    }
}
