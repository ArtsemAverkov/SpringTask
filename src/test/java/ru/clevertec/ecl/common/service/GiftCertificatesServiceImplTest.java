package ru.clevertec.ecl.common.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import ru.clevertec.ecl.common.extension.giftCertificates.InvalidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.extension.giftCertificates.ValidParameterResolverGiftCertificates;
import ru.clevertec.ecl.common.utill.serviceMapper.GiftCertificatesServiceImplTestMapper;
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;



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
            GiftCertificates certificates =
                    GiftCertificatesServiceImplTestMapper.buildGiftCertificates(giftCertificatesDto);
            GiftCertificatesDtoRequest giftCertificatesDtoRequest =
                    GiftCertificatesServiceImplTestMapper.convertToGiftCertificatesDto(certificates);
            when(giftCertificatesRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.of(certificates));
            assertEquals(giftCertificatesDtoRequest, giftCertificatesApiService.read(RequestId.VALUE_1.getValue()));
           verify(giftCertificatesRepository, times(1))
                    .findById(certificates.getId());
        }

        @Test
        void shouldDeleteGiftCertificatesGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates =
                    GiftCertificatesServiceImplTestMapper.buildGiftCertificates(giftCertificatesDto);
            when(giftCertificatesRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.ofNullable(certificates));
            assertTrue(giftCertificatesApiService.delete(RequestId.VALUE_1.getValue()));
            verify(giftCertificatesRepository, times(1)).deleteById(certificates.getId());
        }

        @Test
        void shouldUpdateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates =
                    GiftCertificatesServiceImplTestMapper.buildGiftCertificates(giftCertificatesDto);
            certificates.setCreate_date(null);
            when(giftCertificatesRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.of(certificates));
            assertTrue(giftCertificatesApiService.update(giftCertificatesDto, RequestId.VALUE_1.getValue()));
            verify(giftCertificatesRepository, times(1)).save(certificates);
        }

        @Test
        void shouldCreateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates =
                    GiftCertificatesServiceImplTestMapper.buildGiftCertificates(giftCertificatesDto);
            certificates.setId(null);
            GiftCertificates certificatesResponse =
                    GiftCertificatesServiceImplTestMapper.buildGiftCertificates(giftCertificatesDto);
            when(giftCertificatesRepository.save(certificates)).thenReturn(certificatesResponse);
            assertEquals(RequestId.VALUE_1.getValue(), giftCertificatesApiService.create(giftCertificatesDto));
            verify(giftCertificatesRepository, times(1)).save(certificates);
        }

        @Test
        void shouldReadAllGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            List<GiftCertificates> giftCertificatesList = new ArrayList<>();
            GiftCertificates certificates =
                    GiftCertificatesServiceImplTestMapper.buildGiftCertificates(giftCertificatesDto);
            giftCertificatesList.add(certificates);
            List<GiftCertificatesDtoRequest> giftCertificatesDtoRequests =
                    GiftCertificatesServiceImplTestMapper.convertToDtoList(giftCertificatesList);
            Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

            when(giftCertificatesRepository.findAll(pageable)).thenReturn(new PageImpl<>(giftCertificatesList));
            assertEquals(giftCertificatesDtoRequests,
                    giftCertificatesApiService.readAll(pageable));
            verify(giftCertificatesRepository, times(1)).findAll(pageable);
        }

        @Nested
        @ExtendWith({MockitoExtension.class,
                InvalidParameterResolverGiftCertificates.class})
        public class InvalidData {
            @InjectMocks
            private GiftCertificatesApiService giftCertificatesApiService;

            @Mock
            private GiftCertificatesRepository giftCertificatesRepository;
            @Test
            void shouldUpdateGiftCertificatesWheGiftCertificatesIsInvalid() {
               assertThrows(IllegalArgumentException.class,
                        () -> giftCertificatesApiService.update(null, 1L));
            }

            @Test
            void shouldCreatePGiftCertificatesWheGiftCertificatesIsInvalid() {
                assertThrows(NullPointerException.class,
                        () -> giftCertificatesApiService.create(null));

            }

            @Test
            void shouldDeleteProductWheProductIsInvalid() {
                assertThrows(NullPointerException.class,
                        () -> giftCertificatesApiService.delete(null));
            }

            @Test
            void shouldGetGiftCertificatesWheGiftCertificatesIsInvalid() {
               when(giftCertificatesRepository.findById(1L)).thenThrow(IllegalArgumentException.class);
                assertThrows(IllegalArgumentException.class, () -> giftCertificatesApiService.read(1L));
            }
            }
        }
    }


