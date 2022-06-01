package com.ai.st.microservice.providers.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.providers.dto.*;
import com.ai.st.microservice.providers.services.tracing.SCMTracing;
import com.ai.st.microservice.providers.services.tracing.TracingKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.providers.business.ProviderBusiness;
import com.ai.st.microservice.providers.business.ProviderUserBusiness;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Users", tags = { "Providers Users" })
@RestController
@RequestMapping("api/providers-supplies/v1/users")
public class ProviderUserV1Controller {

    private final Logger log = LoggerFactory.getLogger(ProviderUserV1Controller.class);

    private final ProviderUserBusiness providerUserBusiness;
    private final ProviderBusiness providerBusiness;

    public ProviderUserV1Controller(ProviderUserBusiness providerUserBusiness, ProviderBusiness providerBusiness) {
        this.providerUserBusiness = providerUserBusiness;
        this.providerBusiness = providerBusiness;
    }

    @GetMapping(value = "/{userCode}/providers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get provider by user")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get provider by user", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getProviderByUser(@PathVariable Long userCode) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getProviderByUser");

            responseDto = providerUserBusiness.getProviderByUserCode(userCode);
            httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            log.error("Error ProviderUserV1Controller@getProviderByUser#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add user to provider")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add user to provider", response = ProviderUserDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> addUserToProvider(@RequestBody AddUserToProviderDto requestAddUser) {

        HttpStatus httpStatus;
        List<ProviderUserDto> listUsers = new ArrayList<>();
        Object responseDto;

        try {

            SCMTracing.setTransactionName("addUserToProvider");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, requestAddUser.toString());

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
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderUserV1Controller@addUserToProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderUserV1Controller@addUserToProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listUsers, httpStatus);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Remove user to provider")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Remove user to provider", response = ProviderUserDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> removeUserToProvider(@RequestBody AddUserToProviderDto requestAddUser) {

        HttpStatus httpStatus;
        List<ProviderUserDto> listUsers = new ArrayList<>();
        Object responseDto;

        try {

            SCMTracing.setTransactionName("removeUserToProvider");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, requestAddUser.toString());

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
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderUserV1Controller@removeUserToProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderUserV1Controller@removeUserToProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listUsers, httpStatus);
    }

    @GetMapping(value = "{userCode}/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get areas by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get areas by user", response = ProviderProfileDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getAreasByUser(@PathVariable Long userCode) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getAreasByUser");

            responseDto = providerUserBusiness.getProfilesByUser(userCode);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderUserV1Controller@getAreasByUser#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderUserV1Controller@getAreasByUser#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
