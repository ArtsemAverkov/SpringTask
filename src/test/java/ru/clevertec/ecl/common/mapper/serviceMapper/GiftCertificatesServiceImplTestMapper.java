package ru.clevertec.ecl.common.mapper.serviceMapper;

import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class GiftCertificatesServiceImplTestMapper {

    public static GiftCertificates buildGiftCertificates(GiftCertificatesDtoRequest giftCertificatesDto) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String isoDateTime = now.format(formatter);
        return GiftCertificates.builder()
                .id(RequestId.VALUE_1.getValue())
                .name(giftCertificatesDto.getName())
                .price(giftCertificatesDto.getPrice())
                .description(giftCertificatesDto.getDescription())
                .duration(giftCertificatesDto.getDuration())
                .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                .create_date(isoDateTime)
                .last_update_date(isoDateTime)
                .build();
    }

     public static GiftCertificatesDtoRequest convertToGiftCertificatesDto(GiftCertificates giftCertificates){
        return GiftCertificatesDtoRequest.builder()
                .name(giftCertificates.getName())
                .price(giftCertificates.getPrice())
                .duration(giftCertificates.getDuration())
                .description(giftCertificates.getDescription())
                .tagDto(new TagDtoRequest(giftCertificates.getTag().getName()))
                .build();
    }

    public static List<GiftCertificatesDtoRequest> convertToDtoList(List<GiftCertificates> giftCertificatesList) {
        return giftCertificatesList.stream()
                .map(GiftCertificatesServiceImplTestMapper::convertToGiftCertificatesDto)
                .collect(Collectors.toList());
    }
}
