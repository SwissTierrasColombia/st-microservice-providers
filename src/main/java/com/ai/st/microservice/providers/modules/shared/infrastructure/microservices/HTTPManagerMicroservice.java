package com.ai.st.microservice.providers.modules.shared.infrastructure.microservices;

import com.ai.st.microservice.common.clients.ManagerFeignClient;
import com.ai.st.microservice.common.dto.managers.MicroserviceManagerDto;

import com.ai.st.microservice.providers.modules.shared.domain.contracts.ManagerMicroservice;
import com.ai.st.microservice.providers.modules.shared.domain.exceptions.ManagerNotFound;
import com.ai.st.microservice.providers.modules.shared.domain.Manager;
import com.ai.st.microservice.providers.modules.shared.domain.ManagerCode;

import org.springframework.stereotype.Service;

@Service
public final class HTTPManagerMicroservice implements ManagerMicroservice {

    private final ManagerFeignClient managerFeignClient;

    public HTTPManagerMicroservice(ManagerFeignClient managerFeignClient) {
        this.managerFeignClient = managerFeignClient;
    }

    @Override
    public Manager getManager(ManagerCode managerCode) {
        MicroserviceManagerDto managerDto = managerFeignClient.findById(managerCode.value());
        if (managerDto == null) {
            throw new ManagerNotFound(managerCode.value());
        }
        return Manager.fromPrimitives(managerDto.getId(), managerDto.getName(), managerDto.getAlias());
    }

}
