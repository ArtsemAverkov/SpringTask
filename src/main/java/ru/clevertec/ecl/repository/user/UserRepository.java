package ru.clevertec.ecl.repository.user;

import ru.clevertec.ecl.entity.user.User;

public interface UserRepository {
    User read (Long id);
}
