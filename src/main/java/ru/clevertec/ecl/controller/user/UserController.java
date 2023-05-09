package ru.clevertec.ecl.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.order.OrderDto;
import ru.clevertec.ecl.dto.userDto.UserDto;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.service.order.OrderService;
import ru.clevertec.ecl.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User read(@PathVariable Long id) {
        return userService.read(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public long create(@RequestBody UserDto userDto){
        return userService.create(userDto);
    }


    @GetMapping(value = "/{userId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping(value = "/buy", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean buyGiftCertificate(@RequestParam(name = "userId") Long userId,
                                    @RequestParam(name = "certificateId") Long certificateId) {
        return orderService.buyGiftCertificate(userId, certificateId);
    }

    @GetMapping(value = "/getAPopularCertificate",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getAPopularCertificate(){
        return orderService.getAPopularCertificate();
    }

}
