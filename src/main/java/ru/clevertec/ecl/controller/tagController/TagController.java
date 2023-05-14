package ru.clevertec.ecl.controller.tagController;

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
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.service.tag.TagService;

import java.util.List;

/**
 * The TagController class is a RestController that handles HTTP requests related to tags.
 * This controller maps all requests to the "/tags" endpoint and delegates their processing
 * to the TagService class.
 * The class defines methods for creating, reading, updating, and deleting tags.
 */

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * Creates a new tag based on the provided TagDto object.
     * @param tagDto the TagDto object containing the data for the new tag
     * @return the ID of the newly created tag
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid TagDtoRequest tagDto){
        return tagService.create(tagDto);
    }

    /**
     * Retrieves a tag with the specified ID.
     * @param id the ID of the tag to retrieve
     * @return the TagDtoResponse object representing the tag with the specified ID
     */

    @GetMapping(value= "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse read(@PathVariable  @Valid Long id) {
        return tagService.read(id);
    }

    /**
     * Updates the tag with the specified ID using the data in the provided TagDto object.
     * @param id the ID of the tag to update
     * @param tagDto the TagDto object containing the updated data for the tag
     * @return true if the update was successful, false otherwise
     */

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@PathVariable @Valid Long id, @RequestBody @Valid TagDtoRequest tagDto){
        return tagService.update(tagDto, id);
    }

    /**
     * Deletes the tag with the specified ID.
     * @param id the ID of the tag to delete
     * @return true if the deletion was successful, false otherwise
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable @Valid Long id){
        return tagService.delete(id);
    }

    /**
     * Retrieves all tags, with pagination support.
     * @param pageable the pageable object containing pagination parameters
     * @return a List of TagDtoResponse objects representing the tags
     */

    @GetMapping
    public List<TagDtoResponse> readAll(@PageableDefault Pageable pageable){
        return tagService.readAll(pageable);
    }
}
