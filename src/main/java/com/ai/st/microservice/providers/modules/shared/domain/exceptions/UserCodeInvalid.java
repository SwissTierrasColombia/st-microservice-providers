package com.ai.st.microservice.providers.modules.shared.domain.exceptions;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class UserCodeInvalid extends DomainError {

    public UserCodeInvalid(Long userId) {
        super("user_code_invalid", String.format("El código del usuario '%d' no es válido.", userId));
    }

}
