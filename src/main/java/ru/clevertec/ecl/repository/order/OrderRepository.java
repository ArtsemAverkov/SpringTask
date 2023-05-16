package ru.clevertec.ecl.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.entity.order.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    @Query("SELECT gc.name, COUNT(*) AS total_purchases FROM Order o JOIN GiftCertificates gc " +
            "ON o.giftCertificates.id = gc.id GROUP BY gc.id ORDER BY total_purchases DESC LIMIT 1")
    List<Object[]> findMostUsedTagWithHighestOrderCost();

}
