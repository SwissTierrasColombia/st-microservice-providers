package com.ai.st.microservice.providers.services.tracing;

public enum TracingKeyword {
    USER_ID("userId"), USER_NAME("username"), USER_EMAIL("userEmail"), AUTHORIZATION_HEADER("authorizationHeader"),
    BODY_REQUEST("bodyRequest"), PROVIDER_ID("providerId"), PROVIDER_NAME("providerName");

    private final String value;

    TracingKeyword(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
