package ru.clevertec.ecl.repository.giftCertificates;

import ru.clevertec.ecl.entity.GiftCertificates;

import java.util.List;

public interface GiftCertificatesRepository {
    long create(GiftCertificates giftCertificates);
    GiftCertificates read (long id) throws Exception;
    boolean update (GiftCertificates giftCertificates, Long id);
    boolean delete (Long id);
    List<Object[]> readAll (String tagName, String orderBy, String orderType);

}
