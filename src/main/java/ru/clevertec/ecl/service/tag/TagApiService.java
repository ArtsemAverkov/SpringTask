package ru.clevertec.ecl.service.tag;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDto;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.tag.TagRepository;
import java.util.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TagApiService implements TagService{
    private final TagRepository tagRepository;

    public TagApiService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public long create(TagDto tagDto) {
        Tag tag = buildTag(tagDto);
        return tagRepository.save(tag).getId();
    }

    @Override
    public TagDtoResponse read(long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid tag Id:" + id));
        return convertToDto(tag);
    }

    @Override
    public boolean update(TagDto tagDto, Long id) {
        read(id);
        Tag tag = buildTag(tagDto);
        tag.setId(id);
        tagRepository.save(tag);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        read(id);
        tagRepository.deleteById(id);
        return true;
    }

    @Override
    public List<TagDtoResponse> readAll(Pageable pageable) {
        List<Tag> content = tagRepository.findAll(pageable).getContent();
        return getTagDtoResponseList(content);
    }

    private Tag buildTag(TagDto tagDto){
        return Tag.builder()
                .name(tagDto.getName())
                .build();
    }
    public TagDtoResponse convertToDto(Tag tag) {
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
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
