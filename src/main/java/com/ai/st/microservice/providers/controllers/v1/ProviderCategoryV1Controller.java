package com.ai.st.microservice.providers.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.providers.business.ProviderBusiness;
import com.ai.st.microservice.providers.business.ProviderCategoryBusiness;
import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Providers Categories", description = "Manage Providers Categories", tags = {
		"Providers Categories" })
@RestController
@RequestMapping("api/providers-supplies/v1/categories")
public class ProviderCategoryV1Controller {

	private final Logger log = LoggerFactory.getLogger(ProviderCategoryV1Controller.class);

	@Autowired
	private ProviderCategoryBusiness providerCategoryBusiness;

	@Autowired
	private ProviderBusiness providerBusiness;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get providers categories")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get providers categories", response = ProviderCategoryDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<List<ProviderCategoryDto>> getCategories() {

		HttpStatus httpStatus = null;
		List<ProviderCategoryDto> listCategories = new ArrayList<ProviderCategoryDto>();

		try {

			listCategories = providerCategoryBusiness.getCategories();

			httpStatus = HttpStatus.OK;
		} catch (BusinessException e) {
			listCategories = null;
			log.error("Error ProviderCategoryV1Controller@getCategories#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			listCategories = null;
			log.error("Error ProviderCategoryV1Controller@getCategories#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(listCategories, httpStatus);
	}

	@RequestMapping(value = "/{categoryId}/providers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get providers by category")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get providers by category", response = ProviderDto.class, responseContainer = "List"),
			@ApiResponse(code = 500, message = "Error Server", response = String.class) })
	@ResponseBody
	public ResponseEntity<List<ProviderDto>> getProvidersByCategory(
			@PathVariable(name = "categoryId", required = true) Long categoryId) {

		HttpStatus httpStatus = null;
		List<ProviderDto> listProviders = new ArrayList<ProviderDto>();

		try {

			listProviders = providerBusiness.getProvidersByCategoryId(categoryId);

			httpStatus = HttpStatus.OK;
		} catch (BusinessException e) {
			listProviders = null;
			log.error("Error ProviderCategoryV1Controller@getProvidersByCategory#Business ---> " + e.getMessage());
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			listProviders = null;
			log.error("Error ProviderCategoryV1Controller@getProvidersByCategory#General ---> " + e.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(listProviders, httpStatus);
	}

}
