package ru.clevertec.ecl.service.order;

import ru.clevertec.ecl.dto.order.OrderDtoRequest;

import java.util.List;

public interface OrderService {
    boolean buyGiftCertificate(Long userId, Long certificateId);
    List<OrderDtoRequest> getOrdersByUserId(Long userId);
    List<Object[]> getAPopularCertificate();
}
