package ru.clevertec.ecl.service.giftCertificates;


import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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
        return giftCertificatesRepository.create(certificates);
    }

    /**
     * Retrieves a GiftCertificates entity with the specified id.
     *
     * @param id the id of the GiftCertificates entity to retrieve
     * @return the GiftCertificates entity with the specified id
     * @throws Exception if there is no GiftCertificates entity with the specified id
     */

    @Override
    public GiftCertificates read(long id) throws Exception {
        return giftCertificatesRepository.read(id);
    }

    /**
     * Updates a GiftCertificates entity with the specified id based on the data in the specified GiftCertificatesDto.
     *
     * @param giftCertificates a DTO object that contains data to update a GiftCertificates entity
     * @param id the id of the GiftCertificates entity to update
     * @return true if the GiftCertificates entity was updated successfully, false otherwise
     */


    @Override
    public boolean update(GiftCertificatesDto giftCertificates, Long id) {
        GiftCertificates certificates = buildGiftCertificates(giftCertificates);
        return giftCertificatesRepository.update(certificates, id);
    }

    /**
     * Deletes a GiftCertificates entity with the specified id.
     *
     * @param id the id of the GiftCertificates entity to delete
     * @return true if the GiftCertificates entity was deleted successfully, false otherwise
     */

    @Override
    public boolean delete(Long id) {
        return giftCertificatesRepository.delete(id);
    }

    /**
     * Retrieves a list of GiftCertificates and their corresponding Tag entities based on the specified parameters.
     *
     * @param tagName the name of the Tag entity to filter the GiftCertificates entities by
     * @param orderBy the name of the field to order the GiftCertificates entities by
     * @param orderType the order type (ascending or descending) to sort the GiftCertificates entities by
     * @return a list of Object arrays where the first element is a GiftCertificates entity and the second element
     * is the name of the corresponding Tag entity
     */

    @Override
    public List<Object[]> readAll(String tagName, String orderBy, String orderType) {
        return giftCertificatesRepository.readAll(tagName, orderBy, orderType);
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
                .id(giftCertificatesDto.getId())
                .name(giftCertificatesDto.getName())
                .price(giftCertificatesDto.getPrice())
                .description(giftCertificatesDto.getDescription())
                .duration(giftCertificatesDto.getDuration())
                .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                .create_date(isoDateTime)
                .last_update_date(isoDateTime)
                .build();
    }
}
