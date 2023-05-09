package ru.clevertek.ecl.common.extension.order;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesResponseDto;
import ru.clevertec.ecl.dto.order.OrderDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ValidParameterResolverOrder implements ParameterResolver {
    public static List<GiftCertificatesResponseDto> validList = Arrays.asList(
            new GiftCertificatesResponseDto(
                    1L,
                    "Certificates",
                    "50%",
                    23.3,
                    30L
                    ),
            new GiftCertificatesResponseDto(
                    1L,
                    "Certificates",
                    "25%",
                    10.5,
                    30L));

    private List<OrderDto> orderDtoList = Arrays.asList(
            new OrderDto(
                 1L,
                 LocalDateTime.now(),
                    validList
            )
    );

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==OrderDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return orderDtoList.get(new Random().nextInt(orderDtoList.size()));
    }
}
