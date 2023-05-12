package ru.clevertec.ecl.dto.tag;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

