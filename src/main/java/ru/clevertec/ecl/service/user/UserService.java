package ru.clevertec.ecl.service.user;

import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;

public interface UserService {
    User read (Long id);
    long create (UserDtoRequest userDto);
}
