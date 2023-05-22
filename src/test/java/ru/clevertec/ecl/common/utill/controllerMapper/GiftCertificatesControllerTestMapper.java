package ru.clevertec.ecl.common.utill.controllerMapper;

import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;

public class GiftCertificatesControllerTestMapper {
   public static String buildJson(GiftCertificatesDtoRequest giftCertificatesDto,
                             TagDtoRequest tagDtoRequest){
        return "{\n" +
                "  \"name\": \""+giftCertificatesDto.getName()+"\",\n" +
                "  \"description\": \""+giftCertificatesDto.getDescription()+"\",\n" +
                "  \"price\": "+giftCertificatesDto.getPrice()+",\n" +
                "  \"duration\": "+giftCertificatesDto.getDuration()+",\n" +
                "  \"tagDto\": {\n" +
                "    \"name\": \""+tagDtoRequest.getName()+"\"\n" +
                "  }\n" +
                "}";
    }
}
