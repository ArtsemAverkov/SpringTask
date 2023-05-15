package ru.clevertec.ecl.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.order.OrderDtoRequest;
import ru.clevertec.ecl.entity.order.Order;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.order.OrderRepository;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.repository.user.UserRepository;
import ru.clevertec.ecl.mapper.OrdersMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderApiService implements OrderService {

    private final GiftCertificatesRepository giftCertificateRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    /**
     * Method to buy a gift certificate for a user.
     * @param userId the ID of the user
     * @param certificateId the ID of the gift certificate
     * @return true if the gift certificate was successfully purchased, false otherwise
     */

    @Override
    public boolean buyGiftCertificate(Long userId, Long certificateId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Invalid user Id:" + userId));
        GiftCertificates giftCertificate = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new IllegalArgumentException("Invalid gift certificate Id:" + certificateId));
        Order buildOrder = OrdersMapper.buildOrder(user, giftCertificate);
        orderRepository.save(buildOrder);
        return true;
    }

    /**
     * Method to get a list of orders for a user.
     * @param userId the ID of the user
     * @return a list of OrderDto objects representing the orders for the user
     */

    @Override
    public List<OrderDtoRequest> getOrdersByUserId(Long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream()
                .map(OrdersMapper::convertOrderToOrderDto)
                .collect(Collectors.toList());
    }

    /**
     * Method to get the most popular gift certificate.
     * @return a list of Object[] arrays representing the most popular gift certificate(s)
     */
    @Override
    public List<Object[]> getAPopularCertificate() {
        return orderRepository.findMostUsedTagWithHighestOrderCost();
    }
}


