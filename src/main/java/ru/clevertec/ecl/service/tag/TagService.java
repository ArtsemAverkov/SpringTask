package ru.clevertec.ecl.service.tag;

import ru.clevertec.ecl.entity.Tag;

import java.util.List;

public interface TagService {
    long create(Tag tag);
    Tag read (long id) throws Exception;
    boolean update (Tag tag, Long id);
    boolean delete (Long id);
    List<Tag> readAll ();
}
