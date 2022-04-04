package com.ai.st.microservice.providers.test.controllers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.ai.st.microservice.providers.controllers.v1.ProviderV1Controller;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StGetTypeSuppliesByProviderTests {

    private final static Logger log = LoggerFactory.getLogger(StGetTypeSuppliesByProviderTests.class);

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

    private ProviderEntity providerEntity;

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

        ProviderProfileEntity providerProfile = new ProviderProfileEntity();
        providerProfile.setName(RandomStringUtils.random(10, true, false));
        providerProfile.setDescription(RandomStringUtils.random(10, true, false));
        providerProfile.setProvider(providerEntity);
        providerProfile = providerProfileService.createProviderProfile(providerProfile);

        TypeSupplyEntity typeSupply = new TypeSupplyEntity();
        typeSupply.setName(RandomStringUtils.random(10, true, false));
        typeSupply.setIsMetadataRequired(false);
        typeSupply.setIsModelRequired(false);
        typeSupply.setCreatedAt(new Date());
        typeSupply.setProvider(providerEntity);
        typeSupply.setProviderProfile(providerProfile);
        typeSupply = typeSupplyService.createTypeSupply(typeSupply);

        log.info("configured environment (StGetTypeSuppliesByProviderTests)");
    }

    @Test
    @Transactional
    public void validateGetTypesSuppliesByProvider() {

        ResponseEntity<Object> data = providerController.getTypeSuppliesByProvider(providerEntity.getId(), false);

        @SuppressWarnings("unchecked")
        List<TypeSupplyDto> typeSupplies = (List<TypeSupplyDto>) data.getBody();

        assertEquals(HttpStatus.OK, data.getStatusCode());
        Assert.notNull(typeSupplies, "La respuesta no puede ser nula.");
        assertTrue(typeSupplies.get(0) instanceof TypeSupplyDto);
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesNotExists() {
        ResponseEntity<Object> data = providerController.getTypeSuppliesByProvider((long) 150, false);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "Debe arrojar un estado http con código 422");
    }

}
