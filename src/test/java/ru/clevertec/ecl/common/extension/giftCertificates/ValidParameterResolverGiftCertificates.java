package ru.clevertec.ecl.common.extension.giftCertificates;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.ecl.dto.giftCertificates.GiftCertificatesDtoRequest;
import ru.clevertec.ecl.dto.tag.TagDtoRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class ValidParameterResolverGiftCertificates implements ParameterResolver {
    public static List<GiftCertificatesDtoRequest> validList = Arrays.asList(
            new GiftCertificatesDtoRequest(
                    1L,
                    "Certificates",
                    "50%",
                    23.3,
                    30L,
                    new TagDtoRequest(
                            1L,
                            ""

                    )
            ),
            new GiftCertificatesDtoRequest(
                    1L,
                    "Certificates",
                    "25%",
                    10.5,
                    30L,
                    new TagDtoRequest(
                            1L,
                            ""
                    )));



    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()== GiftCertificatesDtoRequest.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return validList.get(new Random().nextInt(validList.size()));
    }
}
