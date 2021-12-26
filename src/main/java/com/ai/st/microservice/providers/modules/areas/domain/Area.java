package com.ai.st.microservice.providers.modules.areas.domain;

public final class Area {

    private final AreaId areaId;
    private final AreaName areaName;

    public Area(AreaId areaId, AreaName areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public static Area fromPrimitives(Long areaId, String name) {
        return new Area(
                AreaId.fromValue(areaId),
                AreaName.fromValue(name)
        );
    }

    public AreaId areaId() {
        return areaId;
    }

    public AreaName areaName() {
        return areaName;
    }

}
