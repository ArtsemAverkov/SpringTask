package ru.clevertek.ecl.common.extension.tag;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.ecl.dto.tag.TagDto;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InvalidParameterResolverTag implements ParameterResolver{
    public static List<TagDto> validListDto = Arrays.asList(
            new TagDto(
                    null,
                    null
            )
    );
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()== TagDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return validListDto.get(new Random().nextInt(validListDto.size()));
    }
}