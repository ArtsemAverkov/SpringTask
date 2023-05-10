package ru.clevertek.ecl.common.controller;

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
import ru.clevertec.ecl.controller.giftCertificates.GiftCertificatesController;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesService;
import ru.clevertek.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertek.ecl.common.extension.tag.ValidParameterResolverTag;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = SpringTaskApplication.class)
@WebMvcTest(GiftCertificatesController.class)
@RunWith(SpringRunner.class)
@ExtendWith(ValidParameterResolverGiftCertificates.class)
public class GiftCertificatesControllerTest {

    @MockBean
    private GiftCertificatesService giftCertificatesService;

    @Autowired
    private  MockMvc mockMvc;

    @Test
    public void testCreate(GiftCertificatesDto giftCertificatesDto) throws Exception {
        Mockito.when(giftCertificatesService.create(any(GiftCertificatesDto.class))).thenReturn(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"name\": \""+giftCertificatesDto.getName()+"\"," +
                                " \"description\": \""+giftCertificatesDto.getDescription()+"\", " +
                                "\"price\": "+giftCertificatesDto.getPrice()+" }"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("1"));
        Mockito.verify(giftCertificatesService).create(any(GiftCertificatesDto.class));
    }


    @Test
    public void testRead(GiftCertificatesDto giftCertificatesDto) throws Exception {
        Mockito.when(giftCertificatesService.read(anyLong())).thenReturn(giftCertificatesDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/certificates/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(giftCertificatesDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(giftCertificatesDto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(giftCertificatesDto.getPrice()));
        Mockito.verify(giftCertificatesService).read(anyLong());
    }

    @Test
    public void testUpdate(GiftCertificatesDto giftCertificatesDto) throws Exception {
        Mockito.when(giftCertificatesService.update(any(GiftCertificatesDto.class), anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/certificates/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"name\": \""+giftCertificatesDto.getName()+"\"," +
                                " \"description\": \""+giftCertificatesDto.getDescription()+"\", " +
                                "\"price\": "+giftCertificatesDto.getPrice()+" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        Mockito.verify(giftCertificatesService).update(any(GiftCertificatesDto.class), anyLong());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/certificates/1"))
                .andExpect(status().isOk());
        Mockito.verify(giftCertificatesService).delete(anyLong());
    }
}



