package ru.clevertec.ecl.service.order;

import ru.clevertec.ecl.entity.Order;

import java.util.List;

public interface OrderService {
    Order buyGiftCertificate(Long userId, Long certificateId, Double total_price);
    List<Order> getOrdersByUserId(Long userId);
}
