package ru.clevertec.ecl.service.tag;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.repository.tag.TagRepository;

import java.util.List;
@Service
public class TagApiService implements TagService{


    private final TagRepository tagRepository;

    public TagApiService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public long create(Tag tag) {
        return tagRepository.create(tag);
    }

    @Override
    public Tag read(long id) throws Exception {
        return null;
    }

    @Override
    public boolean update(Tag tag, Long id) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Tag> readAll() {
        return null;
    }
}
