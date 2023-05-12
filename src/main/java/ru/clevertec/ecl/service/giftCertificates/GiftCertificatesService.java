package ru.clevertec.ecl.service.giftCertificates;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;

import java.util.List;

public interface GiftCertificatesService {
    long create(GiftCertificatesDto giftCertificates);
    GiftCertificates read (long id) throws Exception;
    boolean update (GiftCertificatesDto giftCertificates, Long id);
    boolean delete (Long id);
    List<GiftCertificates> readAll (Pageable pageable);
}
