package org.springframework.hateoas.mediatype.hal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class Jackson2HalModule extends SimpleModule {

    public Jackson2HalModule() {
        super("Jackson2HalModule");
    }

    public static boolean isAlreadyRegisteredIn(ObjectMapper objectMapper) {
        return true;
    }
}
