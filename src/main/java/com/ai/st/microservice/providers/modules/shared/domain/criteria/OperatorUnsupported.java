package com.ai.st.microservice.providers.modules.shared.domain.criteria;

import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

public final class OperatorUnsupported extends DomainError {

    public OperatorUnsupported() {
        super("operator_unsupported", "Operador no soportado.");
    }
}
