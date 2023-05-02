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
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("duration")
    private Long duration;
}
