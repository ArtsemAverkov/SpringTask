package ru.clevertec.ecl.common.utill.controllerMapper;

import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;

public class UserControllerTestMapper {
    public static User builderUser(UserDtoRequest userDtoRequest){
        return User.builder()
                .id(RequestId.VALUE_1.getValue())
                .name(userDtoRequest.getName())
                .email(userDtoRequest.getEmail())
                .password(userDtoRequest.getPassword())
                .build();

    }

    public static String buildJson(UserDtoRequest userDto){
        return "{\n" +
                "  \"name\": \""+userDto.getName()+"\",\n" +
                "  \"email\": \""+userDto.getEmail()+"\",\n" +
                "  \"password\": \""+userDto.getPassword()+"\"\n" +
                "}";
    }
}
