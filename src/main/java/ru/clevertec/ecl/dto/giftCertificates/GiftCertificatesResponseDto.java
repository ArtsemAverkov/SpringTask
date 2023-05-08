package ru.clevertec.ecl.dto.giftCertificates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificatesResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long duration;
}
