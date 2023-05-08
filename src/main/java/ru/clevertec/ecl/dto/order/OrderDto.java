package ru.clevertec.ecl.dto.order;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime purchaseTime;
    private List<GiftCertificatesResponseDto> giftCertificates;
}
