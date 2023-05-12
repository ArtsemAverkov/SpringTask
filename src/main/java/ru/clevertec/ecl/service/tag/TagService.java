package ru.clevertec.ecl.service.tag;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;

import java.util.List;

public interface TagService {
    long create(TagDtoRequest tagDto);
    TagDtoResponse read (long id) ;
    boolean update (TagDtoRequest tagDto, Long id);
    boolean delete (Long id);
    List<TagDtoResponse> readAll (Pageable pageable);
}
