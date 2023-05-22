package ru.clevertec.ecl.common.utill.repositoryMapper;

import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.order.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderPostgreSqlRepositoryTestMapper {

    public static List<Object[]> getObjects() {
        List<Object[]> orders = new ArrayList<>();
        orders.add(new Object[]{"Restaurant Voucher",1L});
        return orders;
    }

    public static List<Order> buildResponse() {
        List<Order> listOrder = new ArrayList<>();
        listOrder.add(new Order(
                1L,
                20.0,
                LocalDateTime.parse("2022-05-10T13:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                new GiftCertificates(
                        1L,
                        "Restaurant Voucher",
                        "Enjoy a meal at a local restaurant",
                        50.0,
                        365L,
                        "2022-05-10 13:00:00",
                        "2022-05-10 13:00:00"
                )));
        return listOrder;
    }
}
