package com.ai.st.microservice.providers.controllers.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.providers.business.TypeSupplyBusiness;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Types Supplies", description = "Types Supplies", tags = { "Types Supplies" })
@RestController
@RequestMapping("api/providers-supplies/v1/types-supplies")
public class TypeSupplyV1Controller {

	private final Logger log = LoggerFactory.getLogger(TypeSupplyV1Controller.class);

	@Autowired
	private TypeSupplyBusiness typeSupplyBusiness;

	@RequestMapping(value = "{typeSupplyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get type supply")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Get type supply", response = TypeSupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> getTypeSupplyById(@PathVariable Long typeSupplyId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = typeSupplyBusiness.getTypeSupplyById(typeSupplyId);
			httpStatus = (responseDto instanceof TypeSupplyDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		} catch (BusinessException e) {
			log.error("Error TypeSupplyV1Controller@getTypeSupplyById#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error TypeSupplyV1Controller@getTypeSupplyById#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

	@RequestMapping(value = "{typeSupplyId}/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Enable supply")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Supply enabled", response = TypeSupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> enableSupply(@PathVariable Long typeSupplyId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = typeSupplyBusiness.enableTypeSupply(typeSupplyId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error TypeSupplyV1Controller@enableSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error TypeSupplyV1Controller@enableSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}
	
	@RequestMapping(value = "{typeSupplyId}/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Disable supply")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Supply disabled", response = TypeSupplyDto.class),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<?> disableSupply(@PathVariable Long typeSupplyId) {

		HttpStatus httpStatus = null;
		Object responseDto = null;

		try {

			responseDto = typeSupplyBusiness.disableTypeSupply(typeSupplyId);
			httpStatus = HttpStatus.OK;

		} catch (BusinessException e) {
			log.error("Error TypeSupplyV1Controller@disableSupply#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
			responseDto = new ErrorDto(e.getMessage(), 2);
		} catch (Exception e) {
			log.error("Error TypeSupplyV1Controller@disableSupply#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseDto = new ErrorDto(e.getMessage(), 3);
		}

		return new ResponseEntity<>(responseDto, httpStatus);
	}

}
