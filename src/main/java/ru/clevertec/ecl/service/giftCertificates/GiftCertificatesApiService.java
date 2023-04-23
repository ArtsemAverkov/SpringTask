package ru.clevertec.ecl.service.giftCertificates;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.entity.GiftCertificates;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;

import java.util.List;

@Service
public class GiftCertificatesApiService implements GiftCertificatesService{
    private final GiftCertificatesRepository giftCertificatesRepository;

    public GiftCertificatesApiService(GiftCertificatesRepository giftCertificatesRepository) {
        this.giftCertificatesRepository = giftCertificatesRepository;
    }

    @Override
    public long create(GiftCertificates giftCertificates) {
        return giftCertificatesRepository.create(giftCertificates);
    }

    @Override
    public GiftCertificates read(long id) throws Exception {
        return null;
    }

    @Override
    public boolean update(GiftCertificates giftCertificates, Long id) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<GiftCertificates> readAll() {
        return null;
    }
}
