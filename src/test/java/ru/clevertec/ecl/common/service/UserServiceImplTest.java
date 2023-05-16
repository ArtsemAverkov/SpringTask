package ru.clevertec.ecl.common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.common.mapper.serviceMapper.UserServiceImplTestMapper;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.user.UserRepository;
import ru.clevertec.ecl.service.user.UserApiService;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.common.extension.user.ValidParameterResolverUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            User user = UserServiceImplTestMapper.builderUserDto(userDto);
            when(userRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.ofNullable(user));
            assertEquals(user, userApiService.read(RequestId.VALUE_1.getValue()));
            verify(userRepository, times(1)).findById(RequestId.VALUE_1.getValue());
        }

        @Test
        void shouldCreateUserWhenUserIsValid(UserDtoRequest userDto) {
            User user = UserServiceImplTestMapper.builderUserDto(userDto);
            User builderUserDtoForMethodCreate = UserServiceImplTestMapper.builderUserDtoForMethodCreate(userDto);
            when(userRepository.save(builderUserDtoForMethodCreate)).thenReturn(user);
            assertEquals(RequestId.VALUE_1.getValue(), userApiService.create(userDto));
            verify(userRepository, times(1)).save(builderUserDtoForMethodCreate);
        }
    }

    @Nested
    @ExtendWith({MockitoExtension.class, ValidParameterResolverUser.class})
    public class InvalidData {
        @InjectMocks
        private UserApiService userApiService;

        @Mock
        private UserRepository userRepository;

        @Test
        void shouldGetUserWhenUserInvalid() {
            when(userRepository.findById(RequestId.VALUE_1.getValue()))
                            .thenReturn(Optional.ofNullable(null));
            assertThrows(IllegalArgumentException.class,
                    () -> userApiService.read(RequestId.VALUE_1.getValue()));

        }

        @Test
        void shouldCreateUserWhenUserInvalid(UserDtoRequest userDtoRequest) {
            when(userRepository.existActiveUserName("name")).thenReturn(1);
            assertThrows(IllegalArgumentException.class,
                    () -> userApiService.create(userDtoRequest));

        }
    }
}
