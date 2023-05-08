package ru.clevertec.ecl.service.user;

import ru.clevertec.ecl.dto.userDto.UserDto;
import ru.clevertec.ecl.entity.user.User;

import java.util.List;

public interface UserService {
    User read (Long id);
    long create (UserDto userDto);
}
