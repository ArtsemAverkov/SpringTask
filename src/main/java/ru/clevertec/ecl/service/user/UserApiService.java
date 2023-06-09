package ru.clevertec.ecl.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.user.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserApiService implements UserService{

    private final UserRepository userRepository;

    /**
     * Retrieves a User object with the specified ID.
     * @param id the ID of the User object to retrieve.
     * @return the User object with the specified ID.
     * @throws NoSuchElementException if no User object with the specified ID is found.
     */

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    /**
     * Creates a new User object with the provided UserDto data.
     * @param userDto the UserDto object containing the data to create the new User object.
     * @return the ID of the newly created User object.
     */

    @Override
    public long create(UserDtoRequest userDto) {
        User user = builderUserDto(userDto);
        return userRepository.save(user).getId();
    }

    /**
     * Constructs a new User object using the provided UserDto object.
     * @param userDto the UserDto object containing the data to create the new User object.
     * @return the newly created User object.
     */
    private User builderUserDto(UserDtoRequest userDto){
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getEmail())
                .build();
    }
}
