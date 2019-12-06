package com.ai.st.microservice.providers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.business.RequestStateBusiness;
import com.ai.st.microservice.providers.entities.ExtensionEntity;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.ProviderProfileEntity;
import com.ai.st.microservice.providers.entities.ProviderUserEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.services.IExtensionService;
import com.ai.st.microservice.providers.services.IProviderCategoryService;
import com.ai.st.microservice.providers.services.IProviderProfileService;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IProviderUserService;
import com.ai.st.microservice.providers.services.IRequestStateService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@Component
public class StMicroserviceProvidersApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(StMicroserviceProvidersApplicationStartup.class);

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

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("ST - Loading Domains ... ");
		this.initProvidersCategories();
		this.initProviders();
		this.initRequestsStates();
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

				TypeSupplyEntity typeSupply1 = new TypeSupplyEntity();
				typeSupply1.setName("Fichas Prediales");
				typeSupply1.setIsMetadataRequired(false);
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

				TypeSupplyEntity typeSupply10 = new TypeSupplyEntity();
				typeSupply10.setName("Uso y Cobertura de la Tierra");
				typeSupply10.setIsMetadataRequired(false);
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

				TypeSupplyEntity typeSupply11 = new TypeSupplyEntity();
				typeSupply11.setName("Disponibilidad de Agua");
				typeSupply11.setIsMetadataRequired(false);
				typeSupply11.setCreatedAt(new Date());
				typeSupply11.setProvider(providerIgac);
				typeSupply11.setProviderProfile(profileAgrological);
				typeSupply11 = typeSupplyService.createTypeSupply(typeSupply11);

				ExtensionEntity extension20 = new ExtensionEntity();
				extension20.setName("shp");
				extension20.setTypeSupply(typeSupply11);
				extensionService.createExtension(extension20);

				ExtensionEntity extension21 = new ExtensionEntity();
				extension21.setName("gpkg");
				extension21.setTypeSupply(typeSupply11);
				extensionService.createExtension(extension21);
				
				TypeSupplyEntity typeSupply13 = new TypeSupplyEntity();
				typeSupply13.setName("POT-Documentos Plan de ordenamiento territorial");
				typeSupply13.setIsMetadataRequired(false);
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
				providerSNR.setName("SUPERINTENDENCIA DE NOTARIADO Y REGISTRO");
				providerSNR.setTaxIdentificationNumber("0001-8");
				providerSNR.setCreatedAt(new Date());
				providerSNR.setProviderCategory(providerCategoryRegistry);
				providerSNR = providerService.createProvider(providerSNR);

				ProviderProfileEntity profileRegistry = new ProviderProfileEntity();
				profileRegistry.setName("REGISTRO");
				profileRegistry.setProvider(providerSNR);
				providerProfileService.createProviderProfile(profileRegistry);
				
				TypeSupplyEntity typeSupply12 = new TypeSupplyEntity();
				typeSupply12.setName("Datos registrales en modelo de insumos");
				typeSupply12.setIsMetadataRequired(false);
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

}
