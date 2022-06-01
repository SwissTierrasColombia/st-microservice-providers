package com.ai.st.microservice.providers.test.controllers.administrators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.ai.st.microservice.providers.controllers.v1.ProviderAdministratorV1Controller;
import com.ai.st.microservice.providers.dto.AddAdministratorToProviderDto;
import com.ai.st.microservice.providers.dto.ProviderAdministratorDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.RoleEntity;
import com.ai.st.microservice.providers.services.IProviderAdministratorService;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRoleService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class StAddAdministratorToProviderTests {

    private final static Logger log = LoggerFactory.getLogger(StAddAdministratorToProviderTests.class);

    @Autowired
    private ProviderAdministratorV1Controller administratorController;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IProviderCategoryService providerCategoryService;

    @Autowired
    private IProviderAdministratorService providerAdministratorService;

    @Autowired
    private IRoleService roleService;

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

        log.info("configured environment (StAddAdministratorToProviderTests)");
    }

    @Test
    @Transactional
    public void validateAddAdministratorToProvider() {

        AddAdministratorToProviderDto addAdminToProviderDto = new AddAdministratorToProviderDto();

        addAdminToProviderDto.setProviderId(providerEntity.getId());
        addAdminToProviderDto.setRoleId(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);
        addAdminToProviderDto.setUserCode((long) 5);

        ResponseEntity<?> data = administratorController.addUserAdministratorToProvider(addAdminToProviderDto);
        @SuppressWarnings("unchecked")
        List<ProviderAdministratorDto> listProviderAdministratorDto = (List<ProviderAdministratorDto>) data.getBody();

        Assert.notNull(listProviderAdministratorDto, "La respuesta no puede ser null.");
        assertEquals(HttpStatus.CREATED, data.getStatusCode());
        assertTrue((listProviderAdministratorDto.get(0)) != null);
    }

    @Test
    @Transactional
    public void shouldErrorWhenUserIsEmpty() {

        AddAdministratorToProviderDto addAdminToProviderDto = new AddAdministratorToProviderDto();

        addAdminToProviderDto.setProviderId(providerEntity.getId());
        addAdminToProviderDto.setRoleId(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);

        ResponseEntity<?> data = administratorController.addUserAdministratorToProvider(addAdminToProviderDto);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería agregar el usuario al proveedor porque no se ha enviado el código de usuario.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderIsEmpty() {

        AddAdministratorToProviderDto addAdminToProviderDto = new AddAdministratorToProviderDto();

        addAdminToProviderDto.setRoleId(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);
        addAdminToProviderDto.setUserCode((long) 5);

        ResponseEntity<?> data = administratorController.addUserAdministratorToProvider(addAdminToProviderDto);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería agregar el usuario al proveedor porque no se ha enviado el proveedor.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenRoleIsEmpty() {

        AddAdministratorToProviderDto addAdminToProviderDto = new AddAdministratorToProviderDto();

        addAdminToProviderDto.setProviderId(providerEntity.getId());
        addAdminToProviderDto.setUserCode((long) 5);

        ResponseEntity<?> data = administratorController.addUserAdministratorToProvider(addAdminToProviderDto);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería agregar el usuario al proveedor porque no se ha enviado el rol.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesExists() {

        AddAdministratorToProviderDto addAdminToProviderDto = new AddAdministratorToProviderDto();

        addAdminToProviderDto.setProviderId((long) 150);
        addAdminToProviderDto.setRoleId(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);
        addAdminToProviderDto.setUserCode((long) 5);

        ResponseEntity<?> data = administratorController.addUserAdministratorToProvider(addAdminToProviderDto);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería agregar el usuario al proveedor porque el proveedor no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenRoleDoesExists() {

        RoleEntity roleEntity = roleService.getRoleById(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);
        ProviderAdministratorEntity providerAdmin = new ProviderAdministratorEntity();
        providerAdmin.setCreatedAt(new Date());
        providerAdmin.setProvider(providerEntity);
        providerAdmin.setRole(roleEntity);
        providerAdmin.setUserCode((long) 7);
        providerAdministratorService.createProviderAdministrator(providerAdmin);

        AddAdministratorToProviderDto addAdminToProviderDto = new AddAdministratorToProviderDto();
        addAdminToProviderDto.setProviderId(providerEntity.getId());
        addAdminToProviderDto.setRoleId(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);
        addAdminToProviderDto.setUserCode((long) 7);

        ResponseEntity<?> data = administratorController.addUserAdministratorToProvider(addAdminToProviderDto);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería agregar el usuario al proveedor porque el usuario ya ha sido agregado al proveedor.");
    }

}
