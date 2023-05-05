package ru.clevertec.ecl.repository.tag;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.tag.Tag;

import ru.clevertec.ecl.util.hibernate.JPA;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagApiRepository implements TagRepository{


    private final JPA jpa;

    @Autowired
    public TagApiRepository(JPA jpa) {
        this.jpa = jpa;
    }

    @Override
    public long create(Tag tag) {
        EntityManager entityManager = jpa.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(tag);
        transaction.commit();
        return tag.getId();
        }

    @Override
    public TagDtoResponse read(long id) throws Exception {
        EntityManager entityManager = jpa.getEntityManager();
        Tag tag = entityManager.createQuery("SELECT t FROM Tag t LEFT JOIN FETCH t.giftCertificatesList WHERE t.id = :id", Tag.class)
                .setParameter("id", id)
                .getSingleResult();
        List<GiftCertificatesResponseDto> giftCertificatesDto = tag.getGiftCertificatesList().stream()
                .map(gc -> new GiftCertificatesResponseDto(gc.getId(), gc.getName(), gc.getDescription(), gc.getPrice(), gc.getDuration()))
                .collect(Collectors.toList());
        return new TagDtoResponse(tag.getId(), tag.getName(), giftCertificatesDto);
    }

    @Override
    public boolean update(Tag tag, Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        Tag tags = entityManager.find(Tag.class, id);
        tags.setName(tag.getName());
        entityManager.flush();
        return true;
    }

    @Override
    public boolean delete(Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        Tag tags = entityManager.find(Tag.class, id);
        entityManager.remove(tags);
        return true;
    }


    @Override
    public List<Object> readAll() {
        EntityManager entityManager = jpa.getEntityManager();
        TypedQuery<Object> selectTFromTagT = entityManager.createQuery(
                "SELECT DISTINCT t FROM Tag t LEFT JOIN FETCH t.giftCertificatesList", Object.class);
        return selectTFromTagT.getResultList();
    }
}
