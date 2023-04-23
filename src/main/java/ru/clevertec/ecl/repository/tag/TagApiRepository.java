package ru.clevertec.ecl.repository.tag;

import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.Tag;

import java.util.List;

@Repository
public class TagApiRepository implements TagRepository{

    @Override
    public long create(Tag tag) {
        return 0;
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
