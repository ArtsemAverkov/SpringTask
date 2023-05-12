package ru.clevertec.ecl.dto.giftCertificates;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
