package ru.clevertec.ecl.common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.extension.order.ValidParameterResolverOrder;
import ru.clevertec.ecl.common.extension.user.ValidParameterResolverUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@DisplayName("Order Service Test")
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
            when(userRepository.findById(RequestId.VALUE_1.getValue())).thenReturn(java.util.Optional.of(user));
            when(giftCertificatesRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(java.util.Optional.of(giftCertificates));
            when(orderRepository.save(any(Order.class))).thenReturn(new Order());
            boolean result = orderApiService.buyGiftCertificate(
                    RequestId.VALUE_1.getValue(), RequestId.VALUE_1.getValue());
            assertTrue(result);
        }

        @Test
        public void getOrdersByUserIdWhenValidInputShouldReturnOrderDtoList(OrderDtoRequest orderDto) {
            Order order = convertToDto(orderDto);
            when(orderRepository.findByUserId(RequestId.VALUE_1.getValue())).thenReturn(Collections.singletonList(order));
            List<OrderDtoRequest> result = orderApiService.getOrdersByUserId(RequestId.VALUE_1.getValue());
            assertEquals(1, result.size());
            assertEquals(order.getId(), result.get(0).getId());
            assertEquals(order.getPurchaseTime(), result.get(0).getPurchaseTime());
        }

        @Test
        public void getAPopularCertificateWhenValidInputShouldReturnListOfObjectArrays(OrderDtoRequest orderDto) {
            Object[] testDataOne = new Object[]{orderDto};
            Object[] testDataTwo = new Object[]{orderDto};
            when(orderRepository.findMostUsedTagWithHighestOrderCost())
                    .thenReturn(Arrays.asList(testDataOne, testDataTwo));
            List<Object[]> result = orderApiService.getAPopularCertificate();
            assertEquals(2, result.size());
            assertEquals(testDataOne[0], result.get(0)[0]);
            assertEquals(testDataOne[0], result.get(1)[0]);
        }

        private User builderUserDto(UserDtoRequest userDto) {
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

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class InvalidData {

        @InjectMocks
        private OrderApiService orderApiService;

        @Mock
        private UserRepository userRepository;

        @Mock
        private GiftCertificatesRepository giftCertificatesRepository;

        @Test
        public void buyGiftCertificateTestWhenUserRepositoryFindUserByIdRequestIsInvalid(){
            when(userRepository.findById(RequestId.VALUE_1.getValue())).thenThrow(IllegalArgumentException.class);
            assertThrows(IllegalArgumentException.class,
                    () -> orderApiService.buyGiftCertificate(RequestId.VALUE_1.getValue(), RequestId.VALUE_2.getValue()));
        }

        @Test
        public void buyGiftCertificateTestWhenGiftCertificateFindByIdRequestIsInvalid(){
            when(userRepository.findById(RequestId.VALUE_1.getValue())).thenReturn(Optional.of(new User()));
            when(giftCertificatesRepository.findById(RequestId.VALUE_2.getValue())).thenThrow(IllegalArgumentException.class);
            assertThrows(IllegalArgumentException.class,
                    () -> orderApiService.buyGiftCertificate(RequestId.VALUE_1.getValue(), RequestId.VALUE_2.getValue()));
        }
    }
}
