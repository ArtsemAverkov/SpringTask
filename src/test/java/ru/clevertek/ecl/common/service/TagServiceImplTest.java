package ru.clevertek.ecl.common.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.tag.TagRepository;
import ru.clevertec.ecl.service.tag.TagApiService;
import ru.clevertek.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertek.ecl.common.extension.tag.InvalidParameterResolverTag;
import ru.clevertek.ecl.common.extension.tag.ValidParameterResolverTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagServiceImplTest {

    @Nested
    @ExtendWith({MockitoExtension.class,
                ValidParameterResolverTag.class,
                ValidParameterResolverGiftCertificates.class})
    public class ValidData{
        @InjectMocks
        private TagApiService tagApiService;

        @Mock
        private TagRepository tagRepository;

        @Test
        void shouldGetTagWhenTagValid(TagDtoRequest tagDto, GiftCertificatesDtoRequest giftCertificatesDto){
            Tag tag = buildTag(tagDto);
            TagDtoResponse tagDtoResponse = buildTagDtoResponse(tagDto, giftCertificatesDto);
            Mockito.when(tagRepository.findById(tagDto.getId())).thenReturn(Optional.ofNullable(tag));
            Assertions.assertEquals(tagDtoResponse, tagApiService.read(tagDto.getId()));
            Mockito.verify(tagRepository, Mockito.times(1)).findById(tagDtoResponse.getId());
        }

        @Test
        void shouldDeleteTagWhenTagIsValid(TagDtoRequest tagDto){
            Tag tag = buildTag(tagDto);
            Mockito.when(tagRepository.findById(tagDto.getId())).thenReturn(Optional.ofNullable(tag));
            Assertions.assertTrue(tagApiService.delete(tagDto.getId()));
            Mockito.verify(tagRepository, Mockito.timeout(1)).deleteById(tagDto.getId());
        }


        @Test
        void shouldUpdateTagWhenTagIsValid(TagDtoRequest tagDto){
            Tag tag = buildTag(tagDto);
            Mockito.when(tagRepository.findById(tagDto.getId())).thenReturn(Optional.ofNullable(tag));
            Assertions.assertTrue(tagApiService.update(tagDto, tagDto.getId()));
            Mockito.verify(tagRepository, Mockito.timeout(1)).save(tag);
        }

        @Test
        void shouldCreateTagWhenTagIsValid(TagDtoRequest tagDto){
            Tag tag = buildTag(tagDto);
            Mockito.when(tagRepository.save(tag)).thenReturn(tag);
            Assertions.assertEquals(1L, tagApiService.create(tagDto));
            Mockito.verify(tagRepository, Mockito.timeout(1)).save(tag);
        }

        @Test
        void shouldReadAllTagWhenTagIsValid(TagDtoRequest tagDto){
            Tag tag = buildTag(tagDto);
            List<Tag> resultList = new ArrayList<>();
            resultList.add(tag);
            Mockito.when(tagRepository.findAll()).thenReturn(resultList);
            Assertions.assertEquals(resultList, tagApiService.readAll(Pageable.ofSize(10).withPage(0)));
            Mockito.verify(tagRepository, Mockito.timeout(1)).findAll();
        }

        private Tag buildTag(TagDtoRequest tagDto){
            return Tag.builder()
                    .name(tagDto.getName())
                    .build();
        }
        private TagDtoResponse buildTagDtoResponse(TagDtoRequest tagDto,
                                                   GiftCertificatesDtoRequest giftCertificatesDto){
            GiftCertificatesResponseDto giftCertificatesResponseDto = GiftCertificatesResponseDto.builder()
                    .id(giftCertificatesDto.getId())
                    .name(giftCertificatesDto.getName())
                    .price(giftCertificatesDto.getPrice())
                    .duration(giftCertificatesDto.getDuration())
                    .description(giftCertificatesDto.getDescription())
                    .build();
            List<GiftCertificatesResponseDto> certificatesResponseDtoList =
                    Stream.of(giftCertificatesResponseDto)
                            .collect(Collectors.toList());
            return new TagDtoResponse(tagDto.getId(), tagDto.getName(), certificatesResponseDtoList);
        }
    }

    @Nested
    @ExtendWith({MockitoExtension.class,
    InvalidParameterResolverTag.class})
    public class InvalidData{
        @InjectMocks
        private TagApiService tagApiService;

        @Test
        void shouldGetTagWhenTagIsInvalid(TagDtoRequest tagDto){
            Assertions.assertThrows(NullPointerException.class,
                    ()-> tagApiService.read(tagDto.getId()));
        }

        @Test
        void shouldCreateTagWhenTagIsInvalid(TagDtoRequest tagDto){
            Assertions.assertThrows(NullPointerException.class,
                    ()-> tagApiService.create(tagDto));
        }

        @Test
        void shouldUpdateTagWhenTasIsInvalid(TagDtoRequest tagDto){
            Assertions.assertThrows(NullPointerException.class,
                    ()->tagApiService.update(tagDto, 1L));
        }

        @Test
        void shouldDeleteTagWhenTagIsInvalid(TagDtoRequest tagDto){
            Assertions.assertThrows(NullPointerException.class,
                    ()->tagApiService.delete(tagDto.getId()));
        }
    }
}
