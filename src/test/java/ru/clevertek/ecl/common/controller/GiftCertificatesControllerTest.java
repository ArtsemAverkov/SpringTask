package ru.clevertek.ecl.common.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.ecl.dto.GiftCertificatesDto;
import ru.clevertec.ecl.entity.GiftCertificates;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesApiService;
import ru.clevertec.ecl.util.appConfig.AppConfig;
import ru.clevertek.ecl.common.extension.ValidParameterResolverGiftCertificates;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ExtendWith({MockMvcParameterResolver.class,
        MockitoExtension.class,
      GiftCertificatesApiControllerParameterResolver.class,
        ValidParameterResolverGiftCertificates.class})
public class GiftCertificatesControllerTest {

    @Mock
    private GiftCertificatesApiService giftCertificatesService;

    private final MockMvc mockMvc;

    public GiftCertificatesControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testCreate(GiftCertificatesDto giftCertificatesDto) throws Exception {
        GiftCertificatesDto giftCertificatesDto1 = new GiftCertificatesDto();
        giftCertificatesDto1.setName("Test Certificate");
        giftCertificatesDto1.setDescription("Test Description");
        giftCertificatesDto1.setPrice(10.0);
        when(giftCertificatesService.create(giftCertificatesDto)).thenReturn(1L);
        mockMvc.perform(MockMvcRequestBuilders.post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"name\": \"Test Certificate\", \"description\": \"Test Description\", \"price\": 10.0 }"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("1"));
        verify(giftCertificatesService).create(giftCertificatesDto);
    }


    @Test
    public void testRead() throws Exception {
        GiftCertificates giftCertificates = new GiftCertificates();
        giftCertificates.setName("Test Certificate");
        giftCertificates.setDescription("Test Description");
        giftCertificates.setPrice(10.0);
        when(giftCertificatesService.read(anyLong())).thenReturn(giftCertificates);
        mockMvc.perform(MockMvcRequestBuilders.get("/certificates/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Certificate"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0));
        verify(giftCertificatesService).read(anyLong());
    }

    @Test
    public void testUpdate() throws Exception {
        GiftCertificatesDto giftCertificatesDto = new GiftCertificatesDto();
        giftCertificatesDto.setName("Test Certificate");
        giftCertificatesDto.setDescription("Test Description");
        giftCertificatesDto.setPrice(10.0);
        when(giftCertificatesService.update(any(GiftCertificatesDto.class), anyLong())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.patch("/certificates/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"name\": \"Test Certificate\", \"description\": \"Test Description\", \"price\": 10.0 }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
        verify(giftCertificatesService).update(any(GiftCertificatesDto.class), anyLong());
    }

    @Test
    public void testDelete(GiftCertificatesDto giftCertificatesDto) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/certificates/1"))
                .andExpect(status().isOk());
        verify(giftCertificatesService).delete(anyLong());
    }


        private GiftCertificates buildGiftCertificates(GiftCertificatesDto giftCertificatesDto){
        LocalDateTime now = LocalDateTime.now();
        String isoDateTime = now.format(DateTimeFormatter.ISO_DATE_TIME);
        return GiftCertificates.builder()
                .id(giftCertificatesDto.getId())
                .name(giftCertificatesDto.getName())
                .price(giftCertificatesDto.getPrice())
                .description(giftCertificatesDto.getDescription())
                .duration(giftCertificatesDto.getDuration())
                .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                .create_date(isoDateTime)
                .last_update_date(isoDateTime)
                .build();
    }
}



