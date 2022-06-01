package com.ai.st.microservice.providers.test.controllers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.ai.st.microservice.providers.dto.CreateTypeSupplyDto;
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
public class StUpdateTypeSupplyTests {

    private final static Logger log = LoggerFactory.getLogger(StUpdateTypeSupplyTests.class);

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

        log.info("configured environment (StUpdateTypeSupplyTests)");
    }

    @Test
    @Transactional
    public void validateUpdateTypeSupply() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        TypeSupplyDto typeSupply = (TypeSupplyDto) data.getBody();

        Assert.notNull(typeSupply, "La respuesta no puede ser null.");
        assertEquals(HttpStatus.OK, data.getStatusCode());
        assertTrue(true);
    }

    @Test
    @Transactional
    public void shouldErrorWhenNameIsEmpty() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setProviderProfileId(profileEntity.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el insumo porque no se ha enviado el nombre.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenDescriptionIsEmpty() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el insumo porque no se ha enviado la descripción.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProfileIsEmpty() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el insumo porque no se ha enviado el perfil.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenExtensionsIsEmpty() {

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el insumo porque no se ha enviado las extensiones.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenMetadataIsEmpty() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el insumo porque no se ha enviado la metadata.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenModelIsEmpty() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.BAD_REQUEST, data.getStatusCode(),
                "No debería actualizar el insumo porque no se ha enviado el modelo.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProviderDoesExists() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply((long) 150, typeSupply.getId(), createTypeSupply);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería actualizar el insumo porque el proveedor no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProfileDoesExists() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId((long) 150);

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería actualizar el insumo porque el perfil no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenTypeSupplyDoesExists() {

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId((long) 150);

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), (long) 150,
                createTypeSupply);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería actualizar el insumo porque el tipo de insumo no existe.");
    }

    @Test
    @Transactional
    public void shouldErrorWhenProfileDoesNotBelongToProvider() {

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

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity2.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería actualizar el insumo porque el perfil no pertenece al proveedor.");
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

        List<String> extensions = new ArrayList<>();
        extensions.add("png");

        CreateTypeSupplyDto createTypeSupply = new CreateTypeSupplyDto();
        createTypeSupply.setDescription(RandomStringUtils.random(10, true, false));
        createTypeSupply.setExtensions(extensions);
        createTypeSupply.setMetadataRequired(false);
        createTypeSupply.setModelRequired(false);
        createTypeSupply.setName(RandomStringUtils.random(10, true, false));
        createTypeSupply.setProviderProfileId(profileEntity2.getId());

        ResponseEntity<?> data = providerController.updateTypeSupply(providerEntity2.getId(), typeSupply.getId(),
                createTypeSupply);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, data.getStatusCode(),
                "No debería actualizar el insumo porque el tipo de insumo no pertenece al proveedor.");
    }

}
