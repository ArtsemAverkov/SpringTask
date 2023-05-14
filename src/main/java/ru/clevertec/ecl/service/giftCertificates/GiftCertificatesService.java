package ru.clevertec.ecl.service.giftCertificates;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface GiftCertificatesService {
    long create(GiftCertificatesDtoRequest giftCertificates);
    GiftCertificatesDtoRequest read (long id) throws Exception;
    boolean update (GiftCertificatesDtoRequest giftCertificates, Long id);
    boolean delete (Long id);
    List<GiftCertificatesDtoRequest> readAll (Pageable pageable);
}
