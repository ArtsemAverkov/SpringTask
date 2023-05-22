package ru.clevertec.ecl.common.utill.serviceMapper;

import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.order.OrderDtoRequest;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.order.Order;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderServiceImplTestMapper {

    public static User builderUserDto(UserDtoRequest userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getEmail())
                .build();
    }

    public static GiftCertificates buildGiftCertificates(GiftCertificatesDtoRequest giftCertificatesDto) {
        LocalDateTime now = LocalDateTime.now();
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
        return GiftCertificates.builder()
                .name(giftCertificatesDto.getName())
                .price(giftCertificatesDto.getPrice())
                .description(giftCertificatesDto.getDescription())
                .duration(giftCertificatesDto.getDuration())
                .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                .create_date(isoDateTime)
                .last_update_date(isoDateTime)
                .build();
    }

    public static Order convertToDto(OrderDtoRequest order) {
        return Order.builder()
                .id(order.getId())
                .purchaseTime(order.getPurchaseTime())
                .build();
    }
}
