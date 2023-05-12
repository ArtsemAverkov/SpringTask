package ru.clevertec.ecl.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
