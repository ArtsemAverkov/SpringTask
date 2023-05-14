package ru.clevertec.ecl.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TagDtoRequest {

    @NotBlank
    private String name;
}
