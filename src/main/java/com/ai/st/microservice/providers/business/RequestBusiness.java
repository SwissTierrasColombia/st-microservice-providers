package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.TypeSupplyRequestedDto;
import com.ai.st.microservice.providers.entities.EmitterEntity;
import com.ai.st.microservice.providers.entities.ProviderEntity;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.entities.RequestStateEntity;
import com.ai.st.microservice.providers.entities.SupplyRequestedEntity;
import com.ai.st.microservice.providers.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderService;
import com.ai.st.microservice.providers.services.IRequestService;
import com.ai.st.microservice.providers.services.IRequestStateService;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@Component
public class RequestBusiness {

	@Autowired
	private IProviderService providerService;

	@Autowired
	private ITypeSupplyService typeSupplyService;

	@Autowired
	private IRequestStateService requestStateService;

	@Autowired
	private IRequestService requestService;

	public RequestDto createRequest(Date deadline, Long providerId, Long emitterCode,
			List<TypeSupplyRequestedDto> supplies) throws BusinessException {

		// verify that the sea deadline greater than the current date
		if (!deadline.after(new Date())) {
			throw new BusinessException("La fecha límite debe ser mayor a la fecha actual.");
		}

		// verify provider exists
		ProviderEntity providerEntity = providerService.getProviderById(providerId);
		if (!(providerEntity instanceof ProviderEntity)) {
			throw new BusinessException("El proveedor de insumo no existe.");
		}

		// verify supplies
		for (TypeSupplyRequestedDto typeSupplyDto : supplies) {

			// verify the type of input exists
			TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyDto.getTypeSupplyId());
			if (!(typeSupplyEntity instanceof TypeSupplyEntity)) {
				throw new BusinessException("El tipo de insumo no existe.");
			}

			// verify if the type of input belongs to the entity
			if (typeSupplyEntity.getProvider().getId() != providerEntity.getId()) {
				throw new BusinessException("El tipo de insumo no pertenece al proveedor.");
			}

		}

		RequestStateEntity requestStateEntity = requestStateService
				.getRequestStateById(RequestStateBusiness.REQUEST_STATE_REQUESTED);

		RequestEntity requestEntity = new RequestEntity();
		requestEntity.setCreatedAt(new Date());
		requestEntity.setDeadline(deadline);
		requestEntity.setProvider(providerEntity);
		requestEntity.setRequestState(requestStateEntity);

		// supplies
		List<SupplyRequestedEntity> suppliesEntities = new ArrayList<SupplyRequestedEntity>();
		for (TypeSupplyRequestedDto typeSupplyDto : supplies) {
			TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyDto.getTypeSupplyId());
			SupplyRequestedEntity supplyEntity = new SupplyRequestedEntity();
			supplyEntity.setDescription(typeSupplyDto.getObservation());
			supplyEntity.setRequest(requestEntity);
			supplyEntity.setTypeSupply(typeSupplyEntity);
			suppliesEntities.add(supplyEntity);
		}
		requestEntity.setSupplies(suppliesEntities);

		// emitters
		List<EmitterEntity> emitterEntities = new ArrayList<EmitterEntity>();
		EmitterEntity emitterEntity = new EmitterEntity();
		emitterEntity.setCreatedAt(new Date());
		emitterEntity.setEmitterCode(emitterCode);
		emitterEntity.setRequest(requestEntity);
		emitterEntities.add(emitterEntity);
		requestEntity.setEmitters(emitterEntities);

		requestEntity = requestService.createRequest(requestEntity);

		RequestDto requestDto = new RequestDto();
		requestDto.setId(requestEntity.getId());

		return requestDto;
	}

}
