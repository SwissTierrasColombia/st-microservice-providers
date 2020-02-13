package com.ai.st.microservice.providers.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ai.st.microservice.providers.entities.EmitterTypeEnum;
import com.ai.st.microservice.providers.entities.RequestEntity;

public interface RequestRepository extends CrudRepository<RequestEntity, Long> {

	@Query("SELECT r FROM RequestEntity r WHERE r.provider.id = :providerId AND r.requestState.id = :requestStateId")
	List<RequestEntity> getRequestsByProviderIdAndStateId(@Param("providerId") Long providerId,
			@Param("requestStateId") Long requestStateId);

	@Query("SELECT r FROM RequestEntity r, EmitterEntity e WHERE r.id = e.request.id AND e.emitterCode = :emmiterCode AND e.emitterType = :emmiterType")
	List<RequestEntity> getRequestsByEmmiterCodeAndEmmiterType(@Param("emmiterCode") Long emmiterCode,
			@Param("emmiterType") EmitterTypeEnum emmiterType);

}
