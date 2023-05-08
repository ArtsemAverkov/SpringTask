package ru.clevertec.ecl.service.order;

import ru.clevertec.ecl.dto.order.OrderDto;
import ru.clevertec.ecl.entity.Order;

import java.util.List;

public interface OrderService {
    boolean buyGiftCertificate(Long userId, Long certificateId);
    List<OrderDto> getOrdersByUserId(Long userId);
}
