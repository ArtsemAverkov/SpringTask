package ru.clevertec.ecl.repository.tag;


import ru.clevertec.ecl.entity.tag.Tag;

import java.util.List;

public interface TagRepository {
    long create(Tag tag);
    Tag read (long id) throws Exception;
    boolean update (Tag tag, Long id);
    boolean delete (Long id);
    List<Object> readAll ();
}
