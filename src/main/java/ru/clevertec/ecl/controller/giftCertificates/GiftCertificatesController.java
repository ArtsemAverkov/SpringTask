package ru.clevertec.ecl.controller.giftCertificates;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
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
     * @param id the id of the gift certificate to be deleted
     * @return true if the delete is successful, false otherwise
     */

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable Long id) {
        return giftCertificatesService.delete(id);
    }

    /**
     * Retrieves a list of gift certificates as GiftCertificatesDto objects.
     * @param pageable the pageable object used for pagination
     *  @return a List of GiftCertificatesDto objects
     * @throws Exception if an error occurs while retrieving the gift certificates
     * @GetMapping annotation is used to map HTTP GET requests onto this method.
     * The produces attribute is set to MediaType.APPLICATION_JSON_VALUE to indicate
     * that the response should be in JSON format.
     * The @PageableDefault annotation is used to provide default values for page and size
     * parameters in case they are not present in the request. By default, page is set to 0.
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GiftCertificatesDto> readAll(@PageableDefault(page = 0)Pageable pageable) {
        return giftCertificatesService.readAll(pageable);
    }
}


