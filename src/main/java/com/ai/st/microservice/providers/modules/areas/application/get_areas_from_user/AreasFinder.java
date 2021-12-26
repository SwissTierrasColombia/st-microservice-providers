package com.ai.st.microservice.providers.modules.areas.application.get_areas_from_user;

import com.ai.st.microservice.providers.modules.areas.application.AreaResponse;
import com.ai.st.microservice.providers.modules.areas.domain.Area;
import com.ai.st.microservice.providers.modules.areas.domain.contracts.AreaRepository;
import com.ai.st.microservice.providers.modules.shared.application.ListResponse;
import com.ai.st.microservice.providers.modules.shared.application.QueryUseCase;
import com.ai.st.microservice.providers.modules.shared.domain.Service;
import com.ai.st.microservice.providers.modules.shared.domain.UserCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class AreasFinder implements QueryUseCase<AreasFinderQuery, ListResponse<AreaResponse>> {

    private final AreaRepository areaRepository;

    public AreasFinder(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public ListResponse<AreaResponse> handle(AreasFinderQuery query) {

        UserCode userCode = UserCode.fromValue(query.userId());

        List<Area> areas = areaRepository.findAresByUser(userCode);

        return new ListResponse<>(areas.stream().map(AreaResponse::fromAggregate).collect(Collectors.toList()));
    }

}
