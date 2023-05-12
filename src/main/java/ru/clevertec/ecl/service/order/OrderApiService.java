package ru.clevertec.ecl.service.order;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderApiService implements OrderService{
    private final GiftCertificatesRepository giftCertificateRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderApiService(GiftCertificatesRepository giftCertificateRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public Order buyGiftCertificate(Long userId, Long certificateId, Double total_price) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Invalid user Id:" + userId));
        GiftCertificates giftCertificate = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new IllegalArgumentException("Invalid gift certificate Id:" + certificateId));
        Order order = new Order();
        order.setCost(total_price);
        order.setPurchaseTime(LocalDateTime.now());
        order.setUser(user);
        order.setGiftCertificates(giftCertificate);
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
