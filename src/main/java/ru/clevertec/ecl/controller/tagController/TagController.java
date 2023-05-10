package ru.clevertec.ecl.controller.tagController;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ru.clevertec.ecl.dto.tag.TagDto;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.service.tag.TagService;

import java.util.List;

/**
 * The TagController class is a RestController that handles HTTP requests related to tags.
 * This controller maps all requests to the "/tag" endpoint and delegates their processing
 * to the TagService class.
 * The class defines methods for creating, reading, updating, and deleting tags.
 */

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Creates a new tag based on the provided TagDto object.
     * @param tagDto the TagDto object containing the data for the new tag
     * @return the ID of the newly created tag
     * @throws Exception if an error occurs while creating the tag
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody TagDto tagDto){
        return tagService.create(tagDto);
    }

    /**
     * Retrieves a tag with the specified ID.
     * @param id the ID of the tag to retrieve
     * @return the TagDtoResponse object representing the tag with the specified ID
     * @throws Exception if an error occurs while retrieving the tag
     */

    @GetMapping(value= "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse read(@PathVariable  Long id) {
        return tagService.read(id);
    }

    /**
     * Updates the tag with the specified ID using the data in the provided TagDto object.
     * @param id the ID of the tag to update
     * @param tagDto the TagDto object containing the updated data for the tag
     * @return true if the update was successful, false otherwise
     * @throws Exception if an error occurs while updating the tag
     */
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@PathVariable Long id, @RequestBody TagDto tagDto){
        return tagService.update(tagDto, id);
    }

    /**
     * Deletes the tag with the specified ID.
     * @param id the ID of the tag to delete
     * @return true if the deletion was successful, false otherwise
     * @throws Exception if an error occurs while deleting the tag
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable Long id){
        return tagService.delete(id);
    }

    /**
     * Retrieves all tags, with pagination support.
     * @param pageable the pageable object containing pagination parameters
     * @return a List of TagDtoResponse objects representing the tags
     * @throws Exception if an error occurs while retrieving the tags
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagDtoResponse> readAll(@PageableDefault(page = 0)Pageable pageable){
        return tagService.readAll(pageable);
    }
}
