package com.ai.st.microservice.providers;

import java.util.Date;

import com.ai.st.microservice.common.business.RoleBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.business.PetitionStateBusiness;
import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.business.RequestStateBusiness;
import com.ai.st.microservice.providers.business.SupplyRequestedStateBusiness;
import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.entities.PetitionStateEntity;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.entities.RoleEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedStateEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.services.IExtensionService;
import com.ai.st.microservice.providers.services.IPetitionStateService;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRequestStateService;
import com.ai.st.microservice.providers.services.IRoleService;
import com.ai.st.microservice.providers.services.ISupplyRequestedStateService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@Component
public class StMicroserviceProvidersApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(StMicroserviceProvidersApplicationStartup.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private IProviderCategoryService providerCategoryService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IProviderProfileService providerProfileService;

    @Autowired
    private ITypeSupplyService typeSupplyService;

    @Autowired
    private IExtensionService extensionService;

    @Autowired
    private IRequestStateService requestStateService;

    @Autowired
    private ISupplyRequestedStateService supplyRequestedStateService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPetitionStateService petitionStateService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("ST - Loading Domains ... ");

        this.initProvidersCategories();
        this.initRequestsStates();
        this.initSupplyRequestedStates();
        this.initRoles();
        this.initPetitionStates();

        if (!activeProfile.equalsIgnoreCase("test")) {
            this.initProviders();
        }

    }

    public void initProvidersCategories() {
        Long countCategories = providerCategoryService.getCount();
        if (countCategories == 0) {

            try {

                ProviderCategoryEntity providerCategoryCadastral = new ProviderCategoryEntity();
                providerCategoryCadastral.setId(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);
                providerCategoryCadastral.setName("CATASTRO");
                providerCategoryService.createProviderCategory(providerCategoryCadastral);

                ProviderCategoryEntity providerCategoryRegistry = new ProviderCategoryEntity();
                providerCategoryRegistry.setId(ProviderCategoryBusiness.PROVIDER_CATEGORY_REGISTRY);
                providerCategoryRegistry.setName("REGISTRO");
                providerCategoryService.createProviderCategory(providerCategoryRegistry);

                ProviderCategoryEntity providerCategoryLand = new ProviderCategoryEntity();
                providerCategoryLand.setId(ProviderCategoryBusiness.PROVIDER_CATEGORY_LAND);
                providerCategoryLand.setName("TIERRAS");
                providerCategoryService.createProviderCategory(providerCategoryLand);

                ProviderCategoryEntity providerCategoryGeneral = new ProviderCategoryEntity();
                providerCategoryGeneral.setId(ProviderCategoryBusiness.PROVIDER_CATEGORY_GENERAL);
                providerCategoryGeneral.setName("GENERAL");
                providerCategoryService.createProviderCategory(providerCategoryGeneral);

                log.info("The domains 'providers categories' have been loaded!");
            } catch (Exception e) {
                log.error("Failed to load 'providers categories' domains");
            }

        }
    }

    public void initProviders() {
        Long countProviders = providerService.getCount();
        if (countProviders == 0) {

            try {

                ProviderCategoryEntity providerCategoryCadastral = providerCategoryService
                        .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_CADASTRAL);

                ProviderCategoryEntity providerCategoryRegistry = providerCategoryService
                        .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_REGISTRY);

                ProviderCategoryEntity providerCategoryLand = providerCategoryService
                        .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_LAND);

                ProviderCategoryEntity providerCategoryGeneral = providerCategoryService
                        .getProviderCategoryById(ProviderCategoryBusiness.PROVIDER_CATEGORY_GENERAL);

                // provider IGAC

                ProviderEntity providerIgac = new ProviderEntity();
                providerIgac.setName("INSTITUTO GEOGRÁFICO AGUSTÍN CODAZZI");
                providerIgac.setAlias("IGAC");
                providerIgac.setTaxIdentificationNumber("8999990049");
                providerIgac.setCreatedAt(new Date());
                providerIgac.setProviderCategory(providerCategoryCadastral);
                providerIgac.setActive(true);
                providerIgac = providerService.createProvider(providerIgac);

                ProviderProfileEntity profileCadastral = new ProviderProfileEntity();
                profileCadastral.setName("CATASTRO");
                profileCadastral.setProvider(providerIgac);
                profileCadastral = providerProfileService.createProviderProfile(profileCadastral);

                ProviderProfileEntity profileCartographic = new ProviderProfileEntity();
                profileCartographic.setName("CARTOGRAFÍA");
                profileCartographic.setProvider(providerIgac);
                profileCartographic = providerProfileService.createProviderProfile(profileCartographic);

                ProviderProfileEntity profileAgrological = new ProviderProfileEntity();
                profileAgrological.setName("AGROLOGÍA");
                profileAgrological.setProvider(providerIgac);
                profileAgrological = providerProfileService.createProviderProfile(profileAgrological);

                typeSupplyService.setValueSequence((long) 1);
                TypeSupplyEntity typeSupply2 = new TypeSupplyEntity();
                typeSupply2.setName("Bases catastrales del municipio");
                typeSupply2.setIsMetadataRequired(false);
                typeSupply2.setIsModelRequired(true);
                typeSupply2.setCreatedAt(new Date());
                typeSupply2.setProvider(providerIgac);
                typeSupply2.setProviderProfile(profileCadastral);
                typeSupply2.setActive(true);
                typeSupply2 = typeSupplyService.createTypeSupply(typeSupply2);

                ExtensionEntity extension4 = new ExtensionEntity();
                extension4.setName("xtf");
                extension4.setTypeSupply(typeSupply2);
                extensionService.createExtension(extension4);

                TypeSupplyEntity typeSupply5 = new TypeSupplyEntity();
                typeSupply5.setName("Ortoimagen fuentes existentes");
                typeSupply5.setIsMetadataRequired(false);
                typeSupply5.setIsModelRequired(false);
                typeSupply5.setCreatedAt(new Date());
                typeSupply5.setProvider(providerIgac);
                typeSupply5.setProviderProfile(profileCartographic);
                typeSupply5.setActive(true);
                typeSupply5 = typeSupplyService.createTypeSupply(typeSupply5);

                ExtensionEntity extension8 = new ExtensionEntity();
                extension8.setName("tif");
                extension8.setTypeSupply(typeSupply5);
                extensionService.createExtension(extension8);

                ExtensionEntity extension9 = new ExtensionEntity();
                extension9.setName("img");
                extension9.setTypeSupply(typeSupply5);
                extensionService.createExtension(extension9);

                ExtensionEntity extension25 = new ExtensionEntity();
                extension25.setName("ecw");
                extension25.setTypeSupply(typeSupply5);
                extensionService.createExtension(extension25);

                ExtensionEntity extension26 = new ExtensionEntity();
                extension26.setName("sid");
                extension26.setTypeSupply(typeSupply5);
                extensionService.createExtension(extension26);

                TypeSupplyEntity typeSupply13 = new TypeSupplyEntity();
                typeSupply13.setName("Ortoimagen (insumo para catastro)");
                typeSupply13.setIsMetadataRequired(false);
                typeSupply13.setIsModelRequired(false);
                typeSupply13.setCreatedAt(new Date());
                typeSupply13.setProvider(providerIgac);
                typeSupply13.setProviderProfile(profileCartographic);
                typeSupply13.setActive(true);
                typeSupply13 = typeSupplyService.createTypeSupply(typeSupply13);

                ExtensionEntity extension31 = new ExtensionEntity();
                extension31.setName("tif");
                extension31.setTypeSupply(typeSupply13);
                extensionService.createExtension(extension31);

                ExtensionEntity extension32 = new ExtensionEntity();
                extension32.setName("img");
                extension32.setTypeSupply(typeSupply13);
                extensionService.createExtension(extension32);

                ExtensionEntity extension33 = new ExtensionEntity();
                extension33.setName("ecw");
                extension33.setTypeSupply(typeSupply13);
                extensionService.createExtension(extension33);

                ExtensionEntity extension34 = new ExtensionEntity();
                extension34.setName("sid");
                extension34.setTypeSupply(typeSupply13);
                extensionService.createExtension(extension34);

                TypeSupplyEntity typeSupply4 = new TypeSupplyEntity();
                typeSupply4.setName("Cartografía Básica Vectorial");
                typeSupply4.setIsMetadataRequired(false);
                typeSupply4.setIsModelRequired(false);
                typeSupply4.setCreatedAt(new Date());
                typeSupply4.setProvider(providerIgac);
                typeSupply4.setProviderProfile(profileCartographic);
                typeSupply4.setActive(true);
                typeSupply4 = typeSupplyService.createTypeSupply(typeSupply4);

                ExtensionEntity extension6 = new ExtensionEntity();
                extension6.setName("shp");
                extension6.setTypeSupply(typeSupply4);
                extensionService.createExtension(extension6);

                ExtensionEntity extension7 = new ExtensionEntity();
                extension7.setName("gpkg");
                extension7.setTypeSupply(typeSupply4);
                extensionService.createExtension(extension7);

                TypeSupplyEntity typeSupply7 = new TypeSupplyEntity();
                typeSupply7.setName("Modelo Digital de Elevación");
                typeSupply7.setIsMetadataRequired(false);
                typeSupply7.setIsModelRequired(false);
                typeSupply7.setCreatedAt(new Date());
                typeSupply7.setProvider(providerIgac);
                typeSupply7.setProviderProfile(profileCartographic);
                typeSupply7.setActive(true);
                typeSupply7 = typeSupplyService.createTypeSupply(typeSupply7);

                ExtensionEntity extension12 = new ExtensionEntity();
                extension12.setName("tif");
                extension12.setTypeSupply(typeSupply7);
                extensionService.createExtension(extension12);

                ExtensionEntity extension13 = new ExtensionEntity();
                extension13.setName("img");
                extension13.setTypeSupply(typeSupply7);
                extensionService.createExtension(extension13);

                TypeSupplyEntity typeSupply6 = new TypeSupplyEntity();
                typeSupply6.setName("Límites y Fronteras");
                typeSupply6.setIsMetadataRequired(false);
                typeSupply6.setIsModelRequired(false);
                typeSupply6.setCreatedAt(new Date());
                typeSupply6.setProvider(providerIgac);
                typeSupply6.setProviderProfile(profileCartographic);
                typeSupply6.setActive(true);
                typeSupply6 = typeSupplyService.createTypeSupply(typeSupply6);

                ExtensionEntity extension10 = new ExtensionEntity();
                extension10.setName("shp");
                extension10.setTypeSupply(typeSupply6);
                extensionService.createExtension(extension10);

                ExtensionEntity extension11 = new ExtensionEntity();
                extension11.setName("gpkg");
                extension11.setTypeSupply(typeSupply6);
                extensionService.createExtension(extension11);

                TypeSupplyEntity typeSupply8 = new TypeSupplyEntity();
                typeSupply8.setName("Datos Geodésicos");
                typeSupply8.setIsMetadataRequired(false);
                typeSupply8.setIsModelRequired(false);
                typeSupply8.setCreatedAt(new Date());
                typeSupply8.setProvider(providerIgac);
                typeSupply8.setProviderProfile(profileCartographic);
                typeSupply8.setActive(true);
                typeSupply8 = typeSupplyService.createTypeSupply(typeSupply8);

                ExtensionEntity extension14 = new ExtensionEntity();
                extension14.setName("shp");
                extension14.setTypeSupply(typeSupply8);
                extensionService.createExtension(extension14);

                ExtensionEntity extension15 = new ExtensionEntity();
                extension15.setName("gpkg");
                extension15.setTypeSupply(typeSupply8);
                extensionService.createExtension(extension15);

                TypeSupplyEntity typeSupply9 = new TypeSupplyEntity();
                typeSupply9.setName("Áreas Homogéneas de Tierra");
                typeSupply9.setIsMetadataRequired(false);
                typeSupply9.setIsModelRequired(false);
                typeSupply9.setCreatedAt(new Date());
                typeSupply9.setProvider(providerIgac);
                typeSupply9.setProviderProfile(profileAgrological);
                typeSupply9.setActive(true);
                typeSupply9 = typeSupplyService.createTypeSupply(typeSupply9);

                ExtensionEntity extension16 = new ExtensionEntity();
                extension16.setName("gdb");
                extension16.setTypeSupply(typeSupply9);
                extensionService.createExtension(extension16);

                ExtensionEntity extension17 = new ExtensionEntity();
                extension17.setName("mdb");
                extension17.setTypeSupply(typeSupply9);
                extensionService.createExtension(extension17);

                ExtensionEntity extension28 = new ExtensionEntity();
                extension28.setName("pdf");
                extension28.setTypeSupply(typeSupply9);
                extensionService.createExtension(extension28);

                ExtensionEntity extension29 = new ExtensionEntity();
                extension29.setName("gpkg");
                extension29.setTypeSupply(typeSupply9);
                extensionService.createExtension(extension29);

                TypeSupplyEntity typeSupply10 = new TypeSupplyEntity();
                typeSupply10.setName("Uso y Cobertura de la Tierra");
                typeSupply10.setIsMetadataRequired(false);
                typeSupply10.setIsModelRequired(false);
                typeSupply10.setCreatedAt(new Date());
                typeSupply10.setProvider(providerIgac);
                typeSupply10.setProviderProfile(profileAgrological);
                typeSupply10.setActive(true);
                typeSupply10 = typeSupplyService.createTypeSupply(typeSupply10);

                ExtensionEntity extension18 = new ExtensionEntity();
                extension18.setName("shp");
                extension18.setTypeSupply(typeSupply10);
                extensionService.createExtension(extension18);

                ExtensionEntity extension19 = new ExtensionEntity();
                extension19.setName("gpkg");
                extension19.setTypeSupply(typeSupply10);
                extensionService.createExtension(extension19);

                // provider SNR
                providerService.setValueSequence((long) 7);

                ProviderEntity providerSNR = new ProviderEntity();
                providerSNR.setName("SUPERINTENDENCIA DE NOTARIADO Y REGISTRO");
                providerSNR.setAlias("SNR");
                providerSNR.setTaxIdentificationNumber("899999007");
                providerSNR.setCreatedAt(new Date());
                providerSNR.setProviderCategory(providerCategoryRegistry);
                providerSNR.setActive(true);
                providerSNR = providerService.createProvider(providerSNR);

                ProviderProfileEntity profileRegistry = new ProviderProfileEntity();
                profileRegistry.setName("REGISTRO");
                profileRegistry.setProvider(providerSNR);
                profileRegistry = providerProfileService.createProviderProfile(profileRegistry);

                typeSupplyService.setValueSequence((long) 11);
                TypeSupplyEntity typeSupply12 = new TypeSupplyEntity();
                typeSupply12.setName("Datos registrales en modelo de insumos");
                typeSupply12.setIsMetadataRequired(false);
                typeSupply12.setIsModelRequired(true);
                typeSupply12.setCreatedAt(new Date());
                typeSupply12.setProvider(providerSNR);
                typeSupply12.setProviderProfile(profileRegistry);
                typeSupply12.setActive(true);
                typeSupply12 = typeSupplyService.createTypeSupply(typeSupply12);

                ExtensionEntity extension22 = new ExtensionEntity();
                extension22.setName("xtf");
                extension22.setTypeSupply(typeSupply12);
                extensionService.createExtension(extension22);

                // provider ANT

                ProviderEntity providerANT = new ProviderEntity();
                providerANT.setName("AGENCIA NACIONAL DE TIERRAS");
                providerANT.setAlias("ANT");
                providerANT.setTaxIdentificationNumber("9009489538");
                providerANT.setCreatedAt(new Date());
                providerANT.setProviderCategory(providerCategoryLand);
                providerANT.setActive(true);
                providerANT = providerService.createProvider(providerANT);

                ProviderProfileEntity profileANT = new ProviderProfileEntity();
                profileANT.setName("ANT");
                profileANT.setProvider(providerANT);
                providerProfileService.createProviderProfile(profileANT);

                // provider DANE

                ProviderEntity providerDANE = new ProviderEntity();
                providerDANE.setName("DEPARTAMENTO ADMINISTRATIVO NACIONAL DE ESTADÍSTICA");
                providerDANE.setAlias("DANE");
                providerDANE.setTaxIdentificationNumber("899999027");
                providerDANE.setCreatedAt(new Date());
                providerDANE.setProviderCategory(providerCategoryGeneral);
                providerDANE.setActive(true);
                providerDANE = providerService.createProvider(providerDANE);

                log.info("The domains 'providers' have been loaded!");
            } catch (Exception e) {
                log.error("Failed to load 'providers' domains");
                System.out.println("error " + e.getMessage());
            }

        }
    }

    public void initRequestsStates() {
        Long countRequestsStates = requestStateService.getCount();
        if (countRequestsStates == 0) {

            try {

                RequestStateEntity stateRequested = new RequestStateEntity();
                stateRequested.setId(RequestStateBusiness.REQUEST_STATE_REQUESTED);
                stateRequested.setName("SOLICITADA");
                requestStateService.createRequestState(stateRequested);

                RequestStateEntity stateDelivered = new RequestStateEntity();
                stateDelivered.setId(RequestStateBusiness.REQUEST_STATE_DELIVERED);
                stateDelivered.setName("ENTREGADA");
                requestStateService.createRequestState(stateDelivered);

                RequestStateEntity stateCancelled = new RequestStateEntity();
                stateCancelled.setId(RequestStateBusiness.REQUEST_STATE_CANCELLED);
                stateCancelled.setName("CANCELADA");
                requestStateService.createRequestState(stateCancelled);

                log.info("The domains 'requests states' have been loaded!");
            } catch (Exception e) {
                log.error("Failed to load 'requests states' domains");
            }

        }
    }

    public void initSupplyRequestedStates() {
        Long countStates = supplyRequestedStateService.getCount();
        if (countStates == 0) {

            try {

                SupplyRequestedStateEntity stateAccepted = new SupplyRequestedStateEntity();
                stateAccepted.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_ACCEPTED);
                stateAccepted.setName("ACEPTADO");
                supplyRequestedStateService.createState(stateAccepted);

                SupplyRequestedStateEntity stateRejected = new SupplyRequestedStateEntity();
                stateRejected.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_REJECTED);
                stateRejected.setName("RECHAZADO");
                supplyRequestedStateService.createState(stateRejected);

                SupplyRequestedStateEntity stateValidating = new SupplyRequestedStateEntity();
                stateValidating.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_VALIDATING);
                stateValidating.setName("VALIDANDO");
                supplyRequestedStateService.createState(stateValidating);

                SupplyRequestedStateEntity statePending = new SupplyRequestedStateEntity();
                statePending.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_PENDING);
                statePending.setName("PENDIENTE");
                supplyRequestedStateService.createState(statePending);

                SupplyRequestedStateEntity stateUndelivered = new SupplyRequestedStateEntity();
                stateUndelivered.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_UNDELIVERED);
                stateUndelivered.setName("NO ENTREGADO");
                supplyRequestedStateService.createState(stateUndelivered);

                SupplyRequestedStateEntity statetPendingReview = new SupplyRequestedStateEntity();
                statetPendingReview.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_PENDING_REVIEW);
                statetPendingReview.setName("PENDIENTE DE REVISIÓN");
                supplyRequestedStateService.createState(statetPendingReview);

                SupplyRequestedStateEntity statetSettingReview = new SupplyRequestedStateEntity();
                statetSettingReview.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_SETTING_REVIEW);
                statetSettingReview.setName("CONFIGURANDO REVISIÓN");
                supplyRequestedStateService.createState(statetSettingReview);

                SupplyRequestedStateEntity statetInReview = new SupplyRequestedStateEntity();
                statetInReview.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_IN_REVIEW);
                statetInReview.setName("EN REVISIÓN");
                supplyRequestedStateService.createState(statetInReview);

                SupplyRequestedStateEntity statetClosingReview = new SupplyRequestedStateEntity();
                statetClosingReview.setId(SupplyRequestedStateBusiness.SUPPLY_REQUESTED_STATE_CLOSING_REVIEW);
                statetClosingReview.setName("CERRANDO REVISIÓN");
                supplyRequestedStateService.createState(statetClosingReview);

                log.info("The domains 'supply requested states' have been loaded!");
            } catch (Exception e) {
                log.error("Failed to load 'supply requested states' domains");
            }

        }
    }

    public void initRoles() {
        Long countRoles = roleService.getCount();
        if (countRoles == 0) {

            try {

                RoleEntity roleDirector = new RoleEntity();
                roleDirector.setId(RoleBusiness.SUB_ROLE_DIRECTOR_PROVIDER);
                roleDirector.setName("ADMINISTRADOR PROVEEDOR");
                roleService.createRole(roleDirector);

                RoleEntity roleDelegate = new RoleEntity();
                roleDelegate.setId(RoleBusiness.SUB_ROLE_DELEGATE_PROVIDER);
                roleDelegate.setName("REVISOR");
                roleService.createRole(roleDelegate);

                log.info("The domains 'roles' have been loaded!");
            } catch (Exception e) {
                log.error("Failed to load 'roles' domains");
            }

        }
    }

    public void initPetitionStates() {
        Long countStates = petitionStateService.getCount();
        if (countStates == 0) {

            try {

                PetitionStateEntity statePending = new PetitionStateEntity();
                statePending.setId(PetitionStateBusiness.PETITION_STATE_PENDING);
                statePending.setName("PENDIENTE");
                petitionStateService.createPetitionState(statePending);

                PetitionStateEntity stateAccept = new PetitionStateEntity();
                stateAccept.setId(PetitionStateBusiness.PETITION_STATE_ACCEPT);
                stateAccept.setName("ACEPTADO");
                petitionStateService.createPetitionState(stateAccept);

                PetitionStateEntity stateReject = new PetitionStateEntity();
                stateReject.setId(PetitionStateBusiness.PETITION_STATE_REJECT);
                stateReject.setName("RECHAZADO");
                petitionStateService.createPetitionState(stateReject);

                log.info("The domains 'petitions states' have been loaded!");
            } catch (Exception e) {
                log.error("Failed to load 'petitions states' domains");
            }

        }
    }

}
