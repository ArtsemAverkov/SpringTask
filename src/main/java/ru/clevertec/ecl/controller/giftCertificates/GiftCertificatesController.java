package ru.clevertec.ecl.controller.giftCertificates;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesService;

import java.util.List;

/**

 This class represents the REST API controller for managing GiftCertificates resources.
 All requests related to GiftCertificates are handled by this controller.
 @author [ArtsemAverkov]
 @version [1.0]
 */

@RestController
@RequestMapping("/certificates")

public class GiftCertificatesController {
    private final GiftCertificatesService giftCertificatesService;

    public GiftCertificatesController(GiftCertificatesService giftCertificatesService) {
        this.giftCertificatesService = giftCertificatesService;
    }


    /**
     * Create a new gift certificate.
     *
     * @param giftCertificates the GiftCertificatesDto to be created
     * @return the long id of the created giftCertificates
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public long create(@RequestBody GiftCertificatesDto giftCertificates) {
        return giftCertificatesService.create(giftCertificates);
    }

    /**
     * Read a gift certificate by id.
     *
     * @param id the id of the gift certificate to be read
     * @return the gift certificate with the given id
     * @throws Exception if the gift certificate is not found
     */

    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificatesDto read(@PathVariable Long id) throws Exception {
        return giftCertificatesService.read(id);
    }

    /**
     * Update a gift certificate by id.
     *
     * @param id the id of the gift certificate to be updated
     * @param giftCertificates the updated GiftCertificatesDto
     * @return true if the update is successful, false otherwise
     */

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update (@PathVariable Long id, @RequestBody GiftCertificatesDto giftCertificates) {
        return giftCertificatesService.update(giftCertificates, id);
    }

    /**
     * Delete a gift certificate by id.
     *
     * @param id the id of the gift certificate to be deleted
     * @return true if the delete is successful, false otherwise
     */

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable Long id) {
        return giftCertificatesService.delete(id);
    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GiftCertificatesDto> readAll(@PageableDefault(page = 0)Pageable pageable) {
        return giftCertificatesService.readAll(pageable);
    }
}


