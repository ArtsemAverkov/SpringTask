package ru.clevertec.ecl.service.giftCertificates;

import ru.clevertec.ecl.dto.GiftCertificatesDto;
import ru.clevertec.ecl.entity.GiftCertificates;

import java.util.List;

public interface GiftCertificatesService {
    long create(GiftCertificatesDto giftCertificates);
    GiftCertificates read (long id) throws Exception;
    boolean update (GiftCertificatesDto giftCertificates, Long id);
    boolean delete (Long id);
    List<GiftCertificates> readAll ();
}
