package com.ai.st.microservice.providers.controllers.v1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.providers.business.RequestBusiness;
import com.ai.st.microservice.providers.dto.CreateRequestDto;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.RequestEmitterDto;
import com.ai.st.microservice.providers.dto.TypeSupplyRequestedDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Requests", description = "Manage Requests", tags = { "Requests" })
@RestController
@RequestMapping("api/providers-supplies/v1/requests")
public class RequestV1Controller {

	private final Logger log = LoggerFactory.getLogger(RequestV1Controller.class);

	@Autowired
	private RequestBusiness requestBusiness;

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create request")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Create request", response = RequestDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> createRequest(@RequestBody CreateRequestDto createRequestDto) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			// validation provider
			Long providerId = createRequestDto.getProviderId();
			if (providerId == null || providerId <= 0) {
				throw new InputValidationException("El proveedor de insumo es requerido.");
			}

			// validation deadline
			String deadlineString = createRequestDto.getDeadline();
			Date deadline = null;
			if (deadlineString != null && !deadlineString.isEmpty()) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					deadline = sdf.parse(deadlineString);
				} catch (Exception e) {
					throw new InputValidationException("La fecha límite es inválida.");
				}
			} else {
				throw new InputValidationException("La fecha límite es requerida.");
			}

			// validation emitters
			List<RequestEmitterDto> emitters = createRequestDto.getEmitters();
			if (emitters.size() > 0) {
				for (RequestEmitterDto emitterDto : emitters) {
					if (emitterDto.getEmitterCode() == null || emitterDto.getEmitterCode() <= 0) {
						throw new InputValidationException("El código del emisor es inválido.");
					}
					if (emitterDto.getEmitterType() == null || emitterDto.getEmitterType().isEmpty()) {
						throw new InputValidationException("El tipo del emisor es requerido.");
					}
				}
			} else {
				throw new InputValidationException("La solicitud debe contener al menos un emisor.");
			}

			// validation supplies
			List<TypeSupplyRequestedDto> supplies = createRequestDto.getSupplies();
			if (supplies.size() > 0) {
				for (TypeSupplyRequestedDto supplyDto : supplies) {
					if (supplyDto.getTypeSupplyId() == null || supplyDto.getTypeSupplyId() <= 0) {
						throw new InputValidationException("El tipo de insumo es inválido.");
					}
				}
			} else {
				throw new InputValidationException(
						"La solicitud debe contener al menos un tipo de insumo a solicitar.");
			}

			responseDto = requestBusiness.createRequest(deadline, providerId, emitters, supplies);

			httpStatus = HttpStatus.CREATED;

		} catch (InputValidationException e) {
			log.error("Error RequestV1Controller@createRequest#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error RequestV1Controller@createRequest#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error RequestV1Controller@createRequest#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
