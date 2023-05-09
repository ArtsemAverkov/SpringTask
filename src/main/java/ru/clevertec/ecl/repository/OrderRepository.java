package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    @Query("SELECT o.user, o.giftCertificates.tag, SUM(o.cost) as totalCost " +
            "FROM Order o " +
            "GROUP BY o.user, o.giftCertificates.tag " +
            "HAVING o.user = (SELECT u FROM User u ORDER BY SUM(o.cost) DESC LIMIT 1) " +
            "ORDER BY totalCost DESC")
    List<Object[]> findMostUsedTagWithHighestOrderCost();
}
