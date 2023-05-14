package ru.clevertec.ecl.repository.giftCertificates;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;

public interface GiftCertificatesRepository extends JpaRepository<GiftCertificates,Long> {
}
