package ru.clevertek.ecl.common.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesApiService;
import ru.clevertek.ecl.common.extension.giftCertificates.InvalidParameterResolverGiftCertificates;
import ru.clevertek.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@DisplayName("Testing GiftCertificates Service for Valid and Invalid")
public class GiftCertificatesServiceImplTest {


    @Nested
    @ExtendWith({MockitoExtension.class, ValidParameterResolverGiftCertificates.class})
    public class ValidData {

        @InjectMocks
        private GiftCertificatesApiService giftCertificatesApiService;

        @Mock
        private GiftCertificatesRepository giftCertificatesRepository;

        @Test
        void shouldGetGiftCertificatesWhenGiftCertificatesValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            Mockito.when(giftCertificatesRepository.findById(giftCertificatesDto.getId()))
                    .thenReturn(Optional.ofNullable(certificates));
            Assertions.assertEquals(certificates, giftCertificatesApiService.read(giftCertificatesDto.getId()));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1))
                    .findById(certificates.getId());
        }

        @Test
        void shouldDeleteGiftCertificatesGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            Mockito.when(giftCertificatesRepository.findById(giftCertificatesDto.getId()))
                    .thenReturn(Optional.ofNullable(certificates));
            Assertions.assertTrue(giftCertificatesApiService.delete(giftCertificatesDto.getId()));
            Mockito.verify(giftCertificatesRepository, Mockito.timeout(1)).deleteById(certificates.getId());
        }

        @Disabled("This test is currently not working")
        @Test
        void shouldUpdateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            Mockito.when(giftCertificatesRepository.findById(giftCertificatesDto.getId()))
                    .thenReturn(Optional.ofNullable(certificates));
            Assertions.assertTrue(giftCertificatesApiService.update(giftCertificatesDto, 1L));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1)).save(certificates);
        }


        @Disabled("This test is currently not working")
        @Test
        void shouldCreateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            Mockito.when(giftCertificatesRepository.save(certificates)).thenReturn(certificates);
            Assertions.assertEquals(1L, giftCertificatesApiService.create(giftCertificatesDto));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1)).save(certificates);
        }

        @Test
        void shouldReadAllGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            List<GiftCertificates> giftCertificatesList = new ArrayList<>();
            giftCertificatesList.add(buildGiftCertificates(giftCertificatesDto));
            Mockito.when(giftCertificatesRepository.findAll()).thenReturn(giftCertificatesList);
            Assertions.assertEquals(giftCertificatesList,
                    giftCertificatesApiService.readAll(Pageable.ofSize(10).withPage(0)));
            Mockito.verify(giftCertificatesRepository, Mockito.times(1)).findAll();
        }


        private GiftCertificates buildGiftCertificates(GiftCertificatesDtoRequest giftCertificatesDto) {
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

        @Nested
        @ExtendWith({MockitoExtension.class,
                InvalidParameterResolverGiftCertificates.class})
        public class InvalidData {
            @InjectMocks
            private GiftCertificatesApiService giftCertificatesApiService;

            @Test
            void shouldUpdateProductWheProductIsInvalid(GiftCertificatesDtoRequest giftCertificatesDto) {
                Assertions.assertThrows(NullPointerException.class,
                        () -> giftCertificatesApiService.update(giftCertificatesDto, null));
            }

            @Test
            void shouldCreateProductWheProductIsInvalid() {
                Assertions.assertThrows(NullPointerException.class,
                        () -> giftCertificatesApiService.create(null));

            }

            @Test
            void shouldDeleteProductWheProductIsInvalid() {
                Assertions.assertThrows(NullPointerException.class,
                        () -> giftCertificatesApiService.delete(null));

            }

            @Test
            void shouldGetProductWheProductIsInvalid(GiftCertificatesDtoRequest giftCertificatesDto) {
                Assertions.assertThrows(NullPointerException.class,
                        () -> giftCertificatesApiService.read(giftCertificatesDto.getId()));
            }
        }
    }
}

