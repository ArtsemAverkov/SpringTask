package ru.clevertec.ecl.dto.giftCertificates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dto.tag.TagDto;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificatesDto {
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
    private TagDto tagDto;

    public GiftCertificatesDto(Long id, String name, String description, Double price, Long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}
