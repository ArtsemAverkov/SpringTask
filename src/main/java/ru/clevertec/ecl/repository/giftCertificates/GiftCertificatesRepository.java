package ru.clevertec.ecl.repository.giftCertificates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;

@Repository
public interface GiftCertificatesRepository extends JpaRepository<GiftCertificates,Long> {

}
