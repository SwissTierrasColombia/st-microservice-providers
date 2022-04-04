package com.ai.st.microservice.providers.test.controllers.providers;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.business.RequestStateBusiness;
import com.ai.st.microservice.providers.controllers.v1.ProviderV1Controller;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RequestEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RequestStateEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRequestService;
import com.ai.st.microservice.providers.services.IRequestStateService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StGetRequestsByProviderAndUserClosedTests {

    private final static Logger log = LoggerFactory.getLogger(StGetRequestsByProviderAndUserClosedTests.class);

    @Autowired
    private ProviderV1Controller providerController;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IProviderCategoryService providerCategoryService;

    @Autowired
    private IRequestStateService requestStateService;

    @Autowired
    private IRequestService requestService;

    private ProviderEntity providerEntity;

    private Long userCode;

    @BeforeAll
    public void init() {

        ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        RequestStateEntity stateDelivery = requestStateService
                .getRequestStateById(RequestStateBusiness.REQUEST_STATE_DELIVERED);

        userCode = (long) 5;

        providerEntity = new ProviderEntity();
        providerEntity.setName(RandomStringUtils.random(10, true, false));
        providerEntity.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        providerEntity.setCreatedAt(new Date());
        providerEntity.setProviderCategory(providerCategoryCadastral);
        providerEntity = providerService.createProvider(providerEntity);

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setClosedAt(new Date());
        requestEntity.setClosedBy(userCode);
        requestEntity.setCreatedAt(new Date());
        requestEntity.setDeadline(new Date());
        requestEntity.setMunicipalityCode("70001");
        requestEntity.setObservations(RandomStringUtils.random(20, true, false));
        requestEntity.setPackageLabel(RandomStringUtils.random(10, false, true));
        requestEntity.setProvider(providerEntity);
        requestEntity.setRequestState(stateDelivery);
        requestService.createRequest(requestEntity);

        log.info("configured environment (StGetRequestsByProviderAndUserClosedTests)");
    }

    @Test
    @Transactional
    public void validateGetRequestsByProviderAndUserClosed() {

        ResponseEntity<?> data = providerController.getRequestsByProviderAndUserClosed(providerEntity.getId(),
                userCode);

        @SuppressWarnings("unchecked")
        List<RequestDto> requests = (List<RequestDto>) data.getBody();

        assertEquals(HttpStatus.OK, data.getStatusCode());
        Assert.notNull(requests, "La respuesta no puede ser null.");
        assertNotNull(requests.get(0));
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesNotExits() {
        ResponseEntity<?> data = providerController.getRequestsByProviderAndUserClosed((long) 150, userCode);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "Debe arrojar un estado http con código 422 ya que el proveedor no existe.");
    }

}
