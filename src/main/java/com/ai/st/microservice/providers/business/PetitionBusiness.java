package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.PetitionDto;
import com.ai.st.microservice.providers.dto.PetitionStateDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.entities.PetitionEntity;
import com.ai.st.microservice.providers.entities.PetitionStateEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IPetitionService;
import com.ai.st.microservice.providers.services.IPetitionStateService;
import com.ai.st.microservice.providers.services.IProviderService;

@Component
public class PetitionBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private IPetitionService petitionService;

	@Autowired
	private IPetitionStateService petitionStateService;

	public PetitionDto createPetition(Long providerId, Long managerCode, String observations) throws BusinessException {

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		PetitionStateEntity statePending = petitionStateService
				.getPetitionStateById(PetitionStateBusiness.PETITION_STATE_PENDING);

		PetitionEntity petitionEntity = new PetitionEntity();
		petitionEntity.setCreatedAt(new Date());
		petitionEntity.setManagerCode(managerCode);
		petitionEntity.setObservations(observations);
		petitionEntity.setPetitionState(statePending);
		petitionEntity.setProvider(providerEntity);

		petitionEntity = petitionService.createPetition(petitionEntity);
		return entityParseDto(petitionEntity);
	}

	public List<PetitionDto> getPetitionsByProvider(Long providerId, List<Long> states) throws BusinessException {

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		List<PetitionStateEntity> statesEntity = new ArrayList<PetitionStateEntity>();
		for (Long stateId : states) {
			PetitionStateEntity stateEntity = petitionStateService.getPetitionStateById(stateId);
			if (stateEntity == null) {
				throw new BusinessException("El estado de la petición no existe.");
			}
			statesEntity.add(stateEntity);
		}

		List<PetitionEntity> petitionsEntityList = petitionService.getPetitionsByProviderAndStates(providerEntity,
				statesEntity);

		List<PetitionDto> petitionsDtoLis = new ArrayList<>();
		for (PetitionEntity petitionEntity : petitionsEntityList) {
			petitionsDtoLis.add(entityParseDto(petitionEntity));
		}

		return petitionsDtoLis;
	}

	public PetitionDto updatePetition(Long providerId, Long petitionId, Long stateId, String justification)
			throws BusinessException {

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		// verify petition exists
		PetitionEntity petitionEntity = petitionService.getPetitionById(petitionId);
		if (petitionEntity == null) {
			throw new BusinessException("La petición no existe.");
		}

		// verify petition belongs to provider
		if (!petitionEntity.getProvider().getId().equals(providerEntity.getId())) {
			throw new BusinessException("La petición no pertenece al proveedor.");
		}

		if (stateId != null) {
			// verify state exists
			PetitionStateEntity stateEntity = petitionStateService.getPetitionStateById(stateId);
			if (stateEntity == null) {
				throw new BusinessException("La estado de la petición no existe.");
			}
			petitionEntity.setPetitionState(stateEntity);

			if (justification != null) {
				petitionEntity.setJustification(justification);
			}
		}

		petitionEntity = petitionService.createPetition(petitionEntity);
		return entityParseDto(petitionEntity);
	}

	public List<PetitionDto> getPetitionsFromManager(Long providerId, Long managerCode) throws BusinessException {

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		List<PetitionEntity> petitionsEntityList = petitionService.getPetitionsByProviderAndManagerCode(providerEntity,
				managerCode);

		List<PetitionDto> petitionsDtoLis = new ArrayList<>();
		for (PetitionEntity petitionEntity : petitionsEntityList) {
			petitionsDtoLis.add(entityParseDto(petitionEntity));
		}

		return petitionsDtoLis;
	}

	public List<PetitionDto> getPetitionsByManagerCode(Long managerCode) throws BusinessException {

		List<PetitionEntity> petitionsEntityList = petitionService.getPetitionsByManagerCode(managerCode);

		List<PetitionDto> petitionsDtoLis = new ArrayList<>();
		for (PetitionEntity petitionEntity : petitionsEntityList) {
			petitionsDtoLis.add(entityParseDto(petitionEntity));
		}

		return petitionsDtoLis;
	}

	public PetitionDto entityParseDto(PetitionEntity petitionEntity) {

		PetitionDto petitionDto = new PetitionDto();

		petitionDto.setCreatedAt(petitionEntity.getCreatedAt());
		petitionDto.setId(petitionEntity.getId());
		petitionDto.setManagerCode(petitionEntity.getManagerCode());
		petitionDto.setObservations(petitionEntity.getObservations());
		petitionDto.setJustification(petitionEntity.getJustification());

		PetitionStateEntity petitionStateEntity = petitionEntity.getPetitionState();
		petitionDto.setPetitionState(new PetitionStateDto(petitionStateEntity.getId(), petitionStateEntity.getName()));

		ProviderEntity providerEntity = petitionEntity.getProvider();
		ProviderDto providerDto = new ProviderDto();
		providerDto.setId(providerEntity.getId());
		providerDto.setName(providerEntity.getName());
		providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
		providerDto.setCreatedAt(providerEntity.getCreatedAt());
		petitionDto.setProvider(providerDto);

		return petitionDto;
	}

}
