package com.ai.st.microservice.providers.controllers.v1;

import java.util.List;

import com.ai.st.microservice.providers.services.tracing.SCMTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final ProviderCategoryBusiness providerCategoryBusiness;
    private final ProviderBusiness providerBusiness;

    public ProviderCategoryV1Controller(ProviderCategoryBusiness providerCategoryBusiness,
            ProviderBusiness providerBusiness) {
        this.providerCategoryBusiness = providerCategoryBusiness;
        this.providerBusiness = providerBusiness;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get providers categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get providers categories", response = ProviderCategoryDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<List<ProviderCategoryDto>> getCategories() {

        HttpStatus httpStatus;
        List<ProviderCategoryDto> listCategories;

        try {

            SCMTracing.setTransactionName("getCategories");

            listCategories = providerCategoryBusiness.getCategories();

            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            listCategories = null;
            log.error("Error ProviderCategoryV1Controller@getCategories#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            listCategories = null;
            log.error("Error ProviderCategoryV1Controller@getCategories#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(listCategories, httpStatus);
    }

    @GetMapping(value = "/{categoryId}/providers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get providers by category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get providers by category", response = ProviderDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<List<ProviderDto>> getProvidersByCategory(
            @PathVariable(name = "categoryId") Long categoryId) {

        HttpStatus httpStatus;
        List<ProviderDto> listProviders;

        try {

            SCMTracing.setTransactionName("getProvidersByCategory");

            listProviders = providerBusiness.getProvidersByCategoryId(categoryId);

            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            listProviders = null;
            log.error("Error ProviderCategoryV1Controller@getProvidersByCategory#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            listProviders = null;
            log.error("Error ProviderCategoryV1Controller@getProvidersByCategory#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(listProviders, httpStatus);
    }

}
