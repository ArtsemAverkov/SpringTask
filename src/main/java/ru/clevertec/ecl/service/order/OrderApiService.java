package ru.clevertec.ecl.service.order;

import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.order.OrderDto;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderApiService implements OrderService {
    private final GiftCertificatesRepository giftCertificateRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderApiService(GiftCertificatesRepository giftCertificateRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public boolean buyGiftCertificate(Long userId, Long certificateId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Invalid user Id:" + userId));
        GiftCertificates giftCertificate = giftCertificateRepository.findById(certificateId).orElseThrow(() ->
                new IllegalArgumentException("Invalid gift certificate Id:" + certificateId));
        Order order = new Order();
        order.setCost(giftCertificate.getPrice());
        order.setPurchaseTime(LocalDateTime.now());
        order.setUser(user);
        order.setGiftCertificates(giftCertificate);
        orderRepository.save(order);
        return true;
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .purchaseTime(order.getPurchaseTime())
                .giftCertificates(convertToDtoList(order.getGiftCertificates()))
                .build();
    }

    private List<GiftCertificatesResponseDto> convertToDtoList(GiftCertificates giftCertificates) {
        List<GiftCertificatesResponseDto> giftCertificatesResponseDtoList = new ArrayList<>();
        if (giftCertificates != null) {
            GiftCertificatesResponseDto giftCertificatesDto = GiftCertificatesResponseDto.builder()
                    .id(giftCertificates.getId())
                    .name(giftCertificates.getName())
                    .description(giftCertificates.getDescription())
                    .price(giftCertificates.getPrice())
                    .build();
            giftCertificatesResponseDtoList.add(giftCertificatesDto);
        }
        return giftCertificatesResponseDtoList;
    }
}


