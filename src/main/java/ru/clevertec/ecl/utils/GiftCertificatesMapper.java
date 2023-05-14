package ru.clevertec.ecl.utils;

import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 The GiftCertificatesMapper class provides methods for converting GiftCertificates objects
 to GiftCertificatesDtoRequest objects and vice versa.
 */
public class GiftCertificatesMapper {

    /**
     Converts a GiftCertificatesDtoRequest object to a GiftCertificates object.
     @param giftCertificatesDto the GiftCertificatesDtoRequest object to be converted
     @param isCreate a boolean value indicating if the GiftCertificates object is being created or updated
     @return a GiftCertificates object
     */
    public static GiftCertificates buildGiftCertificates(GiftCertificatesDtoRequest giftCertificatesDto, boolean isCreate) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String isoDateTime = now.format(formatter);
        if (isCreate) {
            return GiftCertificates.builder()
                    .name(giftCertificatesDto.getName())
                    .price(giftCertificatesDto.getPrice())
                    .description(giftCertificatesDto.getDescription())
                    .duration(giftCertificatesDto.getDuration())
                    .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                    .create_date(isoDateTime)
                    .last_update_date(isoDateTime)
                    .build();
        } else {
            return GiftCertificates.builder()
                    .name(giftCertificatesDto.getName())
                    .price(giftCertificatesDto.getPrice())
                    .description(giftCertificatesDto.getDescription())
                    .duration(giftCertificatesDto.getDuration())
                    .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                    .last_update_date(isoDateTime)
                    .build();
        }
    }

    /**
     Converts a GiftCertificates object to a GiftCertificatesDtoRequest object.
     @param giftCertificates the GiftCertificates object to be converted
     @return a GiftCertificatesDtoRequest object
     */

    public static GiftCertificatesDtoRequest convertToGiftCertificatesDto(GiftCertificates giftCertificates){
        return GiftCertificatesDtoRequest.builder()
                .name(giftCertificates.getName())
                .price(giftCertificates.getPrice())
                .duration(giftCertificates.getDuration())
                .description(giftCertificates.getDescription())
                .tagDto(new TagDtoRequest(giftCertificates.getTag().getName()))
                .build();
    }

    /**

     Converts a List of GiftCertificates objects to a List of GiftCertificatesDtoRequest objects using the convertToGiftCertificatesDto() method.
     @param giftCertificatesList a List of GiftCertificates objects to be converted
     @return a List of GiftCertificatesDtoRequest objects
     */

    public static List<GiftCertificatesDtoRequest> convertToDtoList(List<GiftCertificates> giftCertificatesList) {
        return giftCertificatesList.stream()
                .map(GiftCertificatesMapper::convertToGiftCertificatesDto)
                .collect(Collectors.toList());
    }
}
