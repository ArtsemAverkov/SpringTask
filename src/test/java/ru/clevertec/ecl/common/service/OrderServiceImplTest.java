package ru.clevertec.ecl.common.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.extension.order.ValidParameterResolverOrder;
import ru.clevertec.ecl.common.extension.user.ValidParameterResolverUser;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.order.OrderDtoRequest;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.order.Order;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.repository.order.OrderRepository;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.repository.user.UserRepository;
import ru.clevertec.ecl.service.order.OrderApiService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class OrderServiceImplTest {
    @Nested
    @ExtendWith({MockitoExtension.class,
            ValidParameterResolverOrder.class,
            ValidParameterResolverUser.class,
            ValidParameterResolverGiftCertificates.class})
    public class ValidData {
        @InjectMocks
        private OrderApiService orderApiService;

        @Mock
        private OrderRepository orderRepository;
        @Mock
        private UserRepository userRepository;
        @Mock
        private GiftCertificatesRepository giftCertificatesRepository;


        @Test
        public void buyGiftCertificateWhenValidInputShouldReturnTrue
                (UserDtoRequest userDto, GiftCertificatesDtoRequest giftCertificatesDto) {
            User user = builderUserDto(userDto);
            GiftCertificates giftCertificates = buildGiftCertificates(giftCertificatesDto);
            Long userId = 1L;
            Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
            Mockito.when(giftCertificatesRepository.findById(giftCertificatesDto.getId()))
                    .thenReturn(java.util.Optional.of(giftCertificates));
            Mockito.when(orderRepository.save(any(Order.class))).thenReturn(new Order());
            boolean result = orderApiService.buyGiftCertificate(userId, giftCertificatesDto.getId());
            assertEquals(true, result);
        }

        @Test
        public void getOrdersByUserIdWhenValidInputShouldReturnOrderDtoList(OrderDtoRequest orderDto) {
            Long userId = 1L;
            Order order = convertToDto(orderDto);
            Mockito.when(orderRepository.findByUserId(userId)).thenReturn(Collections.singletonList(order));
            List<OrderDtoRequest> result = orderApiService.getOrdersByUserId(userId);
            assertEquals(1, result.size());
            assertEquals(order.getId(), result.get(0).getId());
            assertEquals(order.getPurchaseTime(), result.get(0).getPurchaseTime());
        }

        @Test
        public void getAPopularCertificateWhenValidInputShouldReturnListOfObjectArrays(OrderDtoRequest orderDto) {
            Object[] testDataOne = new Object[]{orderDto};
            Object[] testDataTwo = new Object[]{orderDto};
            Mockito.when(orderRepository.findMostUsedTagWithHighestOrderCost())
                    .thenReturn(Arrays.asList(testDataOne, testDataTwo));
            List<Object[]> result = orderApiService.getAPopularCertificate();
            assertEquals(2, result.size());
            assertEquals(testDataOne[0], result.get(0)[0]);
            assertEquals(testDataOne[1], result.get(0)[1]);
            assertEquals(testDataOne[0], result.get(1)[0]);
            assertEquals(testDataOne[1], result.get(1)[1]);
        }

        private User builderUserDto(UserDtoRequest userDto){
            return User.builder()
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .password(userDto.getEmail())
                    .build();
        }

        private GiftCertificates buildGiftCertificates(GiftCertificatesDtoRequest giftCertificatesDto) {
            LocalDateTime now = LocalDateTime.now();
            String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
            return GiftCertificates.builder()
                    .id(giftCertificatesDto.getId())
                    .name(giftCertificatesDto.getName())
                    .price(giftCertificatesDto.getPrice())
                    .description(giftCertificatesDto.getDescription())
                    .duration(giftCertificatesDto.getDuration())
                    .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                    .create_date(isoDateTime)
                    .last_update_date(isoDateTime)
                    .build();
        }

        private Order convertToDto(OrderDtoRequest order) {
            return Order.builder()
                    .id(order.getId())
                    .purchaseTime(order.getPurchaseTime())
                    .build();
        }
    }

}
