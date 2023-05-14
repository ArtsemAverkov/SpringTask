package ru.clevertec.ecl.common.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.order.OrderDtoRequest;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.service.order.OrderService;
import ru.clevertec.ecl.service.user.UserService;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.extension.order.ValidParameterResolverOrder;
import ru.clevertec.ecl.common.extension.user.ValidParameterResolverUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ContextConfiguration(classes = SpringTaskApplication.class)
@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@ExtendWith({ValidParameterResolverUser.class,
        ValidParameterResolverOrder.class,
        ValidParameterResolverGiftCertificates.class})
@DisplayName("Testing User Controller")
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void read(UserDtoRequest userDto) throws Exception {
        User builderuser = builderUser(userDto);
        when(userService.read(builderuser.getId())).thenReturn(builderuser);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}",builderuser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildJson(userDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(userDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(userDto.getEmail())));
        verify(userService).read(builderuser.getId());
    }

    @Test
    public void create(UserDtoRequest userDto) throws Exception {
        when(userService.create(any(UserDtoRequest.class))).thenReturn(RequestId.VALUE_1.getValue());
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildJson(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(String.valueOf(RequestId.VALUE_1.getValue())));
        verify(userService).create(any(UserDtoRequest.class));
    }

    @Test
    public void getOrdersByUserId(OrderDtoRequest orderDto) throws Exception {
        List<OrderDtoRequest> expectedOrders = Arrays.asList(orderDto, orderDto);
        when(orderService.getOrdersByUserId(RequestId.VALUE_1.getValue())).thenReturn(expectedOrders);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", RequestId.VALUE_1.getValue())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedOrders.size())))
                .andExpect(jsonPath("$.[0].id")
                        .value(orderDto.getId()))
                .andExpect(jsonPath("$.[0].giftCertificates[0].id")
                        .value(orderDto.getGiftCertificates().get(0).getId().intValue()))
                .andExpect(jsonPath("$.[0].giftCertificates[0].name")
                        .value(orderDto.getGiftCertificates().get(0).getName()))
                .andExpect(jsonPath("$.[0].giftCertificates[0].description")
                        .value(orderDto.getGiftCertificates().get(0).getDescription()))
                .andExpect(jsonPath("$.[0].giftCertificates[0].price")
                        .value(orderDto.getGiftCertificates().get(0).getPrice()))
                .andExpect(jsonPath("$.[0].giftCertificates[0].duration")
                        .value(orderDto.getGiftCertificates().get(0).getDuration()));
        verify(orderService).getOrdersByUserId(RequestId.VALUE_1.getValue());
    }

    @Test
    public void buyGiftCertificate() throws Exception {
        when(orderService.buyGiftCertificate(RequestId.VALUE_1.getValue(), RequestId.VALUE_2.getValue())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/buy")
                        .param("userId", RequestId.VALUE_1.getValue().toString())
                        .param("certificateId", RequestId.VALUE_2.getValue().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(orderService).buyGiftCertificate(RequestId.VALUE_1.getValue(), RequestId.VALUE_2.getValue());
    }

    @Test
    public void getAPopularCertificate(GiftCertificatesDtoRequest giftCertificatesDto, OrderDtoRequest orderDto) throws Exception {
        List<Object[]> orders = new ArrayList<>();
        orders.add(new Object[]{orderDto, giftCertificatesDto});
        when(orderService.getAPopularCertificate()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/getAPopularCertificate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0][0].id").value(orderDto.getId()));
        verify(orderService).getAPopularCertificate();
        }

        private User builderUser(UserDtoRequest userDtoRequest){
        return User.builder()
                .id(RequestId.VALUE_1.getValue())
                .name(userDtoRequest.getName())
                .email(userDtoRequest.getEmail())
                .password(userDtoRequest.getPassword())
                .build();

        }

        private String buildJson(UserDtoRequest userDto){
        return "{\n" +
                "  \"name\": \""+userDto.getName()+"\",\n" +
                "  \"email\": \""+userDto.getEmail()+"\",\n" +
                "  \"password\": \""+userDto.getPassword()+"\"\n" +
                "}";
        }
}
