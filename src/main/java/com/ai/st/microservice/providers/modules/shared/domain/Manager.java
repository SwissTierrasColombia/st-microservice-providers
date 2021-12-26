package com.ai.st.microservice.providers.modules.shared.domain;

public final class Manager {

    private final ManagerCode code;
    private final ManagerName name;
    private final ManagerAlias alias;

    public Manager(ManagerCode code, ManagerName name, ManagerAlias alias) {
        this.code = code;
        this.name = name;
        this.alias = alias;
    }

    public static Manager fromPrimitives(Long code, String name, String alias) {
        return new Manager(
                ManagerCode.fromValue(code),
                ManagerName.fromValue(name),
                ManagerAlias.fromValue(alias)
        );
    }

    public ManagerCode code() {
        return code;
    }

    public ManagerName name() {
        return name;
    }

    public ManagerAlias alias() {
        return alias;
    }

}
