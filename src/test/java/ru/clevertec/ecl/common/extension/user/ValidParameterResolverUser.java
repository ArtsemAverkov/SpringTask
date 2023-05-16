package ru.clevertec.ecl.common.extension.user;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.ecl.dto.user.UserDtoRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ValidParameterResolverUser implements ParameterResolver {
    private List<UserDtoRequest> userDtoList = Arrays.asList(
            new UserDtoRequest(
                    "name",
                    "email@mail.ru",
                    "password"
            ),
            new UserDtoRequest(
                    "name",
                    "email@mail.com",
                    "passwords"
            )
    );
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()== UserDtoRequest.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return userDtoList.get(new Random().nextInt(userDtoList.size()));
    }
}
