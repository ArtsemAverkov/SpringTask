package ru.clevertek.ecl.common.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesApiRepository;
import ru.clevertec.ecl.util.hibernate.HibernateI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;

import static org.mockito.Mockito.*;

public class GiftCertificatesRepositoryTest {

    @Mock
    private HibernateI hibernateI;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private  GiftCertificatesApiRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(hibernateI.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        String name = "New name";
        Double price = 20.0;
        Long duration = 30L;
        String tagName = "Tag1";
        Tag tag = new Tag(tagName);
        LocalDateTime now = LocalDateTime.now();
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
        GiftCertificates certificates = new GiftCertificates(id, name,name, price, duration,isoDateTime,isoDateTime, tag);
        when(session.get(GiftCertificates.class, id)).thenReturn(certificates);

        GiftCertificates newCertificates = new GiftCertificates();
        newCertificates.setName("New name");
        newCertificates.setPrice(15.0);
        newCertificates.setDuration(60L);
        Tag newTag = new Tag("New tag");
        newCertificates.setTag(newTag);

        boolean result = repository.update(newCertificates, id);

        verify(session).update(certificates);
        verify(transaction).commit();
        Assertions.assertTrue(result);
    }

    @Test
    void testCreate() {
        Long id = 1L;
        String name = "Certificate";
        Double price = 50.0;
        Long duration = 90L;
        Tag tag = new Tag("Tag1");
        LocalDateTime now = LocalDateTime.now();
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
        GiftCertificates certificates = new GiftCertificates(id,name, name, price, duration, isoDateTime,isoDateTime, tag);
        when(sessionFactory.openSession()).thenReturn(session);
        Long result = repository.create(certificates);
        verify(session).save(certificates);
        verify(transaction).commit();
        Assertions.assertNotNull(result);
    }

    @Test
    void testRead() throws Exception {
        Long id = 1L;
        String name = "Certificate";
        Double price = 50.0;
        Long duration = 90L;
        LocalDateTime now = LocalDateTime.now();
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
        String tagName = "Tag1";
        Tag tag = new Tag(tagName);
        GiftCertificates certificates = new GiftCertificates(id, name,name, price, duration, isoDateTime,isoDateTime, tag);
        Mockito.when(session.get(GiftCertificates.class, id)).thenReturn(certificates);
        GiftCertificates result = repository.read(id);
        Mockito.verify(session).get(GiftCertificates.class, id);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(name, result.getName());
        Assertions.assertEquals(price, result.getPrice());
        Assertions.assertEquals(duration, result.getDuration());
        Assertions.assertEquals(isoDateTime, result.getLast_update_date());
        Assertions.assertEquals(tagName, result.getTag().getName());
    }

    @Test
    void testDelete() {
        Long id = 1L;
        GiftCertificates certificates = new GiftCertificates();
        when(session.get(GiftCertificates.class, id)).thenReturn(certificates);
        boolean result = repository.delete(id);
        verify(session).delete(certificates);
        Assertions.assertTrue(result);
    }

    @Test
    void testReadAll() {
        String tagName = "Tag1";
        String orderBy = "name";
        String orderType = "asc";
        Query<Object[]> query = mock(Query.class);
        List<Object[]> resultList = new ArrayList<>();
        resultList.add(new Object[]{new GiftCertificates(), tagName});
        when(session.createQuery(anyString(), eq(Object[].class))).thenReturn(query);
        when(query.setParameter("tagName", tagName)).thenReturn(query);
        when(query.getResultList()).thenReturn(resultList);
        List<Object[]> result = repository.readAll(tagName, orderBy, orderType);
        verify(session).createQuery(anyString(), eq(Object[].class));
        verify(query).setParameter("tagName", tagName);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(resultList.size(), result.size());
        Assertions.assertEquals(tagName, result.get(0)[1]);
    }
}


