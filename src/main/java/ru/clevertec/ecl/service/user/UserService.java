package ru.clevertec.ecl.service.user;

import ru.clevertec.ecl.dto.user.UserDto;
import ru.clevertec.ecl.entity.user.User;

public interface UserService {
    User read (Long id);
    long create (UserDto userDto);
}
