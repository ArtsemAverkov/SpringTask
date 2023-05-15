package ru.clevertec.ecl.common.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.controller.tagController.TagController;
import ru.clevertec.ecl.controller.user.UserController;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.service.order.OrderService;
import ru.clevertec.ecl.service.tag.TagService;
import ru.clevertec.ecl.service.user.UserService;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SpringTaskApplication.class)
@WebMvcTest({TagController.class, UserController.class})

@RunWith(SpringRunner.class)
public class ControllerExceptionHandlerTest {

    @MockBean
    private TagService tagService;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenEntityNotFoundExceptionWhenCreateThenReturnsIncorrectRequest() throws Exception {
        when(tagService.create(ArgumentMatchers.any(TagDtoRequest.class))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(""))
                .andExpect(status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value ("INCORRECT REQUEST"));
    }

    @Test
    void givenNoSuchElementExceptionWhenHandleThenReturnsNoSuchElement() throws Exception {
        when(tagService.read(ArgumentMatchers.anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(MockMvcRequestBuilders.get("/tags/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("NO SUCH ELEMENT"));
    }

    @Test
    void givenHttpRequestMethodNotSupportedExceptionWhenHandlingThenReturnsMethodNotAllowed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/tags"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("METHOD NOT ALLOWED"));
    }

    @Test
    void givenMethodArgumentNotValidExceptionWhenHandleThenReturnsValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\": 50}"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("VALIDATION ERROR"));
    }

    @Test
    void givenMissingServletRequestParameterExceptionWhenHandleThenReturnsNoCorrectRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/buy")
                        .param("certificateId", String.valueOf(RequestId.VALUE_1.getValue()))
                        .param("certificateId", String.valueOf(RequestId.VALUE_1.getValue())))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("NO CORRECT REQUEST"));
    }
}
