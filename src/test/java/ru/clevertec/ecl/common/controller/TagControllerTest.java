package ru.clevertec.ecl.common.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.ecl.SpringTaskApplication;
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.extension.tag.ValidParameterResolverTag;
import ru.clevertec.ecl.controller.tagController.TagController;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoResponse;
import ru.clevertec.ecl.service.tag.TagService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SpringTaskApplication.class)
@WebMvcTest(TagController.class)
@RunWith(SpringRunner.class)
@ExtendWith({ValidParameterResolverTag.class, ValidParameterResolverGiftCertificates.class})
public class TagControllerTest {
    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTag(TagDtoRequest tagDto) throws Exception {
        Mockito.when(tagService.create(any(TagDtoRequest.class))).thenReturn(tagDto.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/tag")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                "  \"id\":"+tagDto.getId()+",\n" +
                        "  \"name\": \""+tagDto.getName()+"\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(tagDto.getId().toString()));
        Mockito.verify(tagService).create(any(TagDtoRequest.class));
    }

    @Test
    public void testReadTag(TagDtoRequest tagDto, GiftCertificatesDtoRequest giftCertificatesDto) throws Exception {
        List<GiftCertificatesResponseDto> giftCertificatesDtoList = Arrays.asList(
                new GiftCertificatesResponseDto(
                        giftCertificatesDto.getId(),
                        giftCertificatesDto.getName(),
                        giftCertificatesDto.getDescription(),
                        giftCertificatesDto.getPrice(),
                        giftCertificatesDto.getDuration()));
        TagDtoResponse tagDtoResponse = new TagDtoResponse(tagDto.getId(), tagDto.getName(),giftCertificatesDtoList );
        Mockito.when(tagService.read(tagDto.getId())).thenReturn(tagDtoResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/tag/"+tagDto.getId()+""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(tagDto.getName()));
        Mockito.verify(tagService).read(anyLong());
    }
    @Test
    public void testUpdateTag(TagDtoRequest tagDto) throws Exception {
        Mockito.when(tagService.update(any(TagDtoRequest.class), anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/tag/"+tagDto.getId()+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\":"+tagDto.getId()+",\n" +
                        "  \"name\": \""+tagDto.getName()+"\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        Mockito.verify(tagService).update(any(TagDtoRequest.class), anyLong());
    }

    @Test
    public void testDeleteTag(TagDtoRequest tagDto) throws Exception {
        Mockito.when(tagService.delete(tagDto.getId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/tag/"+tagDto.getId()+""))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        Mockito.verify(tagService).delete(anyLong());
    }
}
