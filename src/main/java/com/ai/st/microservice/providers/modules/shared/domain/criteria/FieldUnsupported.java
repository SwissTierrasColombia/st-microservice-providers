package com.ai.st.microservice.providers.modules.shared.domain.criteria;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class FieldUnsupported extends DomainError {

    public FieldUnsupported() {
        super("field_unsupported", "El campo a filtrar no esta soportado.");
    }

}
