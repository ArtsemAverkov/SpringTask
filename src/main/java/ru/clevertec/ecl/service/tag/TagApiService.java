package ru.clevertec.ecl.service.tag;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.tag.TagDto;
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
        return tagRepository.create(tag);
    }

    @Override
    public Tag read(long id) throws Exception {
        return tagRepository.read(id);
    }

    @Override
    public boolean update(TagDto tagDto, Long id) {
        Tag tag = buildTag(tagDto);
        return tagRepository.update(tag, id);
    }

    @Override
    public boolean delete(Long id) {
        return tagRepository.delete(id);
    }

    @Override
    public List<Object> readAll() {
        return tagRepository.readAll();
    }

    private Tag buildTag(TagDto tagDto){
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }
}
