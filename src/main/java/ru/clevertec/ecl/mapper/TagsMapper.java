package ru.clevertec.ecl.mapper;

import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 The TagsMapper class provides methods to convert between Tag entities and their DTO representations.
 */
public class TagsMapper {

    /**
     Builds a Tag entity from a TagDtoRequest.
     @param tagDto The TagDtoRequest to convert
     @return The resulting Tag entity
     */

    public static Tag buildTag(TagDtoRequest tagDto){
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }

    /**
     Converts a Tag entity to a TagDtoResponse.
     @param tag The Tag entity to convert
     @return The resulting TagDtoResponse
     */

    public static TagDtoResponse convertTagToTagDtoResponse(Tag tag) {
        List<GiftCertificatesResponseDto> giftCertificates = new ArrayList<>();
        for (GiftCertificates giftCertificate : tag.getGiftCertificatesList()) {
            giftCertificates.add(GiftCertificatesResponseDto.builder()
                    .id(giftCertificate.getId())
                    .name(giftCertificate.getName())
                    .description(giftCertificate.getDescription())
                    .price(giftCertificate.getPrice())
                    .duration(giftCertificate.getDuration())
                    .build());
        }
        return TagDtoResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .giftCertificates(giftCertificates)
                .build();
    }

    /**
     Converts a List of Tag entities to a List of TagDtoResponses.
     @param tagPage The List of Tag entities to convert
     @return The resulting List of TagDtoResponses
     */

    public static List<TagDtoResponse> getTagDtoResponseList(List<Tag> tagPage) {
        return tagPage.stream()
                .map(TagsMapper::convertTagToTagDtoResponse)
                .collect(Collectors.toList());
    }
}
