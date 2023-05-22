package ru.clevertec.ecl.common.utill.controllerMapper;

import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;

import java.util.List;

public class TagControllerTestMapper {
    public static TagDtoResponse getTagDtoResponse(TagDtoRequest tagDto, GiftCertificatesDtoRequest giftCertificatesDto) {
        List<GiftCertificatesResponseDto> giftCertificatesDtoList = List.of(
                new GiftCertificatesResponseDto(
                        RequestId.VALUE_1.getValue(),
                        giftCertificatesDto.getName(),
                        giftCertificatesDto.getDescription(),
                        giftCertificatesDto.getPrice(),
                        giftCertificatesDto.getDuration()));
        return new TagDtoResponse(
                RequestId.VALUE_1.getValue(), tagDto.getName(),giftCertificatesDtoList);
    }

    public static String buildJson(TagDtoRequest tagDtoRequest){
        return "{\n" +
                "  \"name\": \""+tagDtoRequest.getName()+"\"\n" +
                "}";
    }
}
