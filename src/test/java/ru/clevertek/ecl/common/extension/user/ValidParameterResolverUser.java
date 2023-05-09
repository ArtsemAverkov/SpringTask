package ru.clevertek.ecl.common.extension.user;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.ecl.dto.userDto.UserDto;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ValidParameterResolverUser implements ParameterResolver {
    private List<UserDto> userDtoList = Arrays.asList(
            new UserDto(
                    "name",
                    "email",
                    "password"
            ),
            new UserDto(
                    "names",
                    "emails",
                    "passwords"
            )
    );
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==UserDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return userDtoList.get(new Random().nextInt(userDtoList.size()));
    }
}
