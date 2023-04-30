package ru.clevertek.ecl.common.extension;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDto;
import ru.clevertec.ecl.dto.tag.TagDto;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InvalidParameterResolverGiftCertificates implements ParameterResolver {
    public static List<GiftCertificatesDto> validList = Arrays.asList(
            new GiftCertificatesDto(
                    null,
                    null,
                    null,
                    null,
                    null,
                    new TagDto(
                            null,
                            null
                    )
            ));

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==GiftCertificatesDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return validList.get(new Random().nextInt(validList.size()));
    }
}

