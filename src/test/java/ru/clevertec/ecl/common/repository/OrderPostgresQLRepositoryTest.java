package ru.clevertec.ecl.common.repository;

import jakarta.persistence.EntityManager;
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
import ru.clevertec.ecl.entity.order.Order;
import ru.clevertec.ecl.repository.order.OrderRepository;

import java.util.List;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@DisplayName("Order PostgreSQL Repository Test")
public class OrderPostgresQLRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    private final OrderRepository orderRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    public OrderPostgresQLRepositoryTest(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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
    @Sql("/INITIAL_ORDER_DB_SCRIPT.sql")
    void shouldFindByUserId(){
        List<Order> byUserId = orderRepository.findByUserId(1L);
        testEntityManager.flush();
        testEntityManager.getEntityManager().getTransaction().commit();
       // Assertions.assertEquals(1, activeProductId);
    }
}
