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

import com.ai.st.microservice.providers.business.ProviderAdministratorBusiness;
import com.ai.st.microservice.providers.business.ProviderBusiness;
import com.ai.st.microservice.providers.dto.AddAdministratorToProviderDto;
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

@Api(value = "Manage Administrators", description = "Manage Users Administrators", tags = {
        "Providers Administrators" })
@RestController
@RequestMapping("api/providers-supplies/v1/administrators")
public class ProviderAdministratorV1Controller {

    private final Logger log = LoggerFactory.getLogger(ProviderAdministratorV1Controller.class);

    @Autowired
    private ProviderBusiness providerBusiness;

    @Autowired
    private ProviderAdministratorBusiness providerAdministratorBusiness;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add user-administrator to provider")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Add user to provider", response = ProviderUserDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> addAdministratorToProvider(
            @RequestBody AddAdministratorToProviderDto requestAddUser) {

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
            responseDto = new ErrorDto(e.getMessage(), 1);
        } catch (BusinessException e) {
            log.error("Error ProviderAdministratorV1Controller@addAdministratorToProvider#Business ---> "
                    + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error ProviderAdministratorV1Controller@addAdministratorToProvider#General ---> "
                    + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listUsers, httpStatus);
    }

    @RequestMapping(value = "{userCode}/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get roles by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get roles by user", response = ProviderAdministratorDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getRolesByUser(@PathVariable Long userCode) {

        HttpStatus httpStatus = null;
        Object responseDto = null;

        try {
            responseDto = providerAdministratorBusiness.getRolesByUser(userCode);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderAdministratorV1Controller@getRolesByUser#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error ProviderAdministratorV1Controller@getRolesByUser#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/{userCode}/providers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get provider by administrator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get provider by administrator", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getProviderByAdministrator(@PathVariable Long userCode) {

        HttpStatus httpStatus = null;
        Object responseDto = null;

        try {

            responseDto = providerAdministratorBusiness.getProviderByUserCode(userCode);
            httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        } catch (Exception e) {
            log.error("Error ProviderAdministratorV1Controller@getProviderByAdministrator#General ---> "
                    + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
