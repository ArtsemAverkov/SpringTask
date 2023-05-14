package ru.clevertec.ecl.common.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.ecl.SpringTaskApplication;
import ru.clevertec.ecl.common.extension.tag.ValidParameterResolverTag;
import ru.clevertec.ecl.controller.tagController.TagController;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.service.tag.TagService;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SpringTaskApplication.class)
@WebMvcTest(TagController.class)
@RunWith(SpringRunner.class)
@ExtendWith({ValidParameterResolverTag.class, ValidParameterResolverGiftCertificates.class})
@DisplayName("Testing Tag Controller")
public class TagControllerTest {
    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTag(TagDtoRequest tagDto) throws Exception {
        when(tagService.create(any(TagDtoRequest.class))).thenReturn(RequestId.VALUE_1.getValue());
        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildJson(tagDto)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(RequestId.VALUE_1.getValue().toString()));
        verify(tagService).create(any(TagDtoRequest.class));
    }

    @Test
    public void testReadTag(TagDtoRequest tagDto, GiftCertificatesDtoRequest giftCertificatesDto) throws Exception {
        TagDtoResponse tagDtoResponse = getTagDtoResponse(tagDto, giftCertificatesDto);
        when(tagService.read(RequestId.VALUE_1.getValue())).thenReturn(tagDtoResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags/{id}", RequestId.VALUE_1.getValue()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(tagDto.getName()));
        verify(tagService).read(anyLong());
    }

    @Test
    public void testUpdateTag(TagDtoRequest tagDto) throws Exception {
        when(tagService.update(any(TagDtoRequest.class), anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/tags/{id}", RequestId.VALUE_1.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildJson(tagDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        verify(tagService).update(any(TagDtoRequest.class), anyLong());
    }

    @Test
    public void testDeleteTag() throws Exception {
        when(tagService.delete(RequestId.VALUE_1.getValue())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/{id}", RequestId.VALUE_1.getValue()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        verify(tagService).delete(anyLong());
    }

    @Test
    public void testReadAllTag() throws Exception {
        List<TagDtoResponse> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(tagService.readAll(pageable)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags"))
                .andExpect(MockMvcResultMatchers.content().json("[]"));

    }

    private TagDtoResponse getTagDtoResponse(TagDtoRequest tagDto, GiftCertificatesDtoRequest giftCertificatesDto) {
        List<GiftCertificatesResponseDto> giftCertificatesDtoList = List.of(
                new GiftCertificatesResponseDto(
                        RequestId.VALUE_1.getValue(),
                        giftCertificatesDto.getName(),
                        giftCertificatesDto.getDescription(),
                        giftCertificatesDto.getPrice(),
                        giftCertificatesDto.getDuration()));
        return new TagDtoResponse(
                RequestId.VALUE_1.getValue(), tagDto.getName(),giftCertificatesDtoList);
    }

    private String buildJson(TagDtoRequest tagDtoRequest){
        return "{\n" +
                "  \"name\": \""+tagDtoRequest.getName()+"\"\n" +
                "}";
    }
}
