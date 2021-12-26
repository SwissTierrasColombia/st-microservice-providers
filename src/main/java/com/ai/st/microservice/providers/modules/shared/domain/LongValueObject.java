package com.ai.st.microservice.providers.modules.shared.domain;

import java.util.Objects;

public abstract class LongValueObject {

    private final Long value;

    public LongValueObject(Long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LongValueObject)) {
            return false;
        }
        LongValueObject that = (LongValueObject) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
