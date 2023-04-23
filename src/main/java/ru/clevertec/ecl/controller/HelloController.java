package ru.clevertec.ecl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.clevertec.ecl.entity.GiftCertificates;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesService;


@Controller
@ResponseBody
public class HelloController {

    private final GiftCertificatesService giftCertificatesService;

    public HelloController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
    }

    @GetMapping("/hello")
    public long hello() {
        return giftCertificatesService.create(new GiftCertificates());
    }
}

