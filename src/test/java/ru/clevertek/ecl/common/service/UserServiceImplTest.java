package ru.clevertek.ecl.common.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dto.user.UserDto;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.user.UserRepository;
import ru.clevertec.ecl.service.user.UserApiService;
import ru.clevertek.ecl.common.extension.user.ValidParameterResolverUser;

import java.util.Optional;

public class UserServiceImplTest {
    @Nested
    @ExtendWith({MockitoExtension.class, ValidParameterResolverUser.class})
    public class ValidData {
        @InjectMocks
        private UserApiService userApiService;

        @Mock
        private UserRepository userRepository;

        @Test
        void shouldGetUserWhenUserValid(UserDto userDto) {
            User user = builderUserDto(userDto);
            Mockito.when(userRepository.findById(1L))
                    .thenReturn(Optional.ofNullable(user));
            Assertions.assertEquals(user, userApiService.read(1L));
            Mockito.verify(userRepository, Mockito.times(1))
                    .findById(1L);
        }

        @Test
        void shouldCreateUserWhenUserIsValid(UserDto userDto) {
            User user = builderUserDto(userDto);
            Mockito.when(userRepository.save(user)).thenReturn(user);
            Assertions.assertEquals(1L, userApiService.create(userDto));
            Mockito.verify(userRepository, Mockito.times(1)).save(user);
        }

        private User builderUserDto(UserDto userDto){
            return User.builder()
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .password(userDto.getEmail())
                    .build();
        }
    }
}
