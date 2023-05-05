//package ru.clevertec.ecl.util.hibernate;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.springframework.stereotype.Component;
//import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
//import ru.clevertec.ecl.entity.tag.Tag;
//import ru.clevertec.ecl.entity.user.User;
//
//
//@Component
//public class HibernateUtil implements HibernateI{
//
//    static final SessionFactory SESSION_FACTORY = buildSessionFactory();
//    private static SessionFactory buildSessionFactory() {
//        Configuration configuration = new Configuration();
//        configuration.addAnnotatedClass(GiftCertificates.class);
//        configuration.addAnnotatedClass(Tag.class);
//        configuration.addAnnotatedClass(User.class);
//        configuration.configure();
//        return configuration.buildSessionFactory();
//    }
//    @Override
//    public SessionFactory getSessionFactory() {
//        return SESSION_FACTORY;
//    }
//}
