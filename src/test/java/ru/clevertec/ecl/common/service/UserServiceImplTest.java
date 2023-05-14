package ru.clevertec.ecl.common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.user.UserRepository;
import ru.clevertec.ecl.service.user.UserApiService;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.common.extension.user.ValidParameterResolverUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@DisplayName("User Service Test")
public class UserServiceImplTest {
    @Nested
    @ExtendWith({MockitoExtension.class, ValidParameterResolverUser.class})
    public class ValidData {
        @InjectMocks
        private UserApiService userApiService;

        @Mock
        private UserRepository userRepository;

        @Test
        void shouldGetUserWhenUserValid(UserDtoRequest userDto) {
            User user = builderUserDto(userDto);
            when(userRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.ofNullable(user));
            assertEquals(user, userApiService.read(RequestId.VALUE_1.getValue()));
            verify(userRepository, times(1)).findById(RequestId.VALUE_1.getValue());
        }

        @Test
        void shouldCreateUserWhenUserIsValid(UserDtoRequest userDto) {
            User userForReturn = builderUserDto(userDto);
            User user = builderUserDtoForMethodCreate(userDto);
            when(userRepository.save(user)).thenReturn(userForReturn);
            assertEquals(RequestId.VALUE_1.getValue(), userApiService.create(userDto));
            verify(userRepository, times(1)).save(user);
        }

        private User builderUserDto(UserDtoRequest userDto){
            return User.builder()
                    .id(RequestId.VALUE_1.getValue())
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .password(userDto.getEmail())
                    .build();
        }

        private User builderUserDtoForMethodCreate(UserDtoRequest userDto){
            return User.builder()
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .password(userDto.getEmail())
                    .build();
        }
    }
}
