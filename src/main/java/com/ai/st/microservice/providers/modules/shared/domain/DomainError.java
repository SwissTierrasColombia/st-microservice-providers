package com.ai.st.microservice.providers.modules.shared.domain;

public class DomainError extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    public DomainError(String errorCode, String errorMessage) {
        super(errorMessage);

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String errorCode() {
        return errorCode;
    }

    public String errorMessage() {
        return errorMessage;
    }

}
