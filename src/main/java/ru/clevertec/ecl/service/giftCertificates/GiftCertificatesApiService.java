package ru.clevertec.ecl.service.giftCertificates;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
import ru.clevertec.ecl.dto.tag.TagDto;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**

 The {@code GiftCertificatesApiService} class represents a service layer that provides methods
 to interact with GiftCertificates entities. This class implements the {@code GiftCertificatesService} interface.
 @author [ArtsemAverkov]
 @version [1.0]
 */
@Service
public class GiftCertificatesApiService implements GiftCertificatesService{
    private final GiftCertificatesRepository giftCertificatesRepository;


    public GiftCertificatesApiService(GiftCertificatesRepository giftCertificatesRepository) {
        this.giftCertificatesRepository = giftCertificatesRepository;
    }

    /**
     * Creates a new GiftCertificates entity based on the specified GiftCertificatesDto and returns its id.
     *
     * @param giftCertificates a DTO object that contains data for a new GiftCertificates entity
     * @return the id of the newly created GiftCertificates entity
     */

    @Override
    public long create(GiftCertificatesDto giftCertificates) {
        GiftCertificates certificates = buildGiftCertificates(giftCertificates);
        return giftCertificatesRepository.save(certificates).getId();
    }

    /**
     * Retrieves a GiftCertificates entity with the specified id.
     *
     * @param id the id of the GiftCertificates entity to retrieve
     * @return the GiftCertificates entity with the specified id
     * @throws Exception if there is no GiftCertificates entity with the specified id
     */

    @Override
    public GiftCertificatesDto read(long id) {
        GiftCertificates giftCertificates = giftCertificatesRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid GiftCertificates Id:" + id));
        return convertToGiftCertificatesDto(giftCertificates);
    }

    /**
     * Updates a GiftCertificates entity with the specified id based on the data in the specified GiftCertificatesDto.
     *
     * @param giftCertificates a DTO object that contains data to update a GiftCertificates entity
     * @param id the id of the GiftCertificates entity to update
     * @return true if the GiftCertificates entity was updated successfully, false otherwise
     */


    @Override
    @Transactional
    public boolean update(GiftCertificatesDto giftCertificates, Long id) {
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

    @Override
    public List<GiftCertificatesDto> readAll(Pageable pageable) {
        List<GiftCertificates> all = giftCertificatesRepository.findAll(pageable).getContent();
        return convertToDtoList(all);
    }

    /**
     * Builds a new GiftCertificates entity based on the specified GiftCertificatesDto.
     *
     * @param giftCertificatesDto a DTO object that contains data for a new GiftCertificates entity
     * @return the newly created GiftCertificates entity
     */

    private GiftCertificates buildGiftCertificates(GiftCertificatesDto giftCertificatesDto){
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

    private GiftCertificatesDto convertToGiftCertificatesDto( GiftCertificates giftCertificates){
        return GiftCertificatesDto.builder()
                .id(giftCertificates.getId())
                .name(giftCertificates.getName())
                .price(giftCertificates.getPrice())
                .duration(giftCertificates.getDuration())
                .description(giftCertificates.getDescription())
                .tagDto(new TagDto(giftCertificates.getTag().getId(),giftCertificates.getTag().getName()))
                .build();
    }

    public List<GiftCertificatesDto> convertToDtoList(List<GiftCertificates> giftCertificatesList) {
        return giftCertificatesList.stream()
                .map(this::convertToGiftCertificatesDto)
                .collect(Collectors.toList());
    }
}
