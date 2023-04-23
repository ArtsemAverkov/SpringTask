package ru.clevertec.ecl.service.giftCertificates;

import ru.clevertec.ecl.entity.GiftCertificates;

import java.util.List;

public interface GiftCertificatesService {
    long create(GiftCertificates giftCertificates);
    GiftCertificates read (long id) throws Exception;
    boolean update (GiftCertificates giftCertificates, Long id);
    boolean delete (Long id);
    List<GiftCertificates> readAll ();
}
