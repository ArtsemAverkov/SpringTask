package ru.clevertec.ecl.service.user;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.user.UserRepository;

import java.util.List;

@Service
public class UserApiService implements UserService{
    private final UserRepository userRepository;

    public UserApiService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> read(Long id) {
        return userRepository.read(id);
    }
}
