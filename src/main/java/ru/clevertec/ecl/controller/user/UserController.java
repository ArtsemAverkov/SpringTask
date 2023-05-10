package ru.clevertec.ecl.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.order.OrderDto;
import ru.clevertec.ecl.dto.user.UserDto;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.service.order.OrderService;
import ru.clevertec.ecl.service.user.UserService;

import java.util.List;


/**
 * Controller for handling User-related requests.
 * @RestController annotation is used to indicate that this class is a controller
 * and that all methods should return JSON-formatted responses by default.
 * @RequestMapping annotation is used to map all requests with "/user" URI to this controller.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Retrieves a user with the specified ID.
     * @param id the ID of the user to retrieve
     * @return a User object representing the user
     */
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public User read(@PathVariable Long id) {
        return userService.read(id);
    }

    /**
     * Creates a new user with the specified details.
     * @param userDto a UserDto object containing the user details
     * @return the ID of the newly created user
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public long create(@RequestBody UserDto userDto){
        return userService.create(userDto);
    }

    /**
     * Retrieves a list of orders for the specified user.
     * @param userId the ID of the user to retrieve orders for
     * @return a List of OrderDto objects representing the user's orders
     */
    @GetMapping(value = "/{userId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }
    /**
     * Buys a gift certificate for the specified user.
     * @param userId the ID of the user who is buying the gift certificate
     * @param certificateId the ID of the gift certificate to buy
     * @return true if the purchase was successful, false otherwise
     */
    @GetMapping(value = "/buy", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean buyGiftCertificate(@RequestParam(name = "userId") Long userId,
                                    @RequestParam(name = "certificateId") Long certificateId) {
        return orderService.buyGiftCertificate(userId, certificateId);
    }

    /**
     * Retrieves the most popular gift certificate.
     * @return a list of objects containing information about the certificate and its total number of orders.
     */
    @GetMapping(value = "/getAPopularCertificate",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getAPopularCertificate(){
        return orderService.getAPopularCertificate();
    }

}
