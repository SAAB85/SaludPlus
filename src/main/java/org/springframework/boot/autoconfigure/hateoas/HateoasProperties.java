package org.springframework.boot.autoconfigure.hateoas;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.hateoas")
public class HateoasProperties {

    private boolean useHalAsDefaultJsonMediaType = true;

    public boolean isUseHalAsDefaultJsonMediaType() {
        return useHalAsDefaultJsonMediaType;
    }

    public void setUseHalAsDefaultJsonMediaType(boolean useHalAsDefaultJsonMediaType) {
        this.useHalAsDefaultJsonMediaType = useHalAsDefaultJsonMediaType;
    }

    public boolean getUseHalAsDefaultJsonMediaType() {
        return useHalAsDefaultJsonMediaType;
    }
}
