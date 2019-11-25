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

import com.ai.st.microservice.providers.business.ProviderUserBusiness;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;

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

}
