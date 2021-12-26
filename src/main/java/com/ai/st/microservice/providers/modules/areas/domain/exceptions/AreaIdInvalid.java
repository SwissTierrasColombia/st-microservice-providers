package com.ai.st.microservice.providers.modules.areas.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class AreaIdInvalid extends DomainError {

    public AreaIdInvalid(Long areaId) {
        super("area_id_invalid", String.format("El código del área '%d' es inválido", areaId));
    }
}
