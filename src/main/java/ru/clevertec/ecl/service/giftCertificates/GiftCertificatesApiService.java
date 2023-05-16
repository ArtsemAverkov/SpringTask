package ru.clevertec.ecl.service.giftCertificates;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public long create(GiftCertificatesDtoRequest giftCertificates) {
        GiftCertificates certificates = buildGiftCertificates(giftCertificates);
        return giftCertificatesRepository.save(certificates).getId();
    }

    /**
     * Retrieves a GiftCertificates entity with the specified id.
     * @param id the id of the GiftCertificates entity to retrieve
     * @return the GiftCertificates entity with the specified id
     * @throws Exception if there is no GiftCertificates entity with the specified id
     */

    @Override
    public GiftCertificatesDtoRequest read(long id) {
        GiftCertificates giftCertificates = giftCertificatesRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid GiftCertificates Id:" + id));
        return convertToGiftCertificatesDto(giftCertificates);
    }

    /**
     * Updates a GiftCertificates entity with the specified id based on the data in the specified GiftCertificatesDto.
     * @param giftCertificates a DTO object that contains data to update a GiftCertificates entity
     * @param id the id of the GiftCertificates entity to update
     * @return true if the GiftCertificates entity was updated successfully, false otherwise
     */

    @Override
    @Transactional
    public boolean update(GiftCertificatesDtoRequest giftCertificates, Long id) {
        read(id);
        GiftCertificates certificates = buildGiftCertificates(giftCertificates);
        certificates.setId(id);
        giftCertificatesRepository.save(certificates);
        return true;
    }

    /**
     * Deletes a GiftCertificates entity with the specified id.
     *
     * @param id the id of the GiftCertificates entity to delete
     * @return true if the GiftCertificates entity was deleted successfully, false otherwise
     */

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
        return convertToDtoList(all);
    }

    /**
     * Builds and returns a new GiftCertificates object based on the provided GiftCertificatesDto.
     * @param giftCertificatesDto a GiftCertificatesDto object containing the data to be used in building the new GiftCertificates object
     * @return a new GiftCertificates object
     */

    private GiftCertificates buildGiftCertificates(GiftCertificatesDtoRequest giftCertificatesDto){
        LocalDateTime now = LocalDateTime.now();
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
        return GiftCertificates.builder()
                .name(giftCertificatesDto.getName())
                .price(giftCertificatesDto.getPrice())
                .description(giftCertificatesDto.getDescription())
                .duration(giftCertificatesDto.getDuration())
                .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                .create_date(isoDateTime)
                .last_update_date(isoDateTime)
                .build();
    }

    /**
     * Converts a GiftCertificates object to a GiftCertificatesDto object.
     * @param giftCertificates a GiftCertificates object to be converted
     * @return a GiftCertificatesDto object
     */

    private GiftCertificatesDtoRequest convertToGiftCertificatesDto(GiftCertificates giftCertificates){
        return GiftCertificatesDtoRequest.builder()
                .id(giftCertificates.getId())
                .name(giftCertificates.getName())
                .price(giftCertificates.getPrice())
                .duration(giftCertificates.getDuration())
                .description(giftCertificates.getDescription())
                .tagDto(new TagDtoRequest(giftCertificates.getTag().getId(),giftCertificates.getTag().getName()))
                .build();
    }

    /**
     * Converts a List of GiftCertificates objects to a List of GiftCertificatesDto objects using the convertToGiftCertificatesDto() method.
     * @param giftCertificatesList a List of GiftCertificates objects to be converted
     * @return a List of GiftCertificatesDto objects
     */

    public List<GiftCertificatesDtoRequest> convertToDtoList(List<GiftCertificates> giftCertificatesList) {
        return giftCertificatesList.stream()
                .map(this::convertToGiftCertificatesDto)
                .collect(Collectors.toList());
    }
}
