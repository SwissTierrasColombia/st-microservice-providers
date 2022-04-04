package com.ai.st.microservice.providers.modules.shared.infrastructure.microservices;

import com.ai.st.microservice.common.clients.WorkspaceFeignClient;
import com.ai.st.microservice.common.dto.workspaces.MicroserviceMunicipalityDto;
import com.ai.st.microservice.providers.modules.shared.domain.Federation;
import com.ai.st.microservice.providers.modules.shared.domain.FederationCode;
import com.ai.st.microservice.providers.modules.shared.domain.contracts.WorkspaceMicroservice;
import com.ai.st.microservice.providers.modules.shared.domain.exceptions.FederationNotFound;
import org.springframework.stereotype.Service;

@Service
public final class HTTPWorkspaceMicroservice implements WorkspaceMicroservice {

    private final WorkspaceFeignClient workspaceFeignClient;

    public HTTPWorkspaceMicroservice(WorkspaceFeignClient workspaceFeignClient) {
        this.workspaceFeignClient = workspaceFeignClient;
    }

    @Override
    public Federation getFederation(FederationCode federationCode) {

        MicroserviceMunicipalityDto municipalityDto = workspaceFeignClient
                .findMunicipalityByCode(federationCode.value());

        if (municipalityDto == null) {
            throw new FederationNotFound(federationCode.value());
        }

        return Federation.fromPrimitives(municipalityDto.getCode(), municipalityDto.getName(),
                municipalityDto.getDepartment().getName());
    }
}
