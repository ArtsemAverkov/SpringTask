package ru.clevertec.ecl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ru.clevertec.ecl.dto.GiftCertificatesDto;
import ru.clevertec.ecl.entity.GiftCertificates;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesService;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificatesController {
    private final GiftCertificatesService giftCertificatesService;

    public GiftCertificatesController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
    }

    /**
     * this method creates a new giftCertificates
     * @param giftCertificates get from server
     * @return the long id of the created giftCertificates
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody GiftCertificatesDto giftCertificates) {
        return giftCertificatesService.create(giftCertificates);
    }

    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificates read(@PathVariable Long id) throws Exception {
        return giftCertificatesService.read(id);
    }
    /**
     * this method updates giftCertificates by id
     * @param giftCertificates get from server
     * @param id         get from server
     * @return successful and unsuccessful update
     */

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update (@PathVariable Long id, @RequestBody GiftCertificatesDto giftCertificates) {
        return giftCertificatesService.update(giftCertificates, id);
    }

    /**
     * this method removes the giftCertificates from the database
     * @param id get from server
     * @return successful and unsuccessful delete
     */

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable Long id) {
        return giftCertificatesService.delete(id);
    }

    /**
     * this method returns a collection of all giftCertificates in the database
     * @return collection of all giftCertificates
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object[]> readAll(@RequestParam(required = false) String tagName,
                                  @RequestParam(required = false) String sort,
                                  @RequestParam(required = false) String order) {
        String orderBy = sort != null ? sort : "gc.name";
        String orderType = order != null ? order : "ASC";
        return giftCertificatesService.readAll(tagName, orderBy, orderType);
    }
}


