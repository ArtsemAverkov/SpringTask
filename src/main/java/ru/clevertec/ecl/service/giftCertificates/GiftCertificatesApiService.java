package ru.clevertec.ecl.service.giftCertificates;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.mapper.GiftCertificatesMapper;

import java.util.List;

/**
 The {@code GiftCertificatesApiService} class represents a service layer that provides methods
 to interact with GiftCertificates entities. This class implements the {@code GiftCertificatesService} interface.
 @author [ArtsemAverkov]
 @version [1.0]
 */
@Service
@RequiredArgsConstructor
public class GiftCertificatesApiService implements GiftCertificatesService{
    private final GiftCertificatesRepository giftCertificatesRepository;

    /**
     * Creates a new GiftCertificates entity based on the specified GiftCertificatesDto and returns its id.
     * @param giftCertificates a DTO object that contains data for a new GiftCertificates entity
     * @return the id of the newly created GiftCertificates entity
     */

    @Cacheable("GiftCertificatesCache")
    @Override
    public long create(GiftCertificatesDtoRequest giftCertificates) {
        GiftCertificates certificates =
                GiftCertificatesMapper.buildGiftCertificates(giftCertificates, true);
        return giftCertificatesRepository.save(certificates).getId();
    }

    /**
     * Retrieves a GiftCertificates entity with the specified id.
     * @param id the id of the GiftCertificates entity to retrieve
     * @return the GiftCertificates entity with the specified id
     */

    @Cacheable("GiftCertificatesCache")
    @Override
    public GiftCertificatesDtoRequest read(long id) {
        GiftCertificates giftCertificates = giftCertificatesRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid GiftCertificates Id:" + id));
        return GiftCertificatesMapper.convertToGiftCertificatesDto(giftCertificates);
    }

    /**
     * Updates a GiftCertificates entity with the specified id based on the data in the specified GiftCertificatesDto.
     * @param giftCertificates a DTO object that contains data to update a GiftCertificates entity
     * @param id the id of the GiftCertificates entity to update
     * @return true if the GiftCertificates entity was updated successfully, false otherwise
     */

    @Cacheable("GiftCertificatesCache")
    @Override
    @Transactional
    public boolean update(GiftCertificatesDtoRequest giftCertificates, Long id) {
        read(id);
        GiftCertificates certificates = GiftCertificatesMapper.buildGiftCertificates(giftCertificates, false);
        certificates.setId(id);
        giftCertificatesRepository.save(certificates);
        return true;
    }

    /**
     * Deletes a GiftCertificates entity with the specified id.
     * @param id the id of the GiftCertificates entity to delete
     * @return true if the GiftCertificates entity was deleted successfully, false otherwise
     */

    @Cacheable("GiftCertificatesCache")
    @Override
    @Transactional
    public boolean delete(Long id) {
        read(id);
        giftCertificatesRepository.deleteById(id);
        return true;
    }

    /**
     * Retrieves all gift certificates from the repository and converts them to a List of GiftCertificatesDto.
     * @param pageable an object containing pagination information for the query
     * @return a List of GiftCertificatesDto objects
     */
    @Override
    public List<GiftCertificatesDtoRequest> readAll(Pageable pageable) {
        List<GiftCertificates> all = giftCertificatesRepository.findAll(pageable).getContent();
        return GiftCertificatesMapper.convertToDtoList(all);
    }
}
