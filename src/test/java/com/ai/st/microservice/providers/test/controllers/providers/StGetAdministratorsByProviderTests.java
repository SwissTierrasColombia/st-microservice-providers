package com.ai.st.microservice.providers.test.controllers.providers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.st.microservice.common.business.RoleBusiness;
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
import com.ai.st.microservice.providers.dto.ProviderAdministratorDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;
import com.ai.st.microservice.providers.services.IProviderAdministratorService;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRoleService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StGetAdministratorsByProviderTests {

    private final static Logger log = LoggerFactory.getLogger(StGetAdministratorsByProviderTests.class);

    @Autowired
    private ProviderV1Controller providerController;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IProviderCategoryService providerCategoryService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IProviderAdministratorService providerAdministratorService;

    private ProviderEntity providerEntity;

    private RoleEntity roleDirector;

    @BeforeAll
    public void init() {

        ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

        roleDirector = roleService.getRoleById(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);

        providerEntity = new ProviderEntity();
        providerEntity.setName(RandomStringUtils.random(10, true, false));
        providerEntity.setTaxIdentificationNumber(RandomStringUtils.random(10, false, true));
        providerEntity.setCreatedAt(new Date());
        providerEntity.setProviderCategory(providerCategoryCadastral);
        providerEntity = providerService.createProvider(providerEntity);

        ProviderAdministratorEntity userAdmin = new ProviderAdministratorEntity();
        userAdmin.setCreatedAt(new Date());
        userAdmin.setProvider(providerEntity);
        userAdmin.setRole(roleDirector);
        userAdmin.setUserCode((long) 5);
        userAdmin = providerAdministratorService.createProviderAdministrator(userAdmin);

        log.info("configured environment (StGetAdministratorsByProviderTests)");
    }

    @Test
    @Transactional
    public void validateGetAdministratorsByProvider() {
        ResponseEntity<?> data = providerController.getUsersAdministratorsByProvider(providerEntity.getId(), null);

        @SuppressWarnings("unchecked")
        List<ProviderAdministratorDto> response = (List<ProviderAdministratorDto>) data.getBody();

        assertEquals(HttpStatus.OK, data.getStatusCode());
        Assert.notNull(response, "La respuesta no puede ser null.");
        assertNotNull(response.get(0));
    }

    @Test
    @Transactional
    public void validateGetAdministratorsByProviderAndRoles() {

        List<Long> roles = new ArrayList<>();
        roles.add(roleDirector.getId());

        ResponseEntity<?> data = providerController.getUsersAdministratorsByProvider(providerEntity.getId(), roles);

        @SuppressWarnings("unchecked")
        List<ProviderAdministratorDto> response = (List<ProviderAdministratorDto>) data.getBody();

        assertEquals(HttpStatus.OK, data.getStatusCode());
        Assert.notNull(response, "La respuesta no puede ser null.");
        assertNotNull(response.get(0));
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesNotExits() {
        ResponseEntity<?> data = providerController.getUsersAdministratorsByProvider((long) 50, null);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "Debe arrojar un estado http con código 422 ya que el proveedor no existe.");
    }

}
