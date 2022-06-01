package com.ai.st.microservice.providers.controllers.v1;

import java.util.ArrayList;
import java.util.List;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.providers.services.tracing.SCMTracing;
import com.ai.st.microservice.providers.services.tracing.TracingKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.providers.business.ProviderAdministratorBusiness;
import com.ai.st.microservice.providers.business.ProviderBusiness;
import com.ai.st.microservice.providers.dto.AddAdministratorToProviderDto;
import com.ai.st.microservice.providers.dto.ProviderAdministratorDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderUserDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Administrators", tags = { "Providers Administrators" })
@RestController
@RequestMapping("api/providers-supplies/v1/administrators")
public class ProviderAdministratorV1Controller {

    private final Logger log = LoggerFactory.getLogger(ProviderAdministratorV1Controller.class);

    private final ProviderBusiness providerBusiness;
    private final ProviderAdministratorBusiness providerAdministratorBusiness;

    public ProviderAdministratorV1Controller(ProviderBusiness providerBusiness,
            ProviderAdministratorBusiness providerAdministratorBusiness) {
        this.providerBusiness = providerBusiness;
        this.providerAdministratorBusiness = providerAdministratorBusiness;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add user-administrator to provider")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add user to provider", response = ProviderUserDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> addUserAdministratorToProvider(@RequestBody AddAdministratorToProviderDto requestAddUser) {

        HttpStatus httpStatus;
        List<ProviderUserDto> listUsers = new ArrayList<>();
        Object responseDto;

        try {

            SCMTracing.setTransactionName("addUserAdministratorToProvider");
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

            // validation role id
            Long roleId = requestAddUser.getRoleId();
            if (roleId == null || roleId <= 0) {
                throw new InputValidationException("El rol de proveedor es inválido.");
            }

            responseDto = providerBusiness.addAdministratorToProvider(userCode, providerId, roleId);
            httpStatus = (responseDto == null) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.CREATED;

        } catch (InputValidationException e) {
            log.error("Error ProviderAdministratorV1Controller@addAdministratorToProvider#Validation ---> "
                    + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderAdministratorV1Controller@addUserAdministratorToProvider#Business ---> "
                    + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderAdministratorV1Controller@addUserAdministratorToProvider#General ---> "
                    + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listUsers, httpStatus);
    }

    @GetMapping(value = "{userCode}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get roles by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get roles by user", response = ProviderAdministratorDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getRolesByUser(@PathVariable Long userCode) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getRolesByUser");

            responseDto = providerAdministratorBusiness.getRolesByUser(userCode);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderAdministratorV1Controller@getRolesByUser#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderAdministratorV1Controller@getRolesByUser#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/{userCode}/providers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get provider by administrator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get provider by administrator", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getProviderByUserAdministrator(@PathVariable Long userCode) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getProviderByUserAdministrator");

            responseDto = providerAdministratorBusiness.getProviderByUserCode(userCode);
            httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        } catch (Exception e) {
            log.error("Error ProviderAdministratorV1Controller@getProviderByUserAdministrator#General ---> "
                    + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
