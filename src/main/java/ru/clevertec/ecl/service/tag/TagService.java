package ru.clevertec.ecl.service.tag;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.tag.TagDto;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.tag.Tag;

import java.util.List;

public interface TagService {
    long create(TagDto tagDto);
    TagDtoResponse read (long id) ;
    boolean update (TagDto tagDto, Long id);
    boolean delete (Long id);
    List<TagDtoResponse> readAll (Pageable pageable);
}
