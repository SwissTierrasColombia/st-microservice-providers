package com.ai.st.microservice.providers.modules.requests.domain;

public final class SupplyRequested {

    private final SupplyRequestedId id;
    private final SupplyRequestedName name;
    private final SupplyRequestedArea area;

    public SupplyRequested(SupplyRequestedId id, SupplyRequestedName name, SupplyRequestedArea area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    public static SupplyRequested fromPrimitives(Long id, String name, String area) {
        return new SupplyRequested(SupplyRequestedId.fromValue(id), SupplyRequestedName.fromValue(name),
                SupplyRequestedArea.fromValue(area));
    }

    public SupplyRequestedId id() {
        return id;
    }

    public SupplyRequestedName name() {
        return name;
    }

    public SupplyRequestedArea area() {
        return area;
    }
}
