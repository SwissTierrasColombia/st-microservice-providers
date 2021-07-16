package com.ai.st.microservice.providers.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;

public interface IRequestService {

    RequestEntity createRequest(RequestEntity requestEntity);

    List<RequestEntity> getRequestsByProviderIdAndStateId(Long providerId, Long requestStateId);

    RequestEntity getRequestById(Long id);

    RequestEntity updateRequest(RequestEntity requestEntity);

    List<RequestEntity> getRequestsByEmmiter(Long emmiterCode, String emmiterType);

    List<RequestEntity> getRequestByClosedByAndProviderAndRequestState(Long closedBy, ProviderEntity provider,
                                                                       RequestStateEntity requestState);

    Page<RequestEntity> getRequestsByManagerAndMunicipality(Long emmiterCode, String emmiterType,
                                                            String municipalityCode, int page, int numberItems);

    Page<RequestEntity> getRequestsByManagerAndProvider(Long emmiterCode, String emmiterType, Long providerId,
                                                        int page, int numberItems);

    List<RequestEntity> getRequestsByManagerAndPackage(Long emmiterCode, String emmiterType,
                                                       String packageLabel);

    List<RequestEntity> getRequestsByPackage(String packageLabel);

    List<RequestEntity> getRequestForReportSNR(Date from, Date to);

}
