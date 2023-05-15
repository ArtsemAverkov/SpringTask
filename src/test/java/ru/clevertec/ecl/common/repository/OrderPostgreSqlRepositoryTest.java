package ru.clevertec.ecl.common.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.clevertec.ecl.common.mapper.repositoryMapper.OrderPostgreSqlRepositoryTestMapper;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.entity.order.Order;
import ru.clevertec.ecl.repository.order.OrderRepository;

import java.util.List;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Order Repository Test")
public class OrderPostgreSqlRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final DockerImageName PostgresQL_IMAGE = DockerImageName
            .parse("postgres:14.3");
    @Container
    private  static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>(PostgresQL_IMAGE)
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
    @Transactional
    @DirtiesContext
    void shouldFindByUserOrders(){
        List<Order> byUserId = orderRepository.findByUserId(RequestId.VALUE_1.getValue());
        List<Order> orders = OrderPostgreSqlRepositoryTestMapper.buildResponse();
        testEntityManager.flush();
        testEntityManager.getEntityManager().getTransaction().commit();
        Assertions.assertEquals( orders, byUserId);
    }

    @Test
    @Sql("/INITIAL_ALL_DB_SCRIPT.sql")
    @Transactional
    @DirtiesContext
    void findMostUsedTagWithHighestOrderCostTest() {
        List<Object[]> objects = OrderPostgreSqlRepositoryTestMapper.getObjects();
        List<Object[]> mostUsedTagWithHighestOrderCost = orderRepository.findMostUsedTagWithHighestOrderCost();
        testEntityManager.flush();
        testEntityManager.getEntityManager().getTransaction().commit();
        Assertions.assertArrayEquals(objects.toArray(), mostUsedTagWithHighestOrderCost.toArray());
    }
}


