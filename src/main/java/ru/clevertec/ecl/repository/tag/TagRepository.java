package ru.clevertec.ecl.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.entity.tag.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
