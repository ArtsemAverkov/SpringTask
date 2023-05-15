package ru.clevertec.ecl.common.mapper.serviceMapper;

import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagServiceImplTestMapper {

    public static Tag buildTag(TagDtoRequest tagDto){
        return Tag.builder()
                .id(RequestId.VALUE_1.getValue())
                .name(tagDto.getName())
                .build();
    }

    public static Tag buildTagForMethodCreate(TagDtoRequest tagDto){
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }

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

    public static List<TagDtoResponse> getTagDtoResponseList(List<Tag> tagPage) {
        return tagPage.stream()
                .map(TagServiceImplTestMapper::convertTagToTagDtoResponse)
                .collect(Collectors.toList());
    }
}
