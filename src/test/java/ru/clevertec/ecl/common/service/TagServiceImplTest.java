package ru.clevertec.ecl.common.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.common.extension.tag.InvalidParameterResolverTag;
import ru.clevertec.ecl.common.utill.serviceMapper.TagServiceImplTestMapper;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.tag.TagRepository;
import ru.clevertec.ecl.service.tag.TagApiService;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.extension.tag.ValidParameterResolverTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@DisplayName("Tag Service Test")
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
        void shouldGetTagWhenTagValid(TagDtoRequest tagDto){
            Tag tag = TagServiceImplTestMapper.buildTag(tagDto);
            TagDtoResponse tagDtoResponse = TagServiceImplTestMapper.convertTagToTagDtoResponse(tag);
            when(tagRepository.findById(RequestId.VALUE_1.getValue())).thenReturn(Optional.of(tag));
            assertEquals(tagDtoResponse, tagApiService.read(RequestId.VALUE_1.getValue()));
            verify(tagRepository, times(1)).findById(RequestId.VALUE_1.getValue());
        }

        @Test
        void shouldDeleteTagWhenTagIsValid(TagDtoRequest tagDto){
            Tag tag = TagServiceImplTestMapper.buildTag(tagDto);
            when(tagRepository.findById(RequestId.VALUE_1.getValue())).thenReturn(Optional.ofNullable(tag));
            assertTrue(tagApiService.delete(RequestId.VALUE_1.getValue()));
            verify(tagRepository,times(1)).deleteById(RequestId.VALUE_1.getValue());
        }


        @Test
        void shouldUpdateTagWhenTagIsValid(TagDtoRequest tagDto){
            Tag tag = TagServiceImplTestMapper.buildTag(tagDto);
            when(tagRepository.findById(RequestId.VALUE_1.getValue())).thenReturn(Optional.ofNullable(tag));
            assertTrue(tagApiService.update(tagDto, RequestId.VALUE_1.getValue()));
            verify(tagRepository, times(1)).save(tag);
        }

        @Test
        void shouldCreateTagWhenTagIsValid(TagDtoRequest tagDto){
            Tag tag = TagServiceImplTestMapper.buildTagForMethodCreate(tagDto);
            when(tagRepository.save(tag)).thenReturn(tag);
            assertEquals(0, tagApiService.create(tagDto));
            verify(tagRepository, times(1)).save(tag);
        }

        @Test
        void shouldReadAllTagWhenTagIsValid(TagDtoRequest tagDto){
            Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
            Tag tag = TagServiceImplTestMapper.buildTag(tagDto);
            List<Tag> resultList = new ArrayList<>();
            resultList.add(tag);
            List<TagDtoResponse> tagDtoResponseList = TagServiceImplTestMapper.getTagDtoResponseList(resultList);
            when(tagRepository.findAll(pageable)).thenReturn(new PageImpl<>(resultList));
            assertEquals(tagDtoResponseList, tagApiService.readAll(pageable));
            verify(tagRepository, times(1)).findAll(pageable);
        }
    }

    @Nested
    @ExtendWith({MockitoExtension.class,
    InvalidParameterResolverTag.class})
    public class InvalidData{
        @InjectMocks
        private TagApiService tagApiService;

        @Mock
        private TagRepository tagRepository;


        @Test
        void shouldGetTagWhenTagIsInvalid(){
            when(tagRepository.findById(RequestId.VALUE_1.getValue())).thenThrow(IllegalArgumentException.class);
            assertThrows(IllegalArgumentException.class,
                    () -> tagApiService.read(RequestId.VALUE_1.getValue()));
        }

        @Test
        void shouldCreateTagWhenTagIsInvalid(TagDtoRequest tagDto){
            assertThrows(NullPointerException.class,
                    ()-> tagApiService.create(tagDto));
        }

        @Test
        void shouldUpdateTagWhenTasIsInvalid(TagDtoRequest tagDto){
            assertThrows(IllegalArgumentException.class,
                    ()->tagApiService.update(tagDto, RequestId.VALUE_1.getValue()));
        }

        @Test
        void shouldDeleteTagWhenTagIsInvalid(){
            assertThrows(IllegalArgumentException.class,
                    ()->tagApiService.delete(RequestId.VALUE_1.getValue()));
        }
    }
}
