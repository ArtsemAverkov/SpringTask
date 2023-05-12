package ru.clevertec.ecl.dto.giftCertificates;

import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
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
public class GiftCertificatesDtoRequest {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    @PositiveOrZero
    private Double price;

    @NotNull
    @PositiveOrZero
    private Long duration;

    @NotNull
    private TagDtoRequest tagDto;
    public GiftCertificatesDtoRequest(Long id, String name, String description, Double price, Long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}
