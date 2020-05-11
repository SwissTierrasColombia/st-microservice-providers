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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.providers.business.ProviderBusiness;
import com.ai.st.microservice.providers.business.ProviderUserBusiness;
import com.ai.st.microservice.providers.dto.AddUserToProviderDto;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.ProviderAdministratorDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderUserDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Users", description = "Manage Users", tags = { "Providers Users" })
@RestController
@RequestMapping("api/providers-supplies/v1/users")
public class ProviderUserV1Controller {

	private final Logger log = LoggerFactory.getLogger(ProviderUserV1Controller.class);

	@Autowired
	private ProviderUserBusiness providerUserBusiness;

	@Autowired
	private ProviderBusiness providerBusiness;

	@RequestMapping(value = "/{userCode}/providers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get provider by user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Get provider by user", response = ProviderDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> getProviderByUser(@PathVariable Long userCode) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {
			responseDto = providerUserBusiness.getProviderByUserCode(userCode);
			httpStatus = (responseDto instanceof ProviderDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		} catch (BusinessException e) {
			log.error("Error ProviderUserV1Controller@getProviderByUser#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderUserV1Controller@getProviderByUser#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Add user to provider")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Add user to provider", response = ProviderUserDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> addUserToProvider(@RequestBody AddUserToProviderDto requestAddUser) {

		HttpStatus httpStatus = null;
		List<ProviderUserDto> listUsers = new ArrayList<ProviderUserDto>();
		Object responseDto = null;

		try {

			// validation user code
			Long userCode = requestAddUser.getUserCode();
			if (userCode == null || userCode <= 0) {
				throw new InputValidationException("El código de usuario es inválido.");
			}

			// validation provider id
			Long providerId = requestAddUser.getProviderId();
			if (providerId == null || providerId <= 0) {
				throw new InputValidationException("El proveedor es inválido.");
			}

			// validation profile id
			Long profileId = requestAddUser.getProfileId();
			if (profileId == null || profileId <= 0) {
				throw new InputValidationException("El perfil de proveedor es inválido.");
			}

			responseDto = providerBusiness.addUserToProvider(userCode, providerId, profileId);
			httpStatus = (responseDto == null) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.CREATED;

		} catch (InputValidationException e) {
			log.error("Error ProviderUserV1Controller@addUserToProvider#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error ProviderUserV1Controller@addUserToProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderUserV1Controller@addUserToProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listUsers, httpStatus);
	}

	@RequestMapping(value = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Remove user to provider")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Remove user to provider", response = ProviderUserDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> removeUserToProvider(@RequestBody AddUserToProviderDto requestAddUser) {

		HttpStatus httpStatus = null;
		List<ProviderUserDto> listUsers = new ArrayList<ProviderUserDto>();
		Object responseDto = null;

		try {

			// validation user code
			Long userCode = requestAddUser.getUserCode();
			if (userCode == null || userCode <= 0) {
				throw new InputValidationException("El código de usuario es inválido.");
			}

			// validation provider id
			Long providerId = requestAddUser.getProviderId();
			if (providerId == null || providerId <= 0) {
				throw new InputValidationException("El proveedor es inválido.");
			}

			// validation profile id
			Long profileId = requestAddUser.getProfileId();
			if (profileId == null || profileId <= 0) {
				throw new InputValidationException("El perfil de proveedor es inválido.");
			}

			responseDto = providerBusiness.removeUserToProvider(userCode, providerId, profileId);
			httpStatus = (responseDto == null) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;

		} catch (InputValidationException e) {
			log.error("Error ProviderUserV1Controller@removeUserToProvider#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto = new ErrorDto(e.getMessage(), 1);
		} catch (BusinessException e) {
			log.error("Error ProviderUserV1Controller@removeUserToProvider#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderUserV1Controller@removeUserToProvider#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
				: new ResponseEntity<>(listUsers, httpStatus);
	}

	@RequestMapping(value = "{userCode}/profiles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get profiles by user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get profiles by user", response = ProviderAdministratorDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getProfilesByUser(@PathVariable Long userCode) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {
			responseDto = providerUserBusiness.getProfilesByUser(userCode);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error ProviderUserV1Controller@getProfilesByUser#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error ProviderUserV1Controller@getProfilesByUser#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
