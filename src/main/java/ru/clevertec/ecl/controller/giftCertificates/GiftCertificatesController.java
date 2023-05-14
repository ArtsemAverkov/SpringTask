package ru.clevertec.ecl.controller.giftCertificates;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
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
@RequiredArgsConstructor
public class GiftCertificatesController {
    private final GiftCertificatesService giftCertificatesService;

    /**
     * Create a new gift certificate.
     * @param giftCertificates the GiftCertificatesDto to be created
     * @return the long id of the created giftCertificates
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public long create(@RequestBody @Valid GiftCertificatesDtoRequest giftCertificates) {
        return giftCertificatesService.create(giftCertificates);
    }

    /**
     * Read a gift certificate by id.
     * @param id the id of the gift certificate to be read
     * @return the gift certificate with the given id
     * @throws Exception if the gift certificate is not found
     */

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificatesDtoRequest read(@PathVariable @Valid Long id) throws Exception {
        return giftCertificatesService.read(id);
    }

    /**
     * Update a gift certificate by id.
     * @param id the id of the gift certificate to be updated
     * @param giftCertificates the updated GiftCertificatesDto
     * @return true if the update is successful, false otherwise
     */

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update (@PathVariable @Valid Long id,
                           @RequestBody @Valid GiftCertificatesDtoRequest giftCertificates) {
        return giftCertificatesService.update(giftCertificates, id);
    }

    /**
     * Delete a gift certificate by id.
     * @param id the id of the gift certificate to be deleted
     * @return true if to delete is successful, false otherwise
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable @Valid Long id) {
        return giftCertificatesService.delete(id);
    }

    /**
     * Retrieves a list of gift certificates as GiftCertificatesDto objects.
     * @param pageable the pageable object used for pagination
     *  @return a List of GiftCertificatesDto object
     * @GetMapping annotation is used to map HTTP GET requests onto this method.
     */

    @GetMapping
    public List<GiftCertificatesDtoRequest> readAll(@PageableDefault Pageable pageable) {
        return giftCertificatesService.readAll(pageable);
    }
}


