package ru.clevertec.ecl.repository.giftCertificates;

import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.util.hibernate.JPA;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 The {@code GiftCertificatesApiRepository} class is responsible for performing CRUD operations on GiftCertificates
 objects using Hibernate ORM.
 @author [ArtsemAverkov]
 @version [1.0]
 */
@Repository
public class GiftCertificatesApiRepository implements GiftCertificatesRepository{

    private final JPA jpa;

    public GiftCertificatesApiRepository(JPA jpa) {
        this.jpa = jpa;
    }

    /**
     * Creates a new GiftCertificates object in the database.
     *
     * @param giftCertificates a GiftCertificates object to be added to the database
     * @return the ID of the newly created GiftCertificates object
     */
    @Override
    public long create(GiftCertificates giftCertificates) {
        EntityManager entityManager = jpa.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(giftCertificates);
        entityManager.getTransaction().commit();
        return giftCertificates.getId();
    }

    /**
     * Reads a GiftCertificates object from the database.
     *
     * @param id the ID of the GiftCertificates object to be retrieved
     * @return the GiftCertificates object with the specified ID
     * @throws Exception if a GiftCertificates object with the specified ID cannot be found
     */

    @Override
    public GiftCertificates read(long id) throws Exception {
        EntityManager entityManager = jpa.getEntityManager();
        GiftCertificates certificates = entityManager.find(GiftCertificates.class, id);
        if (certificates == null) {
            throw new Exception("GiftCertificates object with id " + id + " not found.");
        }
        certificates.getTag().getName();
        return certificates;
    }

    /**
     * Updates a GiftCertificates object in the database.
     *
     * @param giftCertificates a GiftCertificates object containing updated information
     * @param id the ID of the GiftCertificates object to be updated
     * @return {@code true} if the GiftCertificates object was updated successfully, {@code false} otherwise
     */

    @Override
    public boolean update(GiftCertificates giftCertificates, Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        GiftCertificates certificates = entityManager.find(GiftCertificates.class, id);
        if (certificates == null) {
            return false;
        }
        certificates.setName(giftCertificates.getName());
        certificates.setPrice(giftCertificates.getPrice());
        certificates.setDuration(giftCertificates.getDuration());
        certificates.setLast_update_date(giftCertificates.getLast_update_date());
        certificates.setTag(new Tag(giftCertificates.getTag().getName()));
        entityManager.getTransaction().begin();
        entityManager.merge(certificates);
        entityManager.getTransaction().commit();
        return true;
    }

    /**
     * Deletes a GiftCertificates object from the database.
     *
     * @param id the ID of the GiftCertificates object to be deleted
     * @return {@code true} if the GiftCertificates object was deleted successfully, {@code false} otherwise
     */

    @Override
    public boolean delete(Long id) {
        EntityManager entityManager = jpa.getEntityManager();
        GiftCertificates giftCertificates = entityManager.find(GiftCertificates.class, id);
        if (giftCertificates == null) {
            return false;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(giftCertificates);
        entityManager.getTransaction().commit();
        return true;
    }

    /**

     Returns a list of Object arrays representing GiftCertificates and their corresponding Tag names.
     The list is filtered by the provided tagName parameter and ordered by the provided orderBy and orderType parameters.
     @param tagName a String representing the name of the Tag to filter by, or null to return all GiftCertificates
     @param orderBy a String representing the property to order by
     @param orderType a String representing the order type, either "ASC" or "DESC"
     @return a List of Object arrays representing GiftCertificates and their corresponding Tag names
     */

    @Override
    public List<Object[]> readAll(String tagName, String orderBy, String orderType) {
        EntityManager entityManager = jpa.getEntityManager();
        String jpql = "SELECT gc, t.name FROM GiftCertificates gc JOIN gc.tag t ";
        if (tagName != null) {
            jpql += "WHERE t.name = :tagName ";
        }
        jpql += "ORDER BY " + orderBy + " " + orderType;
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        if (tagName != null) {
            query.setParameter("tagName", tagName);
        }
        return query.getResultList();
    }
}
