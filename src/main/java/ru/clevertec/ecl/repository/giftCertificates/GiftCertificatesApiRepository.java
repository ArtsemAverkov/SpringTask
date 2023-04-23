package ru.clevertec.ecl.repository.giftCertificates;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        return null;
    }

    @Override
    public boolean update(GiftCertificates giftCertificates, Long id) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<GiftCertificates> readAll() {
        return null;
    }
}
