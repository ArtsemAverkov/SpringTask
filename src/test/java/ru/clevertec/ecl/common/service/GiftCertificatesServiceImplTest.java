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
import ru.clevertec.ecl.common.utill.RequestId;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.tag.Tag;
import ru.clevertec.ecl.repository.giftCertificates.GiftCertificatesRepository;
import ru.clevertec.ecl.service.giftCertificates.GiftCertificatesApiService;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            GiftCertificatesDtoRequest giftCertificatesDtoRequest = convertToGiftCertificatesDto(certificates);
            when(giftCertificatesRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.ofNullable(certificates));
            assertEquals(giftCertificatesDtoRequest, giftCertificatesApiService.read(RequestId.VALUE_1.getValue()));
           verify(giftCertificatesRepository, times(1))
                    .findById(certificates.getId());
        }

        @Test
        void shouldDeleteGiftCertificatesGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            when(giftCertificatesRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.ofNullable(certificates));
            assertTrue(giftCertificatesApiService.delete(RequestId.VALUE_1.getValue()));
            verify(giftCertificatesRepository, times(1)).deleteById(certificates.getId());
        }

        //@Disabled("This test is currently not working")
        @Test
        void shouldUpdateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates =  buildGiftCertificates(giftCertificatesDto);
            certificates.setCreate_date(null);
            when(giftCertificatesRepository.findById(RequestId.VALUE_1.getValue()))
                    .thenReturn(Optional.ofNullable(buildGiftCertificates(giftCertificatesDto)));
            assertTrue(giftCertificatesApiService.update(giftCertificatesDto, RequestId.VALUE_1.getValue()));
            verify(giftCertificatesRepository, times(1)).save(certificates);
        }

        @Test
        void shouldCreateGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            GiftCertificates certificates = buildGiftCertificates(giftCertificatesDto);
            certificates.setId(null);
            when(giftCertificatesRepository.save(certificates)).thenReturn(buildGiftCertificates(giftCertificatesDto));
            assertEquals(RequestId.VALUE_1.getValue(), giftCertificatesApiService.create(giftCertificatesDto));
            verify(giftCertificatesRepository, times(1)).save(certificates);
        }

        @Test
        void shouldReadAllGiftCertificatesWhenGiftCertificatesIsValid(GiftCertificatesDtoRequest giftCertificatesDto) {
            Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
            List<GiftCertificates> giftCertificatesList = new ArrayList<>();
            giftCertificatesList.add(buildGiftCertificates(giftCertificatesDto));
            when(giftCertificatesRepository.findAll(pageable)).thenReturn(new PageImpl<>(giftCertificatesList));
            List<GiftCertificatesDtoRequest> giftCertificatesDtoRequests = convertToDtoList(giftCertificatesList);
            assertEquals(giftCertificatesDtoRequests,
                    giftCertificatesApiService.readAll(pageable));
            verify(giftCertificatesRepository, times(1)).findAll(pageable);
        }

        private GiftCertificates buildGiftCertificates(GiftCertificatesDtoRequest giftCertificatesDto) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String isoDateTime = now.format(formatter);
            return GiftCertificates.builder()
                    .id(RequestId.VALUE_1.getValue())
                    .name(giftCertificatesDto.getName())
                    .price(giftCertificatesDto.getPrice())
                    .description(giftCertificatesDto.getDescription())
                    .duration(giftCertificatesDto.getDuration())
                    .tag(new Tag(giftCertificatesDto.getTagDto().getName()))
                    .create_date(isoDateTime)
                    .last_update_date(isoDateTime)
                    .build();
        }

        private GiftCertificatesDtoRequest convertToGiftCertificatesDto(GiftCertificates giftCertificates){
            return GiftCertificatesDtoRequest.builder()
                    .name(giftCertificates.getName())
                    .price(giftCertificates.getPrice())
                    .duration(giftCertificates.getDuration())
                    .description(giftCertificates.getDescription())
                    .tagDto(new TagDtoRequest(giftCertificates.getTag().getName()))
                    .build();
        }

        public List<GiftCertificatesDtoRequest> convertToDtoList(List<GiftCertificates> giftCertificatesList) {
            return giftCertificatesList.stream()
                    .map(this::convertToGiftCertificatesDto)
                    .collect(Collectors.toList());
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


