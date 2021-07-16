package com.ai.st.microservice.providers.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ai.st.microservice.providers.entities.EmitterTypeEnum;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;

public interface RequestRepository extends PagingAndSortingRepository<RequestEntity, Long> {

    @Query("SELECT r FROM RequestEntity r WHERE r.provider.id = :providerId AND r.requestState.id = :requestStateId")
    List<RequestEntity> getRequestsByProviderIdAndStateId(@Param("providerId") Long providerId,
                                                          @Param("requestStateId") Long requestStateId);

    @Query("SELECT r FROM RequestEntity r, EmitterEntity e WHERE r.id = e.request.id AND e.emitterCode = :emmiterCode AND e.emitterType = :emmiterType")
    List<RequestEntity> getRequestsByEmmiterCodeAndEmmiterType(@Param("emmiterCode") Long emmiterCode,
                                                               @Param("emmiterType") EmitterTypeEnum emmiterType);

    List<RequestEntity> findByClosedByAndProviderAndRequestState(Long closedBy, ProviderEntity provider,
                                                                 RequestStateEntity requestState);

    @Query(value = "SELECT r FROM RequestEntity r, EmitterEntity e WHERE r.id = e.request.id AND e.emitterCode = :emmiterCode AND e.emitterType = :emmiterType AND r.municipalityCode = :municipalityCode")
    Page<RequestEntity> getRequestsByManagerAndMunicipality(@Param("emmiterCode") Long emmiterCode,
                                                            @Param("emmiterType") EmitterTypeEnum emmiterType, @Param("municipalityCode") String municipalityCode,
                                                            Pageable pageable);

    @Query(value = "SELECT r FROM RequestEntity r, EmitterEntity e WHERE r.id = e.request.id AND e.emitterCode = :emmiterCode AND e.emitterType = :emmiterType AND r.provider.id = :providerId")
    Page<RequestEntity> getRequestsByManagerAndProvider(@Param("emmiterCode") Long emmiterCode,
                                                        @Param("emmiterType") EmitterTypeEnum emmiterType, @Param("providerId") Long providerId, Pageable pageable);

    @Query(value = "SELECT r FROM RequestEntity r, EmitterEntity e WHERE r.id = e.request.id AND e.emitterCode = :emmiterCode AND e.emitterType = :emmiterType AND r.packageLabel = :packageLabel")
    List<RequestEntity> getRequestsByManagerAndPackage(@Param("emmiterCode") Long emmiterCode,
                                                       @Param("emmiterType") EmitterTypeEnum emmiterType, @Param("packageLabel") String packageLabel);

    List<RequestEntity> findByPackageLabel(String packageLabel);

    @Query(nativeQuery = true, value = "select " +
            "r.* " +
            "from " +
            "providers.requests r " +
            "where " +
            "(r.request_state_id = 2 " +
            "or r.sent_review_at is not null) " +
            "and r.provider_id = 8 " +
            "and (r.sent_review_at between :from and :to " +
            "or r.closed_at between :from and :to) ")
    List<RequestEntity> getRequestsForReportSNR(@Param("from") Date from, @Param("to") Date to);

}
