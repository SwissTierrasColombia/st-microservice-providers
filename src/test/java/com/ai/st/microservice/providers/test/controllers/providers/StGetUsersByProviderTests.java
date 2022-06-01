package com.ai.st.microservice.providers.test.controllers.providers;

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
import org.springframework.util.Assert;

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.controllers.v1.ProviderV1Controller;
import com.ai.st.microservice.providers.dto.ProviderUserDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IProviderUserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StGetUsersByProviderTests {

    private final static Logger log = LoggerFactory.getLogger(StGetUsersByProviderTests.class);

    @Autowired
    private ProviderV1Controller providerController;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IProviderCategoryService providerCategoryService;

    @Autowired
    private IProviderProfileService providerProfileService;

    @Autowired
    private IProviderUserService providerUserService;

    private ProviderEntity providerEntity;

    private ProviderProfileEntity providerProfile;

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

        providerProfile = new ProviderProfileEntity();
        providerProfile.setName(RandomStringUtils.random(10, true, false));
        providerProfile.setDescription(RandomStringUtils.random(10, true, false));
        providerProfile.setProvider(providerEntity);
        providerProfile = providerProfileService.createProviderProfile(providerProfile);

        ProviderUserEntity providerUser = new ProviderUserEntity();
        providerUser.setCreatedAt(new Date());
        providerUser.setProvider(providerEntity);
        providerUser.setProviderProfile(providerProfile);
        providerUser.setUserCode((long) 20);
        providerUserService.createProviderUser(providerUser);

        log.info("configured environment (StGetUsersByProviderTests)");
    }

    @Test
    @Transactional
    public void validateGetUsersByProvider() {

        ResponseEntity<?> data = providerController.getUsersByProvider(providerEntity.getId(), null);

        @SuppressWarnings("unchecked")
        List<ProviderUserDto> response = (List<ProviderUserDto>) data.getBody();

        assertEquals(HttpStatus.OK, data.getStatusCode());
        Assert.notNull(response, "La respuesta no puede ser nula.");
        assertNotNull(response.get(0));
    }

    @Test
    @Transactional
    public void validateGetUsersByProviderAndProfiles() {

        List<Long> profiles = new ArrayList<Long>();
        profiles.add(providerProfile.getId());

        ResponseEntity<?> data = providerController.getUsersByProvider(providerEntity.getId(), profiles);

        @SuppressWarnings("unchecked")
        List<ProviderUserDto> response = (List<ProviderUserDto>) data.getBody();

        assertEquals(HttpStatus.OK, data.getStatusCode());
        Assert.notNull(response, "La respuesta no puede ser null.");
        assertNotNull(response.get(0));
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesNotExits() {
        ResponseEntity<?> data = providerController.getUsersByProvider((long) 150, null);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería obtener los usuarios porque el proveedor no existe.");
    }

}