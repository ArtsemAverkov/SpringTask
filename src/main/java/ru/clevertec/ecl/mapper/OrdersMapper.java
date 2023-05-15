package ru.clevertec.ecl.mapper;

import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.order.OrderDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.order.Order;
import ru.clevertec.ecl.entity.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 The OrdersMapper class provides methods for mapping between Order and OrderDtoRequest objects,
 and GiftCertificates and GiftCertificatesResponseDto objects.
 */
public class OrdersMapper {

    /**
     Builds an Order object based on the provided User and GiftCertificates objects.
     @param user a User object
     @param giftCertificate a GiftCertificates object
     @return an Order object
     */

    public static Order buildOrder(User user, GiftCertificates giftCertificate){
        return Order.builder()
                .cost(giftCertificate.getPrice())
                .purchaseTime(LocalDateTime.now())
                .user(user)
                .giftCertificates(giftCertificate)
                .build();
    }

    /**
     Converts an Order object to an OrderDtoRequest object.
     @param order an Order object to be converted
     @return an OrderDtoRequest object
     */

    public static OrderDtoRequest convertOrderToOrderDto(Order order) {
        return OrderDtoRequest.builder()
                .id(order.getId())
                .purchaseTime(order.getPurchaseTime())
                .giftCertificates(convertGiftCertificatesToGiftCertificatesDtoList(order.getGiftCertificates()))
                .build();
    }

    /**
     Converts a GiftCertificates object to a List of GiftCertificatesResponseDto objects.
     @param giftCertificates a GiftCertificates object to be converted
     @return a List of GiftCertificatesResponseDto objects
     */
    public static List<GiftCertificatesResponseDto> convertGiftCertificatesToGiftCertificatesDtoList
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
