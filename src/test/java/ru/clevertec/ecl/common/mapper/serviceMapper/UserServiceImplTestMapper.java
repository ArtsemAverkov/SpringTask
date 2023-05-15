package ru.clevertec.ecl.common.mapper.serviceMapper;

import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;

public class UserServiceImplTestMapper {

    public static User builderUserDto(UserDtoRequest userDto){
        return User.builder()
                .id(RequestId.VALUE_1.getValue())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getEmail())
                .build();
    }

    public static User builderUserDtoForMethodCreate(UserDtoRequest userDto){
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getEmail())
                .build();
    }
}
