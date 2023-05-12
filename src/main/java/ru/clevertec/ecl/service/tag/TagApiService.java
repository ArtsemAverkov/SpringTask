package ru.clevertec.ecl.service.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.tag.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 This class represents the implementation of the TagService interface. It provides methods for CRUD operations
 on Tag entities, using a TagRepository to access the data source.
 */
@Service
@RequiredArgsConstructor
public class TagApiService implements TagService{
    private final TagRepository tagRepository;

    /**
     * Creates a new Tag entity from the provided TagDto, and saves it to the data source.
     * @param tagDto a TagDto instance containing the data to create the Tag entity with.
     * @return the ID of the newly created Tag entity.
     */

    @Override
    public long create(TagDtoRequest tagDto) {
        Tag tag = buildTag(tagDto);
        return tagRepository.save(tag).getId();
    }

    /**
     * Reads a Tag entity from the data source with the specified ID, and returns it as a TagDtoResponse.
     * @param id the ID of the Tag entity to read.
     * @return a TagDtoResponse instance representing the Tag entity with the specified ID.
     * @throws IllegalArgumentException if no Tag entity with the specified ID exists in the data source.
     */
    @Override
    public TagDtoResponse read(long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid tag Id:" + id));
        return convertTagToTagDtoResponse(tag);
    }

    /**
     * Updates a Tag entity in the data source with the provided data.
     * @param tagDto a TagDto instance containing the updated data for the Tag entity.
     * @param id the ID of the Tag entity to update.
     * @return true if the update was successful, false otherwise.
     * @throws IllegalArgumentException if no Tag entity with the specified ID exists in the data source.
     */

    @Override
    public boolean update(TagDtoRequest tagDto, Long id) {
        read(id);
        Tag tag = buildTag(tagDto);
        tag.setId(id);
        tagRepository.save(tag);
        return true;
    }

    /**
     * Deletes a Tag entity from the data source with the specified ID.
     * @param id the ID of the Tag entity to delete.
     * @return true if the deletion was successful, false otherwise.
     * @throws IllegalArgumentException if no Tag entity with the specified ID exists in the data source.
     */

    @Override
    public boolean delete(Long id) {
        read(id);
        tagRepository.deleteById(id);
        return true;
    }

    /**
     * Reads a page of Tag entities from the data source, and returns them as a list of TagDtoResponse instances.
     * @param pageable a Pageable instance specifying the page to read.
     * @return a list of TagDtoResponse instances representing the Tag entities in the specified page.
     */

    @Override
    public List<TagDtoResponse> readAll(Pageable pageable) {
        List<Tag> content = tagRepository.findAll(pageable).getContent();
        return getTagDtoResponseList(content);
    }

    /**
     * Builds a new Tag entity from the provided TagDto.
     * @param tagDto a TagDto instance containing the data to create the Tag entity with.
     * @return a new Tag entity.
     */

    private Tag buildTag(TagDtoRequest tagDto){
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }

    /**
     * Converts a Tag entity to a TagDtoResponse instance.
     * @param tag a Tag entity to convert.
     * @return a Tag
     */
    public TagDtoResponse convertTagToTagDtoResponse(Tag tag) {
        List<GiftCertificatesResponseDto> giftCertificates = new ArrayList<>();
        for (GiftCertificates giftCertificate : tag.getGiftCertificatesList()) {
            giftCertificates.add(GiftCertificatesResponseDto.builder()
                    .id(giftCertificate.getId())
                    .name(giftCertificate.getName())
                    .description(giftCertificate.getDescription())
                    .price(giftCertificate.getPrice())
                    .duration(giftCertificate.getDuration())
                    .build());
        }
        return TagDtoResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .giftCertificates(giftCertificates)
                .build();
    }

    private List<TagDtoResponse> getTagDtoResponseList(List<Tag> tagPage) {
       return tagPage.stream()
                .map(this::convertTagToTagDtoResponse)
                .collect(Collectors.toList());
    }
}
