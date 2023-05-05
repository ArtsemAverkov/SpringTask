package ru.clevertec.ecl.service.user;

import ru.clevertec.ecl.entity.user.User;

import java.util.List;

public interface UserService {
    List<User> read (Long id);
}
