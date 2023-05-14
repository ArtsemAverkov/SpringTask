package ru.clevertec.ecl.common.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.clevertec.ecl.repository.user.UserRepository;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("User Repository Test")
public class UserPostgresQLRepositoryTest {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  TestEntityManager testEntityManager;



    private static final DockerImageName PostgresQL_IMAGE = DockerImageName
            .parse("postgres:14.3");
    @Container
    private  static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>(PostgresQL_IMAGE)
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("root")
            .withExposedPorts(5432);

    @DynamicPropertySource
    private static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgresSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgresSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgresSQLContainer::getUsername);
    }

    @Test
    @Sql("/INITIAL_ALL_DB_SCRIPT.sql")
    void shouldFindByUserName(){
        int activeUserName = userRepository.existActiveUserName("John Doe");
        testEntityManager.flush();
        testEntityManager.getEntityManager().getTransaction().commit();
       Assertions.assertEquals(1, activeUserName);
    }


}
