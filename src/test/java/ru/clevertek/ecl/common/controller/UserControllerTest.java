package ru.clevertek.ecl.common.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.ecl.SpringTaskApplication;
import ru.clevertec.ecl.controller.user.UserController;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
import ru.clevertec.ecl.dto.order.OrderDto;
import ru.clevertec.ecl.dto.user.UserDto;
import ru.clevertec.ecl.service.order.OrderService;
import ru.clevertec.ecl.service.user.UserService;
import ru.clevertek.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertek.ecl.common.extension.order.ValidParameterResolverOrder;
import ru.clevertek.ecl.common.extension.user.ValidParameterResolverUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = SpringTaskApplication.class)
@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@ExtendWith({ValidParameterResolverUser.class,
        ValidParameterResolverOrder.class,
        ValidParameterResolverGiftCertificates.class})
public class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void read(UserDto userDto) throws Exception {
        Long userId = 1L;
        Mockito.when(userService.create(any(UserDto.class))).thenReturn(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \""+userDto.getName()+"\",\n" +
                        "  \"email\": \""+userDto.getEmail()+"\",\n" +
                        "  \"password\": \""+userDto.getPassword()+"\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(userDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(userDto.getEmail())));
        Mockito.verify(userService).create(any(UserDto.class));
    }

    @Test
    public void create(UserDto userDto) throws Exception {
        Long expectedUserId = 1L;
        Mockito.when(userService.create(any(UserDto.class))).thenReturn(expectedUserId);
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \""+userDto.getName()+"\",\n" +
                                "  \"email\": \""+userDto.getEmail()+"\",\n" +
                                "  \"password\": \""+userDto.getPassword()+"\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(String.valueOf(1L)));
        Mockito.verify(userService).create(any(UserDto.class));
    }

    @Test
    public void getOrdersByUserId(OrderDto orderDto) throws Exception {
        Long userId = 1L;
        List<OrderDto> expectedOrders = Arrays.asList(orderDto, orderDto);
        Mockito.when(orderService.getOrdersByUserId(userId)).thenReturn(expectedOrders);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(orderDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseTime",
                        Matchers.is(orderDto.getPurchaseTime())));

        Mockito.verify(orderService).getOrdersByUserId(userId);
    }

    @Test
    public void buyGiftCertificate() throws Exception {
        Long userId = 1L;
        Long certificateId = 2L;
        Mockito.when(orderService.buyGiftCertificate(userId, certificateId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/buy")
                        .param("userId", userId.toString())
                        .param("certificateId", certificateId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        Mockito.verify(orderService).buyGiftCertificate(userId, certificateId);
    }


    @Test
    public void getAPopularCertificate(GiftCertificatesDto giftCertificatesDto, OrderDto orderDto) throws Exception {
        List<Object[]> orders = new ArrayList<>();
        orders.add(new Object[]{orderDto, giftCertificatesDto});
        Mockito.when(orderService.getAPopularCertificate()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/getAPopularCertificate")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0][0]").value(orderDto.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0][2]").value(giftCertificatesDto))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0][1]").value(orderDto.getPurchaseTime()));


        Mockito.verify(orderService).getAPopularCertificate();
        }
}
