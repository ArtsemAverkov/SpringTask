package ru.clevertec.ecl.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.order.OrderDtoRequest;
import ru.clevertec.ecl.entity.order.Order;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.order.OrderRepository;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
     * @throws IllegalArgumentException if the user ID or gift certificate ID is invalid
     */
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

    /**
     * Method to get a list of orders for a user.
     * @param userId the ID of the user
     * @return a list of OrderDto objects representing the orders for the user
     */

    @Override
    public List<OrderDtoRequest> getOrdersByUserId(Long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream()
                .map(this::convertOrderToOrderDto)
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

    /**
     * Method to convert an Order object to an OrderDto object.
     * @param order the Order object to convert
     * @return an OrderDto object representing the Order
     */


    private OrderDtoRequest convertOrderToOrderDto(Order order) {
        return OrderDtoRequest.builder()
                .id(order.getId())
                .purchaseTime(order.getPurchaseTime())
                .giftCertificates(convertGiftCertificatesToGiftCertificatesDtoList(order.getGiftCertificates()))
                .build();
    }

    /**
     * Method to convert a GiftCertificates object to a list of GiftCertificatesResponseDto objects.
     * @param giftCertificates the GiftCertificates object to convert
     * @return a list of GiftCertificatesResponseDto objects representing the GiftCertificates
     */
    private List<GiftCertificatesResponseDto> convertGiftCertificatesToGiftCertificatesDtoList
    (GiftCertificates giftCertificates) {
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


