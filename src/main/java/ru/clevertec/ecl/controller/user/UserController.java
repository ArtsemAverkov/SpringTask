package ru.clevertec.ecl.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.entity.user.User;
import ru.clevertec.ecl.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<User> read(@PathVariable Long id){
        return userService.read(id);
    }

    @GetMapping(value = "/by/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String byGiftCertificates(@PathVariable Long id){
        return "by"+ id;
    }
}
