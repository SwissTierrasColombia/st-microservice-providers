package com.ai.st.microservice.providers.modules.areas.infrastructure;

import com.ai.st.microservice.providers.modules.areas.domain.Area;
import com.ai.st.microservice.providers.modules.areas.domain.contracts.AreaRepository;
import com.ai.st.microservice.providers.modules.shared.domain.UserCode;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.repositories.ProviderUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public final class PostgresAreaRepository implements AreaRepository {

    private final ProviderUserRepository providerUserRepository;

    public PostgresAreaRepository(ProviderUserRepository providerUserRepository) {
        this.providerUserRepository = providerUserRepository;
    }

    @Override
    public List<Area> findAresByUser(UserCode userCode) {
        List<ProviderUserEntity> providerUsers = providerUserRepository.findByUserCode(userCode.value());
        return providerUsers.stream().map(this::mapping).collect(Collectors.toList());
    }

    private Area mapping(ProviderUserEntity providerUserEntity) {
        ProviderProfileEntity profile = providerUserEntity.getProviderProfile();
        return Area.fromPrimitives(profile.getId(), profile.getName());
    }

}
