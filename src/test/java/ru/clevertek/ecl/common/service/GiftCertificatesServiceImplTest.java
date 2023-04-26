package ru.clevertek.ecl.common.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.clevertec.ecl.dto.GiftCertificatesDto;
import ru.clevertec.ecl.entity.GiftCertificates;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesApiService;
import ru.clevertec.ecl.util.appConfig.AppConfig;
import ru.clevertek.ecl.common.extension.InvalidParameterResolverGiftCertificates;
import ru.clevertek.ecl.common.extension.ValidParameterResolverGiftCertificates;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@DisplayName("Testing GiftCertificates Service for Valid and Invalid")
public class GiftCertificatesServiceImplTest {


    @Nested
    @ExtendWith({MockitoExtension.class,
            GiftCertificatesApiServiceParameterResolver.class,
            ValidParameterResolverGiftCertificates.class})
    public class ValidData {

        @InjectMocks
        private final GiftCertificatesApiService giftCertificatesApiService;

        @Mock
        private GiftCertificatesRepository giftCertificatesRepository;


        public ValidData(GiftCertificatesApiService giftCertificatesApiService) {
            this.giftCertificatesApiService = giftCertificatesApiService;
        }

        @Test
        void shouldGetGiftCertificatesWhenGiftCertificatesValid(GiftCertificatesDto giftCertificatesDto) throws Exception {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            when(giftCertificatesRepository.read(giftCertificatesDto.getId())).thenReturn(certificates);
            Assertions.assertEquals(certificates, giftCertificatesApiService.read(giftCertificatesDto.getId()));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1)).read(certificates.getId());
        }

        @Test
        void shouldDeleteGiftCertificatesGiftCertificatesIsValid(GiftCertificatesDto giftCertificatesDto) throws Exception {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            when(giftCertificatesRepository.delete(giftCertificatesDto.getId())).thenReturn(true);
            Assertions.assertTrue(giftCertificatesApiService.delete(giftCertificatesDto.getId()));
            Mockito.verify(giftCertificatesRepository, Mockito.timeout(1)).delete(certificates.getId());
        }

        @Test
        void shouldUpdateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDto giftCertificatesDto) throws Exception {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            when(giftCertificatesRepository.update(certificates, 1L)).thenReturn(true);
            Assertions.assertTrue(giftCertificatesApiService.update(giftCertificatesDto, 1L));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1)).update(certificates, 1L);
        }

        @Test
        void shouldCreateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDto giftCertificatesDto){
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            when(giftCertificatesRepository.create(certificates)).thenReturn(1L);
            Assertions.assertEquals(1L, giftCertificatesApiService.create(giftCertificatesDto));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1)).create(certificates);
        }

        @Test
        void shouldReadAllGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDto giftCertificatesDto) {
            String param = null;
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            GiftCertificates[] certificatesArray = {certificates};
            List<Object[]> certificatesList = new ArrayList<>();
            certificatesList.add(Arrays.asList(certificatesArray).toArray());
            when(giftCertificatesRepository.readAll(param,param,param)).thenReturn(certificatesList);
            Assertions.assertEquals(certificatesList, giftCertificatesApiService.readAll(param,param,param));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1)).readAll(param,param,param);
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
    @Nested
    @ExtendWith({MockitoExtension.class,
            GiftCertificatesApiServiceParameterResolver.class,
            InvalidParameterResolverGiftCertificates.class})
    public class InvalidData{
        @InjectMocks
        private final GiftCertificatesApiService giftCertificatesApiService;

        public InvalidData(GiftCertificatesApiService giftCertificatesApiService) {
            this.giftCertificatesApiService = giftCertificatesApiService;
        }

        @Test
        void shouldUpdateProductWheProductIsInvalid (GiftCertificatesDto giftCertificatesDto){
            Assertions.assertThrows(NullPointerException.class,
                    () -> giftCertificatesApiService.update(giftCertificatesDto,null));
        }

        @Test
        void shouldCreateProductWheProductIsInvalid (){
            Assertions.assertThrows(NullPointerException.class,
                    () -> giftCertificatesApiService.create(null));

        }
        @Test
        void shouldDeleteProductWheProductIsInvalid (){
            Assertions.assertThrows(NullPointerException.class,
                    () -> giftCertificatesApiService.delete(null));

        }

        @Test
        void shouldGetProductWheProductIsInvalid (GiftCertificatesDto giftCertificatesDto){
            Assertions.assertThrows(NullPointerException.class,
                    () -> giftCertificatesApiService.read(giftCertificatesDto.getId()));
        }

        @Test
        void shouldReadAllProductWhenProductIsInvalid() {
            Assertions.assertThrows(NullPointerException.class,
                    () -> giftCertificatesApiService.readAll(null,null,null));
        }
    }
}
