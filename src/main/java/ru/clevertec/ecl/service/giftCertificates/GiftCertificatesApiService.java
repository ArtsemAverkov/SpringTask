package ru.clevertec.ecl.service.giftCertificates;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificatesDto;
import ru.clevertec.ecl.entity.GiftCertificates;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GiftCertificatesApiService implements GiftCertificatesService{
    private final GiftCertificatesRepository giftCertificatesRepository;

    public GiftCertificatesApiService(GiftCertificatesRepository giftCertificatesRepository) {
        this.giftCertificatesRepository = giftCertificatesRepository;
    }

    @Override
    public long create(GiftCertificatesDto giftCertificates) {
        GiftCertificates certificates = buildGiftCertificates(giftCertificates);
        return giftCertificatesRepository.create(certificates);
    }

    @Override
    public GiftCertificates read(long id) throws Exception {
        return giftCertificatesRepository.read(id);
    }

    @Override
    public boolean update(GiftCertificatesDto giftCertificates, Long id) {
        GiftCertificates certificates = buildGiftCertificates(giftCertificates);
        return giftCertificatesRepository.update(certificates, id);
    }

    @Override
    public boolean delete(Long id) {
        return giftCertificatesRepository.delete(id);
    }

    @Override
    public List<GiftCertificates> readAll() {
        return giftCertificatesRepository.readAll();
    }

    private GiftCertificates buildGiftCertificates(GiftCertificatesDto giftCertificatesDto){
        LocalDateTime now = LocalDateTime.now();
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
        return GiftCertificates.builder()
                .id(giftCertificatesDto.getId())
                .name(giftCertificatesDto.getName())
                .price(giftCertificatesDto.getPrice())
                .description(giftCertificatesDto.getDescription())
                .duration(giftCertificatesDto.getDuration())
                .create_date(isoDateTime)
                .last_update_date(isoDateTime)
                .build();
    }
}
