package ru.clevertec.ecl.service.tag;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.tag.TagDto;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.tag.TagRepository;

import java.util.List;

@Service
public class TagApiService implements TagService{
    private final TagRepository tagRepository;

    public TagApiService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public long create(TagDto tagDto) {
        Tag tag = buildTag(tagDto);
        return tagRepository.save(tag).getId();
    }

    @Override
    public TagDtoResponse read(long id) {
        return tagRepository.findById(id).orElseThrow(NoSuchMethodException::new);
    }

    @Override
    public boolean update(TagDto tagDto, Long id) {
        read(id);
        Tag tag = buildTag(tagDto);
        tag.setId(id);
        tagRepository.save(tag);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        read(id);
        tagRepository.deleteById(id);
        return true;
    }

    @Override
    public List<TagDtoResponse> readAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    private Tag buildTag(TagDto tagDto){
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }
}
