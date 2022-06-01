package com.ai.st.microservice.providers.modules.shared.application;

import com.ai.st.microservice.providers.modules.shared.domain.Manager;

public final class ManagerResponse implements Response {

    private final Long code;
    private final String name;
    private final String alias;

    public ManagerResponse(Long code, String name, String alias) {
        this.code = code;
        this.name = name;
        this.alias = alias;
    }

    public static ManagerResponse fromAggregate(Manager manager) {
        return new ManagerResponse(manager.code().value(), manager.name().value(), manager.alias().value());
    }

    public Long code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String alias() {
        return alias;
    }

}
