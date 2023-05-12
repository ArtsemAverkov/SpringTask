package ru.clevertec.ecl.dto.order;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoRequest {
    @NotNull
    private Long id;

    @NotNull
    private LocalDateTime purchaseTime;

    @Valid
    @NotNull
    private List<GiftCertificatesResponseDto> giftCertificates;
}

