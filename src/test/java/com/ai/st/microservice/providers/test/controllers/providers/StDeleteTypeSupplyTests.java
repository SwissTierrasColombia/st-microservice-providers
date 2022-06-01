package com.ai.st.microservice.providers.test.controllers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.business.RequestStateBusiness;
import com.ai.st.microservice.providers.business.SupplyRequestedStateBusiness;
import com.ai.st.microservice.providers.controllers.v1.ProviderV1Controller;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RequestEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RequestStateEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.SupplyRequestedStateEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRequestService;
import com.ai.st.microservice.providers.services.IRequestStateService;
import com.ai.st.microservice.providers.services.ISupplyRequestedStateService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StDeleteTypeSupplyTests {

    private final static Logger log = LoggerFactory.getLogger(StDeleteTypeSupplyTests.class);

    @Autowired
    private ProviderV1Controller providerController;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IProviderCategoryService providerCategoryService;

    @Autowired
    private IProviderProfileService providerProfileService;

    @Autowired
    private ITypeSupplyService typeSupplyService;

    @Autowired
    private IRequestStateService requestStateService;

    @Autowired
    private IRequestService requestService;

    @Autowired
    private ISupplyRequestedStateService supplyRequestedStateService;

    private ProviderEntity providerEntity;
    private ProviderProfileEntity profileEntity;
    private TypeSupplyEntity typeSupply;

    @BeforeAll
    public void init() {

        ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        providerEntity = new ProviderEntity();
        providerEntity.setName(RandomStringUtils.random(10, true, false));
        providerEntity.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        providerEntity.setCreatedAt(new Date());
        providerEntity.setProviderCategory(providerCategoryCadastral);
        providerEntity = providerService.createProvider(providerEntity);

        profileEntity = new ProviderProfileEntity();
        profileEntity.setName(RandomStringUtils.random(10, true, false));
        profileEntity.setDescription(RandomStringUtils.random(10, true, false));
        profileEntity.setProvider(providerEntity);
        providerProfileService.createProviderProfile(profileEntity);

        typeSupply = new TypeSupplyEntity();
        typeSupply.setName(RandomStringUtils.random(10, true, false));
        typeSupply.setIsMetadataRequired(false);
        typeSupply.setIsModelRequired(false);
        typeSupply.setCreatedAt(new Date());
        typeSupply.setProvider(providerEntity);
        typeSupply.setProviderProfile(profileEntity);
        typeSupply = typeSupplyService.createTypeSupply(typeSupply);

        log.info("configured environment (StDeleteTypeSupplyTests) " + providerEntity.getId());
    }

    @Test
    @Transactional
    public void validateDeleteTypeSupply() {

        TypeSupplyEntity typeSupply2 = new TypeSupplyEntity();
        typeSupply2.setName(RandomStringUtils.random(10, true, false));
        typeSupply2.setIsMetadataRequired(false);
        typeSupply2.setIsModelRequired(false);
        typeSupply2.setCreatedAt(new Date());
        typeSupply2.setProvider(providerEntity);
        typeSupply2.setProviderProfile(profileEntity);
        typeSupply2 = typeSupplyService.createTypeSupply(typeSupply2);

        ResponseEntity<?> data = providerController.deleteTypeSupply(providerEntity.getId(), typeSupply2.getId());
        assertEquals(HttpStatus.NO_CONTENT, data.getStatusCode());
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesNotExists() {

        ResponseEntity<?> data = providerController.deleteTypeSupply((long) 150, typeSupply.getId());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el tipo de insumo porque el proveedor no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenTypeSupplyDoesNotExists() {

        ResponseEntity<?> data = providerController.deleteTypeSupply(providerEntity.getId(), (long) 150);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el tipo de insumo porque el insumo no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenTypeSupplyDoesNotBelongToProvider() {

        ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ProviderEntity providerEntity2 = new ProviderEntity();
        providerEntity2.setName(RandomStringUtils.random(10, true, false));
        providerEntity2.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        providerEntity2.setCreatedAt(new Date());
        providerEntity2.setProviderCategory(providerCategoryCadastral);
        providerEntity2 = providerService.createProvider(providerEntity2);

        ProviderProfileEntity profileEntity2 = new ProviderProfileEntity();
        profileEntity2.setName(RandomStringUtils.random(10, true, false));
        profileEntity2.setDescription(RandomStringUtils.random(10, true, false));
        profileEntity2.setProvider(providerEntity2);
        providerProfileService.createProviderProfile(profileEntity2);

        TypeSupplyEntity typeSupply2 = new TypeSupplyEntity();
        typeSupply2.setName(RandomStringUtils.random(10, true, false));
        typeSupply2.setIsMetadataRequired(false);
        typeSupply2.setIsModelRequired(false);
        typeSupply2.setCreatedAt(new Date());
        typeSupply2.setProvider(providerEntity2);
        typeSupply2.setProviderProfile(profileEntity2);
        typeSupply2 = typeSupplyService.createTypeSupply(typeSupply2);

        ResponseEntity<?> data = providerController.deleteTypeSupply(providerEntity.getId(), typeSupply2.getId());

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el tipo de insumo porque el insumo no pertenece al proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenTypeSupplyHasBeenRequested() {

        ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        ProviderEntity providerEntity2 = new ProviderEntity();
        providerEntity2.setName(RandomStringUtils.random(10, true, false));
        providerEntity2.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        providerEntity2.setCreatedAt(new Date());
        providerEntity2.setProviderCategory(providerCategoryCadastral);
        providerEntity2 = providerService.createProvider(providerEntity2);

        ProviderProfileEntity profileEntity2 = new ProviderProfileEntity();
        profileEntity2.setName(RandomStringUtils.random(10, true, false));
        profileEntity2.setDescription(RandomStringUtils.random(10, true, false));
        profileEntity2.setProvider(providerEntity2);
        providerProfileService.createProviderProfile(profileEntity2);

        TypeSupplyEntity typeSupply2 = new TypeSupplyEntity();
        typeSupply2.setName(RandomStringUtils.random(10, true, false));
        typeSupply2.setIsMetadataRequired(false);
        typeSupply2.setIsModelRequired(false);
        typeSupply2.setCreatedAt(new Date());
        typeSupply2.setProvider(providerEntity2);
        typeSupply2.setProviderProfile(profileEntity2);
        typeSupply2 = typeSupplyService.createTypeSupply(typeSupply2);

        RequestStateEntity state = requestStateService
                .getRequestStateById(RequestStateBusiness.REQUEST_STATE_REQUESTED);

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setCreatedAt(new Date());
        requestEntity.setDeadline(new Date());
        requestEntity.setMunicipalityCode("70001");
        requestEntity.setObservations(RandomStringUtils.random(20, true, false));
        requestEntity.setPackageLabel(RandomStringUtils.random(10, false, true));
        requestEntity.setProvider(providerEntity);
        requestEntity.setRequestState(state);

        SupplyRequestedStateEntity stateSupply = supplyRequestedStateService
                .getStateById(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_ACCEPTED);

        SupplyRequestedEntity supplyRequest = new SupplyRequestedEntity();
        supplyRequest.setCreatedAt(new Date());
        supplyRequest.setDelivered(false);
        supplyRequest.setDescription(RandomStringUtils.random(20, true, false));
        supplyRequest.setRequest(requestEntity);
        supplyRequest.setState(stateSupply);
        supplyRequest.setTypeSupply(typeSupply2);

        List<SupplyRequestedEntity> supplies = new ArrayList<>();
        supplies.add(supplyRequest);

        requestEntity.setSupplies(supplies);

        requestEntity = requestService.createRequest(requestEntity);

        ResponseEntity<?> data = providerController.deleteTypeSupply(providerEntity2.getId(), typeSupply2.getId());

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería eliminar el tipo de insumo porque el insumo ya pertenece a una solicitud.");
    }

}
