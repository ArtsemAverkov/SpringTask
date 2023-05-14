package ru.clevertec.ecl.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select count(name) from users where name =:name", nativeQuery = true)
    int existActiveUserName (@Param("name") String name);

}
