package com.ai.st.microservice.providers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.business.RequestStateBusiness;
import com.ai.st.microservice.providers.business.RoleBusiness;
import com.ai.st.microservice.providers.business.SupplyRequestedStateBusiness;
import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.entities.ProviderAdministratorEntity;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.entities.RoleEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedStateEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.services.IExtensionService;
import com.ai.st.microservice.providers.services.IProviderAdministratorService;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IProviderUserService;
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
	private IProviderUserService providerUserService;

	@Autowired
	private ISupplyRequestedStateService supplyRequestedStateService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IProviderAdministratorService providerAdministratorService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		log.info("ST - Loading Domains ... ");

		this.initProvidersCategories();
		this.initRequestsStates();
		this.initSupplyRequestedStates();
		this.initRoles();

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

				RoleEntity roleDirector = roleService.getRoleById(RoleBusiness.ROLE_DIRECTOR);

				RoleEntity roleDelegate = roleService.getRoleById(RoleBusiness.ROLE_DELEGATE);

				// provider IGAC

				ProviderEntity providerIgac = new ProviderEntity();
				providerIgac.setName("IGAC");
				providerIgac.setTaxIdentificationNumber("0001-1");
				providerIgac.setCreatedAt(new Date());
				providerIgac.setProviderCategory(providerCategoryCadastral);
				providerIgac = providerService.createProvider(providerIgac);

				ProviderProfileEntity profileCadastral = new ProviderProfileEntity();
				profileCadastral.setName("CATASTRAL");
				profileCadastral.setProvider(providerIgac);
				profileCadastral = providerProfileService.createProviderProfile(profileCadastral);

				ProviderProfileEntity profileCartographic = new ProviderProfileEntity();
				profileCartographic.setName("CARTOGRÁFICO");
				profileCartographic.setProvider(providerIgac);
				profileCartographic = providerProfileService.createProviderProfile(profileCartographic);

				ProviderProfileEntity profileAgrological = new ProviderProfileEntity();
				profileAgrological.setName("AGROLÓGICO");
				profileAgrological.setProvider(providerIgac);
				profileAgrological = providerProfileService.createProviderProfile(profileAgrological);

				ProviderUserEntity user1 = new ProviderUserEntity();
				user1.setCreatedAt(new Date());
				user1.setProvider(providerIgac);
				user1.setProviderProfile(profileCadastral);
				user1.setUserCode((long) 5);
				providerUserService.createProviderUser(user1);

				ProviderUserEntity user2 = new ProviderUserEntity();
				user2.setCreatedAt(new Date());
				user2.setProvider(providerIgac);
				user2.setProviderProfile(profileCartographic);
				user2.setUserCode((long) 5);
				providerUserService.createProviderUser(user2);

				ProviderUserEntity user3 = new ProviderUserEntity();
				user3.setCreatedAt(new Date());
				user3.setProvider(providerIgac);
				user3.setProviderProfile(profileCadastral);
				user3.setUserCode((long) 7);
				providerUserService.createProviderUser(user3);

				TypeSupplyEntity typeSupply1 = new TypeSupplyEntity();
				typeSupply1.setName("Fichas Prediales");
				typeSupply1.setIsMetadataRequired(false);
				typeSupply1.setIsModelRequired(false);
				typeSupply1.setCreatedAt(new Date());
				typeSupply1.setProvider(providerIgac);
				typeSupply1.setProviderProfile(profileCadastral);
				typeSupply1 = typeSupplyService.createTypeSupply(typeSupply1);

				ExtensionEntity extension1 = new ExtensionEntity();
				extension1.setName("pdf");
				extension1.setTypeSupply(typeSupply1);
				extensionService.createExtension(extension1);

				ExtensionEntity extension2 = new ExtensionEntity();
				extension2.setName("png");
				extension2.setTypeSupply(typeSupply1);
				extensionService.createExtension(extension2);

				ExtensionEntity extension3 = new ExtensionEntity();
				extension3.setName("jpg");
				extension3.setTypeSupply(typeSupply1);
				extensionService.createExtension(extension3);

				TypeSupplyEntity typeSupply2 = new TypeSupplyEntity();
				typeSupply2.setName("Datos catastrales en modelo de insumos");
				typeSupply2.setIsMetadataRequired(false);
				typeSupply2.setIsModelRequired(true);
				typeSupply2.setCreatedAt(new Date());
				typeSupply2.setProvider(providerIgac);
				typeSupply2.setProviderProfile(profileCadastral);
				typeSupply2 = typeSupplyService.createTypeSupply(typeSupply2);

				ExtensionEntity extension4 = new ExtensionEntity();
				extension4.setName("xtf");
				extension4.setTypeSupply(typeSupply2);
				extensionService.createExtension(extension4);

				TypeSupplyEntity typeSupply4 = new TypeSupplyEntity();
				typeSupply4.setName("Cartografía Básica Vectorial");
				typeSupply4.setIsMetadataRequired(false);
				typeSupply4.setIsModelRequired(false);
				typeSupply4.setCreatedAt(new Date());
				typeSupply4.setProvider(providerIgac);
				typeSupply4.setProviderProfile(profileCartographic);
				typeSupply4 = typeSupplyService.createTypeSupply(typeSupply4);

				ExtensionEntity extension6 = new ExtensionEntity();
				extension6.setName("shp");
				extension6.setTypeSupply(typeSupply4);
				extensionService.createExtension(extension6);

				ExtensionEntity extension7 = new ExtensionEntity();
				extension7.setName("gpkg");
				extension7.setTypeSupply(typeSupply4);
				extensionService.createExtension(extension7);

				TypeSupplyEntity typeSupply5 = new TypeSupplyEntity();
				typeSupply5.setName("Ortoimagen");
				typeSupply5.setIsMetadataRequired(false);
				typeSupply5.setIsModelRequired(false);
				typeSupply5.setCreatedAt(new Date());
				typeSupply5.setProvider(providerIgac);
				typeSupply5.setProviderProfile(profileCartographic);
				typeSupply5 = typeSupplyService.createTypeSupply(typeSupply5);

				ExtensionEntity extension8 = new ExtensionEntity();
				extension8.setName("tif");
				extension8.setTypeSupply(typeSupply5);
				extensionService.createExtension(extension8);

				ExtensionEntity extension9 = new ExtensionEntity();
				extension9.setName("img");
				extension9.setTypeSupply(typeSupply5);
				extensionService.createExtension(extension9);

				TypeSupplyEntity typeSupply6 = new TypeSupplyEntity();
				typeSupply6.setName("Límites y Fronteras");
				typeSupply6.setIsMetadataRequired(false);
				typeSupply6.setIsModelRequired(false);
				typeSupply6.setCreatedAt(new Date());
				typeSupply6.setProvider(providerIgac);
				typeSupply6.setProviderProfile(profileCartographic);
				typeSupply6 = typeSupplyService.createTypeSupply(typeSupply6);

				ExtensionEntity extension10 = new ExtensionEntity();
				extension10.setName("shp");
				extension10.setTypeSupply(typeSupply6);
				extensionService.createExtension(extension10);

				ExtensionEntity extension11 = new ExtensionEntity();
				extension11.setName("gpkg");
				extension11.setTypeSupply(typeSupply6);
				extensionService.createExtension(extension11);

				TypeSupplyEntity typeSupply7 = new TypeSupplyEntity();
				typeSupply7.setName("Modelo Digital de Elevación");
				typeSupply7.setIsMetadataRequired(false);
				typeSupply7.setIsModelRequired(false);
				typeSupply7.setCreatedAt(new Date());
				typeSupply7.setProvider(providerIgac);
				typeSupply7.setProviderProfile(profileCartographic);
				typeSupply7 = typeSupplyService.createTypeSupply(typeSupply7);

				ExtensionEntity extension12 = new ExtensionEntity();
				extension12.setName("tif");
				extension12.setTypeSupply(typeSupply7);
				extensionService.createExtension(extension12);

				ExtensionEntity extension13 = new ExtensionEntity();
				extension13.setName("img");
				extension13.setTypeSupply(typeSupply7);
				extensionService.createExtension(extension13);

				TypeSupplyEntity typeSupply8 = new TypeSupplyEntity();
				typeSupply8.setName("Datos Geodésicos");
				typeSupply8.setIsMetadataRequired(false);
				typeSupply8.setIsModelRequired(false);
				typeSupply8.setCreatedAt(new Date());
				typeSupply8.setProvider(providerIgac);
				typeSupply8.setProviderProfile(profileCartographic);
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
				typeSupply9 = typeSupplyService.createTypeSupply(typeSupply9);

				ExtensionEntity extension16 = new ExtensionEntity();
				extension16.setName("shp");
				extension16.setTypeSupply(typeSupply9);
				extensionService.createExtension(extension16);

				ExtensionEntity extension17 = new ExtensionEntity();
				extension17.setName("gpkg");
				extension17.setTypeSupply(typeSupply9);
				extensionService.createExtension(extension17);

				ExtensionEntity extension28 = new ExtensionEntity();
				extension28.setName("gdb");
				extension28.setTypeSupply(typeSupply9);
				extensionService.createExtension(extension28);

				ExtensionEntity extension29 = new ExtensionEntity();
				extension29.setName("mdb");
				extension29.setTypeSupply(typeSupply9);
				extensionService.createExtension(extension29);

				ExtensionEntity extension30 = new ExtensionEntity();
				extension30.setName("pdf");
				extension30.setTypeSupply(typeSupply9);
				extensionService.createExtension(extension30);

				TypeSupplyEntity typeSupply10 = new TypeSupplyEntity();
				typeSupply10.setName("Uso y Cobertura de la Tierra");
				typeSupply10.setIsMetadataRequired(false);
				typeSupply10.setIsModelRequired(false);
				typeSupply10.setCreatedAt(new Date());
				typeSupply10.setProvider(providerIgac);
				typeSupply10.setProviderProfile(profileAgrological);
				typeSupply10 = typeSupplyService.createTypeSupply(typeSupply10);

				ExtensionEntity extension18 = new ExtensionEntity();
				extension18.setName("shp");
				extension18.setTypeSupply(typeSupply10);
				extensionService.createExtension(extension18);

				ExtensionEntity extension19 = new ExtensionEntity();
				extension19.setName("gpkg");
				extension19.setTypeSupply(typeSupply10);
				extensionService.createExtension(extension19);

				TypeSupplyEntity typeSupply13 = new TypeSupplyEntity();
				typeSupply13.setName("POT-Documentos Plan de ordenamiento territorial");
				typeSupply13.setIsMetadataRequired(false);
				typeSupply13.setIsModelRequired(false);
				typeSupply13.setCreatedAt(new Date());
				typeSupply13.setProvider(providerIgac);
				typeSupply13.setProviderProfile(profileCadastral);
				typeSupply13 = typeSupplyService.createTypeSupply(typeSupply13);

				ExtensionEntity extension23 = new ExtensionEntity();
				extension23.setName("pdf");
				extension23.setTypeSupply(typeSupply13);
				extensionService.createExtension(extension23);

				TypeSupplyEntity typeSupply14 = new TypeSupplyEntity();
				typeSupply14.setName("POT-Planos Plan de ordenamiento territorial");
				typeSupply14.setIsMetadataRequired(false);
				typeSupply14.setIsModelRequired(false);
				typeSupply14.setCreatedAt(new Date());
				typeSupply14.setProvider(providerIgac);
				typeSupply14.setProviderProfile(profileCadastral);
				typeSupply14 = typeSupplyService.createTypeSupply(typeSupply14);

				ExtensionEntity extension24 = new ExtensionEntity();
				extension24.setName("pdf");
				extension24.setTypeSupply(typeSupply14);
				extensionService.createExtension(extension24);

				// provider UAECD

				ProviderEntity providerUAECD = new ProviderEntity();
				providerUAECD.setName("UAECD");
				providerUAECD.setTaxIdentificationNumber("0001-2");
				providerUAECD.setCreatedAt(new Date());
				providerUAECD.setProviderCategory(providerCategoryCadastral);
				providerService.createProvider(providerUAECD);

				ProviderEntity providerAMCO = new ProviderEntity();
				providerAMCO.setName("AMCO");
				providerAMCO.setTaxIdentificationNumber("0001-3");
				providerAMCO.setCreatedAt(new Date());
				providerAMCO.setProviderCategory(providerCategoryCadastral);
				providerService.createProvider(providerAMCO);

				ProviderEntity providerAntioquia = new ProviderEntity();
				providerAntioquia.setName("ANTIOQUIA");
				providerAntioquia.setTaxIdentificationNumber("0001-4");
				providerAntioquia.setCreatedAt(new Date());
				providerAntioquia.setProviderCategory(providerCategoryCadastral);
				providerService.createProvider(providerAntioquia);

				ProviderEntity providerMedellin = new ProviderEntity();
				providerMedellin.setName("MEDELLIN");
				providerMedellin.setTaxIdentificationNumber("0001-5");
				providerMedellin.setCreatedAt(new Date());
				providerMedellin.setProviderCategory(providerCategoryCadastral);
				providerService.createProvider(providerMedellin);

				ProviderEntity providerCali = new ProviderEntity();
				providerCali.setName("CALI");
				providerCali.setTaxIdentificationNumber("0001-6");
				providerCali.setCreatedAt(new Date());
				providerCali.setProviderCategory(providerCategoryCadastral);
				providerService.createProvider(providerCali);

				ProviderEntity providerBarranquilla = new ProviderEntity();
				providerBarranquilla.setName("BARRANQUILLA");
				providerBarranquilla.setTaxIdentificationNumber("0001-7");
				providerBarranquilla.setCreatedAt(new Date());
				providerBarranquilla.setProviderCategory(providerCategoryCadastral);
				providerService.createProvider(providerBarranquilla);

				// provider SNR

				ProviderEntity providerSNR = new ProviderEntity();
				providerSNR.setName("SNR");
				providerSNR.setTaxIdentificationNumber("0001-8");
				providerSNR.setCreatedAt(new Date());
				providerSNR.setProviderCategory(providerCategoryRegistry);
				providerSNR = providerService.createProvider(providerSNR);

				ProviderProfileEntity profileRegistry = new ProviderProfileEntity();
				profileRegistry.setName("REGISTRO");
				profileRegistry.setProvider(providerSNR);
				profileRegistry = providerProfileService.createProviderProfile(profileRegistry);

				ProviderUserEntity user4 = new ProviderUserEntity();
				user4.setCreatedAt(new Date());
				user4.setProvider(providerSNR);
				user4.setProviderProfile(profileRegistry);
				user4.setUserCode((long) 4);
				providerUserService.createProviderUser(user4);

				ProviderAdministratorEntity user5 = new ProviderAdministratorEntity();
				user5.setCreatedAt(new Date());
				user5.setProvider(providerSNR);
				user5.setUserCode((long) 9);
				user5.setRole(roleDirector);
				providerAdministratorService.createProviderAdministrator(user5);

				ProviderAdministratorEntity user6 = new ProviderAdministratorEntity();
				user6.setCreatedAt(new Date());
				user6.setProvider(providerSNR);
				user6.setUserCode((long) 10);
				user6.setRole(roleDelegate);
				providerAdministratorService.createProviderAdministrator(user6);

				TypeSupplyEntity typeSupply12 = new TypeSupplyEntity();
				typeSupply12.setName("Datos registrales en modelo de insumos");
				typeSupply12.setIsMetadataRequired(false);
				typeSupply12.setIsModelRequired(true);
				typeSupply12.setCreatedAt(new Date());
				typeSupply12.setProvider(providerSNR);
				typeSupply12.setProviderProfile(profileRegistry);
				typeSupply12 = typeSupplyService.createTypeSupply(typeSupply12);

				ExtensionEntity extension22 = new ExtensionEntity();
				extension22.setName("xtf");
				extension22.setTypeSupply(typeSupply12);
				extensionService.createExtension(extension22);

				// provider ANT

				ProviderEntity providerANT = new ProviderEntity();
				providerANT.setName("AGENCIA NACIONAL DE TIERRAS");
				providerANT.setTaxIdentificationNumber("0001-9");
				providerANT.setCreatedAt(new Date());
				providerANT.setProviderCategory(providerCategoryLand);
				providerANT = providerService.createProvider(providerANT);

				ProviderProfileEntity profileANT = new ProviderProfileEntity();
				profileANT.setName("ANT");
				profileANT.setProvider(providerANT);
				providerProfileService.createProviderProfile(profileANT);

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
				roleDirector.setId(RoleBusiness.ROLE_DIRECTOR);
				roleDirector.setName("ADMINISTRADOR PROVEEDOR");
				roleService.createRole(roleDirector);

				RoleEntity roleDelegate = new RoleEntity();
				roleDelegate.setId(RoleBusiness.ROLE_DELEGATE);
				roleDelegate.setName("REVISOR");
				roleService.createRole(roleDelegate);

				log.info("The domains 'roles' have been loaded!");
			} catch (Exception e) {
				log.error("Failed to load 'roles' domains");
			}

		}
	}

}
