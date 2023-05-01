package ru.clevertec.ecl.dto.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;

import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDtoResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    private List<GiftCertificatesDto> giftCertificates;


}

