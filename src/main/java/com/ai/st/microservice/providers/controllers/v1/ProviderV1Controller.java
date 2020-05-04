package com.ai.st.microservice.providers.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.providers.business.ProviderBusiness;
import com.ai.st.microservice.providers.dto.CreateProviderDto;
import com.ai.st.microservice.providers.dto.CreateProviderProfileDto;
import com.ai.st.microservice.providers.dto.CreateTypeSupplyDto;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.ProviderAdministratorDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.dto.ProviderUserDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Providers", description = "Manage Providers", tags = { "Providers" })
@RestController
@RequestMapping("api/providers-supplies/v1/providers")
public class ProviderV1Controller {

	private final Logger log = LoggerFactory.getLogger(ProviderV1Controller.class);

	@Autowired
	private ProviderBusiness providerBusiness;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get providers")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get providers", response = ProviderDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<List<ProviderDto>> getProviders() {

		HttpStatus httpStatus = null;
		List<ProviderDto> listProviders = new ArrayList<ProviderDto>();

		try {

			listProviders = providerBusiness.getProviders();

			httpStatus = HttpStatus.OK;
		} catch (BusinessException e) {
			listProviders = null;
			log.error("Error ProviderV1Controller@getProviders#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			listProviders = null;
			log.error("Error ProviderV1Controller@getProviders#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(listProviders, httpStatus);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create provider")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Create provider", response = ProviderDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<ProviderDto> createProvider(@RequestBody CreateProviderDto createProviderDto) {

		HttpStatus httpStatus = null;
		ProviderDto responseProviderDto = null;

		try {

			// validation input data
			if (createProviderDto.getName().isEmpty()) {
				throw new InputValidationException("The provider name is required.");
			}
			if (createProviderDto.getTaxIdentificationNumber().isEmpty()) {
				throw new InputValidationException("The tax identification number is required.");
			}
			if (createProviderDto.getProviderCategoryId() == null) {
				throw new InputValidationException("The provider category is required.");
			}

			responseProviderDto = providerBusiness.createProvider(createProviderDto.getName(),
					createProviderDto.getTaxIdentificationNumber(), createProviderDto.getProviderCategoryId());

			httpStatus = HttpStatus.CREATED;
		} catch (InputValidationException e) {
			log.error("Error ProviderV1Controller@createProvider#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@createProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@createProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseProviderDto, httpStatus);
	}

	@RequestMapping(value = "/{providerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get provider by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Get provider by id", response = ProviderDto.class),
			@ApiResponse(code = 404, message = "Provider does not exists.", response = ProviderDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<ProviderDto> getProviderById(
			@PathVariable(name = "providerId", required = true) Long providerId) {

		HttpStatus httpStatus = null;
		ProviderDto providerDto = null;

		try {
			providerDto = providerBusiness.getProviderById(providerId);
			httpStatus = (providerDto instanceof ProviderDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@getProviderById#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@getProviderById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(providerDto, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/types-supplies", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get types supplies by provider")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get types supplies by provider", response = ProviderDto.class),
			@ApiResponse(code = 404, message = "Provider not found.", response = ProviderDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> getTypeSuppliesByProvider(@PathVariable Long providerId) {

		HttpStatus httpStatus = null;
		List<TypeSupplyDto> listTypesSupplies = new ArrayList<TypeSupplyDto>();
		Object responseDto = null;

		try {

			listTypesSupplies = providerBusiness.getTypesSuppliesByProviderId(providerId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@getTypeSuppliesByProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@getTypeSuppliesByProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listTypesSupplies, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/requests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get requests by provider")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get requests by provider", response = ProviderDto.class),
			@ApiResponse(code = 404, message = "Provider not found.", response = ProviderDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> getRequestsByProvider(@PathVariable Long providerId,
			@RequestParam(required = false, name = "state") Long requestStateId) {

		HttpStatus httpStatus = null;
		List<RequestDto> listRequests = new ArrayList<RequestDto>();
		Object responseDto = null;

		try {

			listRequests = providerBusiness.getRequestsByProviderAndState(providerId, requestStateId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@getRequestsByProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@getRequestsByProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listRequests, httpStatus);

	}

	@RequestMapping(value = "/{providerId}/requests/closed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get requests closed by provider and user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get requests closed by provider and user", response = ProviderDto.class),
			@ApiResponse(code = 404, message = "Provider not found.", response = ProviderDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> getRequestsByProviderAndUserClosed(@PathVariable Long providerId,
			@RequestParam(required = false, name = "user") Long userCode) {

		HttpStatus httpStatus = null;
		List<RequestDto> listRequests = new ArrayList<RequestDto>();
		Object responseDto = null;

		try {

			listRequests = providerBusiness.getRequestsByProviderAndUserClosed(providerId, userCode);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@getRequestsByProviderAndUserClosed#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@getRequestsByProviderAndUserClosed#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listRequests, httpStatus);

	}

	@RequestMapping(value = "/{providerId}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get users by provider")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get users by provider", response = ProviderUserDto.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "Provider not found.", response = ProviderUserDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> getUsersByProvider(@PathVariable Long providerId,
			@RequestParam(name = "profiles", required = false) List<Long> profiles) {

		HttpStatus httpStatus = null;
		List<ProviderUserDto> listUsers = new ArrayList<ProviderUserDto>();
		Object responseDto = null;

		try {

			listUsers = providerBusiness.getUsersByProvider(providerId, profiles);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@getUsersByProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@getUsersByProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listUsers, httpStatus);

	}

	@RequestMapping(value = "/{providerId}/administrators", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get administrators by provider")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get administrators by provider", response = ProviderUserDto.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "Provider not found.", response = ProviderUserDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> getAdministratorsByProvider(@PathVariable Long providerId,
			@RequestParam(name = "roles", required = false) List<Long> roles) {

		HttpStatus httpStatus = null;
		List<ProviderAdministratorDto> listUsers = new ArrayList<ProviderAdministratorDto>();
		Object responseDto = null;

		try {

			listUsers = providerBusiness.getAdministratorsByProvider(providerId, roles);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@getAdministratorsByProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@getAdministratorsByProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listUsers, httpStatus);

	}

	@RequestMapping(value = "/{providerId}/profiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get profiles by provider")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get profiles by provider", response = ProviderProfileDto.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "Provider not found.", response = ErrorDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = ErrorDto.class) })
	@ResponseBody
	public ResponseEntity<Object> getProfilesByProvider(@PathVariable Long providerId) {

		HttpStatus httpStatus = null;
		List<ProviderProfileDto> listProfiles = new ArrayList<ProviderProfileDto>();
		Object responseDto = null;

		try {

			listProfiles = providerBusiness.getProfilesByProvider(providerId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@getProfilesByProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@getProfilesByProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listProfiles, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/profiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create provider profile")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Create provider profile", response = ProviderProfileDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> createProviderProfile(@PathVariable Long providerId,
			@RequestBody CreateProviderProfileDto createProviderProfileDto) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			// validation input data
			if (createProviderProfileDto.getName().isEmpty()) {
				throw new InputValidationException("The provider profile name is required.");
			}
			if (createProviderProfileDto.getDescription().isEmpty()) {
				throw new InputValidationException("The provider profile description is required.");
			}
			if (providerId == null) {
				throw new InputValidationException("The provider is required.");
			}

			responseDto = providerBusiness.createProviderProfile(createProviderProfileDto.getName(),
					createProviderProfileDto.getDescription(), providerId);

			httpStatus = HttpStatus.CREATED;
		} catch (InputValidationException e) {
			log.error("Error ProviderV1Controller@createProviderProfile#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@createProviderProfile#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@createProviderProfile#General ---> " + e.getMessage());
			responseDto = new ErrorDto(e.getMessage(), 3);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/profiles/{profileId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update provider profile")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update provider profile", response = ProviderProfileDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> updateProviderProfile(@PathVariable Long providerId, @PathVariable Long profileId,
			@RequestBody CreateProviderProfileDto updateProviderProfileDto) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			// validation input data
			if (updateProviderProfileDto.getName().isEmpty()) {
				throw new InputValidationException("The provider profile name is required.");
			}
			if (updateProviderProfileDto.getDescription().isEmpty()) {
				throw new InputValidationException("The provider profile description is required.");
			}

			responseDto = providerBusiness.updateProviderProfile(updateProviderProfileDto.getName(),
					updateProviderProfileDto.getDescription(), providerId, profileId);

			httpStatus = HttpStatus.OK;
		} catch (InputValidationException e) {
			log.error("Error ProviderV1Controller@updateProviderProfile#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@updateProviderProfile#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@updateProviderProfile#General ---> " + e.getMessage());
			responseDto = new ErrorDto(e.getMessage(), 3);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/profiles/{profileId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete provider profile")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Delete provider profile"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> deleteProviderProfile(@PathVariable Long providerId, @PathVariable Long profileId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			providerBusiness.deleteProviderProfile(providerId, profileId);

			httpStatus = HttpStatus.NO_CONTENT;
		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@deleteProviderProfile#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@deleteProviderProfile#General ---> " + e.getMessage());
			responseDto = new ErrorDto(e.getMessage(), 3);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/type-supplies", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create type supply")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Create type supply", response = TypeSupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> createTypeSupply(@PathVariable Long providerId,
			@RequestBody CreateTypeSupplyDto createTypeSupplyDto) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			// validation input data
			if (createTypeSupplyDto.getName().isEmpty()) {
				throw new InputValidationException("The provider profile name is required.");
			}
			if (createTypeSupplyDto.getDescription().isEmpty()) {
				throw new InputValidationException("The provider profile description is required.");
			}
			if (providerId == null) {
				throw new InputValidationException("The provider is required.");
			}
			if (createTypeSupplyDto.getProviderProfileId() == null) {
				throw new InputValidationException("The provider profile is required.");
			}

			responseDto = providerBusiness.createTypeSupply(createTypeSupplyDto.getName(),
					createTypeSupplyDto.getDescription(), createTypeSupplyDto.getMetadataRequired(), providerId,
					createTypeSupplyDto.getProviderProfileId(), createTypeSupplyDto.getModelRequired(),
					createTypeSupplyDto.getExtensions());

			httpStatus = HttpStatus.CREATED;
		} catch (InputValidationException e) {
			log.error("Error ProviderV1Controller@createTypeSupply#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@createTypeSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@createTypeSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/type-supplies/{typeSupplyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update type supply")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Update type supply", response = TypeSupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> updateTypeSupply(@PathVariable Long providerId, @PathVariable Long typeSupplyId,
			@RequestBody CreateTypeSupplyDto createTypeSupplyDto) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			// validation input data
			if (createTypeSupplyDto.getName().isEmpty()) {
				throw new InputValidationException("The provider profile name is required.");
			}
			if (createTypeSupplyDto.getDescription().isEmpty()) {
				throw new InputValidationException("The provider profile description is required.");
			}
			if (providerId == null) {
				throw new InputValidationException("The provider is required.");
			}
			if (createTypeSupplyDto.getProviderProfileId() == null) {
				throw new InputValidationException("The provider profile is required.");
			}

			responseDto = providerBusiness.updateTypeSupply(createTypeSupplyDto.getName(),
					createTypeSupplyDto.getDescription(), createTypeSupplyDto.getMetadataRequired(), providerId,
					createTypeSupplyDto.getProviderProfileId(), createTypeSupplyDto.getModelRequired(),
					createTypeSupplyDto.getExtensions(), typeSupplyId);

			httpStatus = HttpStatus.OK;
		} catch (InputValidationException e) {
			log.error("Error ProviderV1Controller@updateTypeSupply#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@updateTypeSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@updateTypeSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "/{providerId}/type-supplies/{typeSupplyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete type supply")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Delete type supply", response = TypeSupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> deleteTypeSupply(@PathVariable Long providerId, @PathVariable Long typeSupplyId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			providerBusiness.deleteTypeSupply(providerId, typeSupplyId);
			httpStatus = HttpStatus.NO_CONTENT;

		} catch (BusinessException e) {
			log.error("Error ProviderV1Controller@deleteTypeSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderV1Controller@deleteTypeSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}
}
