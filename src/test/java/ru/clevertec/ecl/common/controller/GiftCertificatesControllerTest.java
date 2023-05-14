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
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.extension.tag.ValidParameterResolverTag;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.controller.giftCertificates.GiftCertificatesController;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesService;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SpringTaskApplication.class)
@WebMvcTest(GiftCertificatesController.class)
@RunWith(SpringRunner.class)
@ExtendWith({ValidParameterResolverGiftCertificates.class, ValidParameterResolverTag.class})
@DisplayName("Testing GiftCertificates Controller")
public class GiftCertificatesControllerTest {

    @MockBean
    private GiftCertificatesService giftCertificatesService;

    @Autowired
    private  MockMvc mockMvc;

    @Test
    public void testCreate(GiftCertificatesDtoRequest giftCertificatesDto,
                           TagDtoRequest tagDtoRequest) throws Exception {
        when(giftCertificatesService.create(any(GiftCertificatesDtoRequest.class))).thenReturn(RequestId.VALUE_1.getValue());
        mockMvc.perform(MockMvcRequestBuilders.post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(buildJson(giftCertificatesDto,tagDtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(RequestId.VALUE_1.getValue().toString()));
        verify(giftCertificatesService).create(any(GiftCertificatesDtoRequest.class));
    }


    @Test
    public void testRead(GiftCertificatesDtoRequest giftCertificatesDto) throws Exception {
        when(giftCertificatesService.read(anyLong())).thenReturn(giftCertificatesDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/certificates/{id}", RequestId.VALUE_1.getValue()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(giftCertificatesDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(giftCertificatesDto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(giftCertificatesDto.getPrice()));
        verify(giftCertificatesService).read(anyLong());
    }

    @Test
    public void testUpdate(GiftCertificatesDtoRequest giftCertificatesDto,
                           TagDtoRequest tagDtoRequest) throws Exception {
        when(giftCertificatesService.update(any(GiftCertificatesDtoRequest.class), anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/certificates/{id}", RequestId.VALUE_1.getValue())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(buildJson(giftCertificatesDto,tagDtoRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        verify(giftCertificatesService).update(any(GiftCertificatesDtoRequest.class), anyLong());
    }

    @Test
    public void testDelete() throws Exception {
        when(giftCertificatesService.delete(RequestId.VALUE_1.getValue()))
                .thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/certificates/{id}", RequestId.VALUE_1.getValue()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        verify(giftCertificatesService).delete(anyLong());
    }

    @Test
    public void testReadAll(GiftCertificatesDtoRequest giftCertificatesDto,
                            TagDtoRequest tagDtoRequest) throws Exception {
        String jsonResponse = buildJson(giftCertificatesDto, tagDtoRequest);
        List<GiftCertificatesDtoRequest> list = new ArrayList<>();
        list.add(giftCertificatesDto);
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(giftCertificatesService.readAll(pageable)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/certificates"))
                .andExpect(MockMvcResultMatchers.content().json("[" + jsonResponse + "]"));
    }

    private String buildJson(GiftCertificatesDtoRequest giftCertificatesDto,
                             TagDtoRequest tagDtoRequest){
        return "{\n" +
                "  \"name\": \""+giftCertificatesDto.getName()+"\",\n" +
                "  \"description\": \""+giftCertificatesDto.getDescription()+"\",\n" +
                "  \"price\": "+giftCertificatesDto.getPrice()+",\n" +
                "  \"duration\": "+giftCertificatesDto.getDuration()+",\n" +
                "  \"tagDto\": {\n" +
                "    \"name\": \""+tagDtoRequest.getName()+"\"\n" +
                "  }\n" +
                "}";
    }
}



