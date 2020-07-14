package com.ai.st.microservice.providers.controllers.v1;

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

import com.ai.st.microservice.providers.business.SupplyRequestedBusiness;
import com.ai.st.microservice.providers.business.SupplyRevisionBusiness;
import com.ai.st.microservice.providers.dto.CreateSupplyRevisionDto;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.SupplyRevisionDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Supplies Requested", tags = { "Supplies Requested" })
@RestController
@RequestMapping("api/providers-supplies/v1/supplies-requested")
public class SupplyRequestedV1Controller {

	private final Logger log = LoggerFactory.getLogger(SupplyRequestedV1Controller.class);

	@Autowired
	private SupplyRequestedBusiness supplyRequestedBusiness;

	@Autowired
	private SupplyRevisionBusiness supplyRevisionBusiness;

	@RequestMapping(value = "/{supplyRequestedId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get supply requested by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get supply requested", response = SupplyRequestedDto.class),
			@ApiResponse(code = 404, message = "Supply not found", response = ErrorDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<Object> getSupplyRequestedById(@PathVariable Long supplyRequestedId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = supplyRequestedBusiness.getSupplyRequestedById(supplyRequestedId);
			httpStatus = (responseDto instanceof SupplyRequestedDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		} catch (Exception e) {
			log.error("Error SupplyRequestedV1Controller@getSupplyRequestedById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{supplyRequestedId}/revision", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create revision")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Revision created", response = SupplyRevisionDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> createRevision(@PathVariable Long supplyRequestedId,
			@RequestBody CreateSupplyRevisionDto createRevisionDto) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			if (createRevisionDto.getDatabase() == null || createRevisionDto.getDatabase().isEmpty()) {
				throw new InputValidationException("El nombre de base de datos es requerido.");
			}

			if (createRevisionDto.getHostname() == null || createRevisionDto.getHostname().isEmpty()) {
				throw new InputValidationException("El nombre del servidor de base de datos es requerido.");
			}

			if (createRevisionDto.getPort() == null || createRevisionDto.getPort().isEmpty()) {
				throw new InputValidationException("El puerto de la base de datos es requerido.");
			}

			if (createRevisionDto.getSchema() == null || createRevisionDto.getSchema().isEmpty()) {
				throw new InputValidationException("El nombre del esquema de base de datos es requerido.");
			}

			if (createRevisionDto.getUsername() == null || createRevisionDto.getUsername().isEmpty()) {
				throw new InputValidationException("El nombre de usuario de base de datos es requerido.");
			}

			if (createRevisionDto.getPassword() == null || createRevisionDto.getPassword().isEmpty()) {
				throw new InputValidationException("La contraseña de la base de datos es requerida.");
			}

			if (createRevisionDto.getStartBy() == null || createRevisionDto.getStartBy() <= 0) {
				throw new InputValidationException("El usuario que inicia la revisión es requerido.");
			}

			responseDto = supplyRevisionBusiness.createSupplyRevision(supplyRequestedId,
					createRevisionDto.getDatabase(), createRevisionDto.getHostname(), createRevisionDto.getSchema(),
					createRevisionDto.getPort(), createRevisionDto.getUsername(), createRevisionDto.getPassword(),
					createRevisionDto.getStartBy());
			httpStatus = HttpStatus.CREATED;

		} catch (InputValidationException e) {
			log.error("Error SupplyRequestedV1Controller@getSupplyRequestedById#Validation ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		} catch (Exception e) {
			log.error("Error SupplyRequestedV1Controller@getSupplyRequestedById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{supplyRequestedId}/revision/{supplyRevisionId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Delete revision")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Revision deleted", response = SupplyRevisionDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> deleteRevision(@PathVariable Long supplyRequestedId, @PathVariable Long supplyRevisionId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			supplyRevisionBusiness.deleteRevision(supplyRequestedId, supplyRevisionId);
			responseDto = new ErrorDto("Revision borrada", 7);
			httpStatus = HttpStatus.NO_CONTENT;

		} catch (Exception e) {
			log.error("Error SupplyRequestedV1Controller@deleteRevision#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{supplyRequestedId}/revision", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get revision from supply requested")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get revision from supply requested", response = SupplyRevisionDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getRevisionFromSupply(@PathVariable Long supplyRequestedId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = supplyRevisionBusiness.getSupplyRevisionFromSupplyRequested(supplyRequestedId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error SupplyRequestedV1Controller@getRevisionFromSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error SupplyRequestedV1Controller@getRevisionFromSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
