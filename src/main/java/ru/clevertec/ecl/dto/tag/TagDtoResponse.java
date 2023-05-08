package ru.clevertec.ecl.dto.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;

import java.util.List;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDtoResponse {
    private Long id;
    private String name;
    private List<GiftCertificatesResponseDto> giftCertificates;


}

