package ru.clevertec.ecl.repository.user;

import ru.clevertec.ecl.entity.user.User;

import java.util.List;

public interface UserRepository {
    List<User> read (Long id);
}
