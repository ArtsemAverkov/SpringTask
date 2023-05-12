package ru.clevertec.ecl.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDtoRequest {

    private Long id;
    @NotBlank
    private String name;
}
