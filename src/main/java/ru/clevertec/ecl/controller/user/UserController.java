package ru.clevertec.ecl.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.ecl.dto.order.OrderDtoRequest;
import ru.clevertec.ecl.dto.user.UserDtoRequest;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.service.order.OrderService;
import ru.clevertec.ecl.service.user.UserService;

import java.util.List;

/**
 * Controller for handling User-related requests.
 * This controller maps all requests to the "/user" endpoint and delegates their processing
 * to the UserService and  OrderService class.
 */

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    /**
     * Retrieves a user with the specified ID.
     * @param id the ID of the user to retrieve
     * @return a User object representing the user
     */

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User read(@PathVariable @Valid Long id) {
        return userService.read(id);
    }

    /**
     * Creates a new user with the specified details.
     * @param userDto a UserDto object containing the user details
     * @return the ID of the newly created user
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public long create(@RequestBody @Valid UserDtoRequest userDto){
        return userService.create(userDto);
    }

    /**
     * Retrieves a list of orders for the specified user.
     * @param userId the ID of the user to retrieve orders for
     * @return a List of OrderDto objects representing the user's orders
     */

    @GetMapping(value = "/{userId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDtoRequest> getOrdersByUserId(@PathVariable @Valid Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    /**
     * Buys a gift certificate for the specified user.
     * @param userId the ID of the user who is buying the gift certificate
     * @param certificateId the ID of the gift certificate to buy
     * @return true if the purchase was successful, false otherwise
     */

    @GetMapping(value = "/buy")
    public boolean buyGiftCertificate(@RequestParam(name = "userId") @Valid Long userId,
                                    @RequestParam(name = "certificateId") @Valid Long certificateId) {
        return orderService.buyGiftCertificate(userId, certificateId);
    }

    /**
     * Retrieves the most popular gift certificate.
     * @return a list of objects containing information about the certificate and its total number of orders.
     */

    @GetMapping(value = "/getAPopularCertificate")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> getAPopularCertificate(){
        return orderService.getAPopularCertificate();
    }
}
