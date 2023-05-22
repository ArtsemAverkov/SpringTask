package ru.clevertec.ecl.common.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.clevertec.ecl.repository.user.UserRepository;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("User Repository Test")
public class UserPostgresQLRepositoryTest extends TestContainerInitializer{

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  TestEntityManager testEntityManager;

    @Test
    @Sql("/INITIAL_ALL_DB_SCRIPT.sql")
    void shouldFindByUserName(){
        int activeUserName = userRepository.existActiveUserName("John Doe");
        testEntityManager.flush();
        testEntityManager.getEntityManager().getTransaction().commit();
       Assertions.assertEquals(1, activeUserName);
    }
}
