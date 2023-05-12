package ru.clevertec.ecl.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
