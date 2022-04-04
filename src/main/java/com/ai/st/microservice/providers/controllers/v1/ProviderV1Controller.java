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

import com.ai.st.microservice.providers.business.PetitionBusiness;
import com.ai.st.microservice.providers.business.ProviderBusiness;
import com.ai.st.microservice.providers.business.SupplyRequestedBusiness;
import com.ai.st.microservice.providers.dto.CreatePetitionDto;
import com.ai.st.microservice.providers.dto.CreateProviderDto;
import com.ai.st.microservice.providers.dto.CreateProviderProfileDto;
import com.ai.st.microservice.providers.dto.CreateTypeSupplyDto;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.PetitionDto;
import com.ai.st.microservice.providers.dto.ProviderAdministratorDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.dto.ProviderUserDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.SupplyRequestedDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.dto.UpdatePetitionDto;
import com.ai.st.microservice.providers.dto.UpdateProviderDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Providers", tags = { "Providers" })
@RestController
@RequestMapping("api/providers-supplies/v1/providers")
public class ProviderV1Controller {

    private final Logger log = LoggerFactory.getLogger(ProviderV1Controller.class);

    private final ProviderBusiness providerBusiness;
    private final SupplyRequestedBusiness supplyRequestedBusiness;
    private final PetitionBusiness petitionBusiness;

    public ProviderV1Controller(ProviderBusiness providerBusiness, SupplyRequestedBusiness supplyRequestedBusiness,
            PetitionBusiness petitionBusiness) {
        this.providerBusiness = providerBusiness;
        this.supplyRequestedBusiness = supplyRequestedBusiness;
        this.petitionBusiness = petitionBusiness;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get providers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get providers", response = ProviderDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getProviders(
            @RequestParam(name = "onlyActive", required = false, defaultValue = "false") Boolean onlyActive) {

        HttpStatus httpStatus;
        List<ProviderDto> listProviders;

        try {

            SCMTracing.setTransactionName("getProviders");

            listProviders = providerBusiness.getProviders(onlyActive);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            listProviders = null;
            log.error("Error ProviderV1Controller@getProviders#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            listProviders = null;
            log.error("Error ProviderV1Controller@getProviders#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(listProviders, httpStatus);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create provider")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Provider created", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> createProvider(@RequestBody CreateProviderDto createProviderDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("createProvider");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, createProviderDto.toString());

            // validation input data
            if (createProviderDto.getName() == null || createProviderDto.getName().isEmpty()) {
                throw new InputValidationException("El nombre del proveedor es requerido.");
            }
            if (createProviderDto.getTaxIdentificationNumber() == null
                    || createProviderDto.getTaxIdentificationNumber().isEmpty()) {
                throw new InputValidationException("El nit es requerido.");
            }
            if (createProviderDto.getProviderCategoryId() == null) {
                throw new InputValidationException("La categoría es requerida.");
            }

            responseDto = providerBusiness.createProvider(createProviderDto.getName(),
                    createProviderDto.getTaxIdentificationNumber(), createProviderDto.getProviderCategoryId(),
                    createProviderDto.getAlias());

            httpStatus = HttpStatus.CREATED;
        } catch (InputValidationException e) {
            log.error("Error ProviderV1Controller@createProvider#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@createProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@createProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update provider")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Provider updated", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> updateProvider(@RequestBody UpdateProviderDto updateProviderDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("updateProvider");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, updateProviderDto.toString());

            // validation input data
            if (updateProviderDto.getName() == null || updateProviderDto.getName().isEmpty()) {
                throw new InputValidationException("El nombre del proveedor es requerido.");
            }
            if (updateProviderDto.getTaxIdentificationNumber() == null
                    || updateProviderDto.getTaxIdentificationNumber().isEmpty()) {
                throw new InputValidationException("El nit es requerido.");
            }
            if (updateProviderDto.getProviderCategoryId() == null) {
                throw new InputValidationException("La categoría es requerida.");
            }
            if (updateProviderDto.getId() == null) {
                throw new InputValidationException("El ID de la categoría es requerido.");
            }

            responseDto = providerBusiness.updateProvider(updateProviderDto.getId(), updateProviderDto.getName(),
                    updateProviderDto.getTaxIdentificationNumber(), updateProviderDto.getProviderCategoryId(),
                    updateProviderDto.getAlias());

            httpStatus = HttpStatus.OK;
        } catch (InputValidationException e) {
            log.error("Error ProviderV1Controller@updateProvider#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@updateProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@updateProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "/{providerId}/enable", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enable provider")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Provider enabled", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> enableProvider(@PathVariable Long providerId) {

        HttpStatus httpStatus;
        ProviderDto responseProviderDto = null;

        try {

            SCMTracing.setTransactionName("enableProvider");

            responseProviderDto = providerBusiness.enableProvider(providerId);
            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@enableProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@enableProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseProviderDto, httpStatus);
    }

    @PutMapping(value = "/{providerId}/disable", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Disable provider")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Provider disabled", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> disableProvider(@PathVariable Long providerId) {

        HttpStatus httpStatus;
        ProviderDto responseProviderDto = null;

        try {

            SCMTracing.setTransactionName("disableProvider");

            responseProviderDto = providerBusiness.disableProvider(providerId);
            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@disableProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@disableProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseProviderDto, httpStatus);
    }

    @DeleteMapping(value = "/{providerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete provider")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Provider deleted"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> deleteProvider(@PathVariable Long providerId) {

        HttpStatus httpStatus;
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("deleteProvider");

            providerBusiness.deleteProvider(providerId);
            httpStatus = HttpStatus.NO_CONTENT;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@deleteProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@deleteProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/{providerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get provider by id")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get provider by id", response = ProviderDto.class),
            @ApiResponse(code = 404, message = "Provider does not exists.", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<ProviderDto> getProviderById(@PathVariable(name = "providerId") Long providerId) {

        HttpStatus httpStatus;
        ProviderDto providerDto = null;

        try {

            SCMTracing.setTransactionName("getProviderById");

            providerDto = providerBusiness.getProviderById(providerId);
            httpStatus = (providerDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getProviderById#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getProviderById#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(providerDto, httpStatus);
    }

    @GetMapping(value = "/{providerId}/types-supplies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get types supplies by provider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get types supplies by provider", response = ProviderDto.class),
            @ApiResponse(code = 404, message = "Provider not found.", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getTypeSuppliesByProvider(@PathVariable Long providerId,
            @RequestParam(name = "onlyActive", required = false, defaultValue = "false") Boolean onlyActive) {

        HttpStatus httpStatus;
        List<TypeSupplyDto> listTypesSupplies = new ArrayList<>();
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getTypeSuppliesByProvider");

            listTypesSupplies = providerBusiness.getTypesSuppliesByProviderId(providerId, onlyActive);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getTypeSuppliesByProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getTypeSuppliesByProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listTypesSupplies, httpStatus);
    }

    @GetMapping(value = "/{providerId}/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get requests by provider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get requests by provider", response = ProviderDto.class),
            @ApiResponse(code = 404, message = "Provider not found.", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getRequestsByProvider(@PathVariable Long providerId,
            @RequestParam(required = false, name = "state") Long requestStateId) {

        HttpStatus httpStatus;
        List<RequestDto> listRequests = new ArrayList<>();
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getRequestsByProvider");

            listRequests = providerBusiness.getRequestsByProviderAndState(providerId, requestStateId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getRequestsByProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getRequestsByProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listRequests, httpStatus);
    }

    @GetMapping(value = "/{providerId}/requests/closed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get requests closed by provider and user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get requests closed by provider and user", response = ProviderDto.class),
            @ApiResponse(code = 404, message = "Provider not found.", response = ProviderDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getRequestsByProviderAndUserClosed(@PathVariable Long providerId,
            @RequestParam(required = false, name = "user") Long userCode) {

        HttpStatus httpStatus;
        List<RequestDto> listRequests = new ArrayList<>();
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getRequestsByProviderAndUserClosed");

            listRequests = providerBusiness.getRequestsByProviderAndUserClosed(providerId, userCode);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getRequestsByProviderAndUserClosed#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getRequestsByProviderAndUserClosed#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listRequests, httpStatus);

    }

    @GetMapping(value = "/{providerId}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users by provider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get users by provider", response = ProviderUserDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Provider not found.", response = ProviderUserDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getUsersByProvider(@PathVariable Long providerId,
            @RequestParam(name = "profiles", required = false) List<Long> profiles) {

        HttpStatus httpStatus;
        List<ProviderUserDto> listUsers = new ArrayList<>();
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getUsersByProvider");

            listUsers = providerBusiness.getUsersByProvider(providerId, profiles);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getUsersByProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getUsersByProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listUsers, httpStatus);
    }

    @GetMapping(value = "/{providerId}/administrators", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get administrators by provider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get administrators by provider", response = ProviderUserDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Provider not found.", response = ProviderUserDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getUsersAdministratorsByProvider(@PathVariable Long providerId,
            @RequestParam(name = "roles", required = false) List<Long> roles) {

        HttpStatus httpStatus;
        List<ProviderAdministratorDto> listUsers = new ArrayList<>();
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getUsersAdministratorsByProvider");

            listUsers = providerBusiness.getAdministratorsByProvider(providerId, roles);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getUsersAdministratorsByProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getUsersAdministratorsByProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listUsers, httpStatus);
    }

    @GetMapping(value = "/{providerId}/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get profiles by provider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get profiles by provider", response = ProviderProfileDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Provider not found.", response = ErrorDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = ErrorDto.class) })
    @ResponseBody
    public ResponseEntity<?> getAreasByProvider(@PathVariable Long providerId) {

        HttpStatus httpStatus;
        List<ProviderProfileDto> listProfiles = new ArrayList<>();
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("getAreasByProvider");

            listProfiles = providerBusiness.getProfilesByProvider(providerId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getAreasByProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getAreasByProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return (responseDto != null) ? new ResponseEntity<>(responseDto, httpStatus)
                : new ResponseEntity<>(listProfiles, httpStatus);
    }

    @PostMapping(value = "/{providerId}/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create provider profile")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create provider profile", response = ProviderProfileDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> createProviderArea(@PathVariable Long providerId,
            @RequestBody CreateProviderProfileDto createProviderProfileDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("createProviderArea");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, createProviderProfileDto.toString());

            // validation input data
            if (createProviderProfileDto.getName() == null || createProviderProfileDto.getName().isEmpty()) {
                throw new InputValidationException("El nombre del perfil es requerido.");
            }

            if (createProviderProfileDto.getDescription() == null
                    || createProviderProfileDto.getDescription().isEmpty()) {
                throw new InputValidationException("La descripción es requerida.");
            }

            if (providerId == null) {
                throw new InputValidationException("El proveedor es requerido.");
            }

            responseDto = providerBusiness.createProviderProfile(createProviderProfileDto.getName(),
                    createProviderProfileDto.getDescription(), providerId);

            httpStatus = HttpStatus.CREATED;
        } catch (InputValidationException e) {
            log.error("Error ProviderV1Controller@createProviderArea#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@createProviderArea#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@createProviderArea#General ---> " + e.getMessage());
            responseDto = new BasicResponseDto(e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "/{providerId}/profiles/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update provider profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update provider profile", response = ProviderProfileDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> updateProviderArea(@PathVariable Long providerId, @PathVariable Long profileId,
            @RequestBody CreateProviderProfileDto updateProviderProfileDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("updateProviderArea");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, updateProviderProfileDto.toString());

            // validation input data
            if (updateProviderProfileDto.getName() == null || updateProviderProfileDto.getName().isEmpty()) {
                throw new InputValidationException("El nombre del perfil es requerido.");
            }
            if (updateProviderProfileDto.getDescription() == null
                    || updateProviderProfileDto.getDescription().isEmpty()) {
                throw new InputValidationException("La descripción es requerida.");
            }

            responseDto = providerBusiness.updateProviderProfile(updateProviderProfileDto.getName(),
                    updateProviderProfileDto.getDescription(), providerId, profileId);

            httpStatus = HttpStatus.OK;
        } catch (InputValidationException e) {
            log.error("Error ProviderV1Controller@updateProviderArea#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@updateProviderArea#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@updateProviderArea#General ---> " + e.getMessage());
            responseDto = new BasicResponseDto(e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @DeleteMapping(value = "/{providerId}/profiles/{profileId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete provider area")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Area deleted"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> deleteProviderArea(@PathVariable Long providerId, @PathVariable Long profileId) {

        HttpStatus httpStatus;
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("deleteProviderArea");

            providerBusiness.deleteProviderProfile(providerId, profileId);

            httpStatus = HttpStatus.NO_CONTENT;
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@deleteProviderArea#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@deleteProviderArea#General ---> " + e.getMessage());
            responseDto = new BasicResponseDto(e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PostMapping(value = "/{providerId}/type-supplies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create type supply")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Create type supply", response = TypeSupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> createTypeSupply(@PathVariable Long providerId,
            @RequestBody CreateTypeSupplyDto createTypeSupplyDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("createTypeSupply");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, createTypeSupplyDto.toString());

            // validation input data
            if (createTypeSupplyDto.getName() == null || createTypeSupplyDto.getName().isEmpty()) {
                throw new InputValidationException("El nombre del insumo es requerido.");
            }
            if (createTypeSupplyDto.getDescription() == null || createTypeSupplyDto.getDescription().isEmpty()) {
                throw new InputValidationException("La descripción del insumo es requerida.");
            }
            if (providerId == null) {
                throw new InputValidationException("El proveedor es requerido.");
            }
            if (createTypeSupplyDto.getProviderProfileId() == null) {
                throw new InputValidationException("El perfil del proveedor es requerido.");
            }
            if (createTypeSupplyDto.getExtensions() == null || createTypeSupplyDto.getExtensions().size() == 0) {
                throw new InputValidationException("Las extensiones son requeridas.");
            }
            if (createTypeSupplyDto.getMetadataRequired() == null) {
                throw new InputValidationException("Se debe especificar si el insumo requiere metadata.");
            }
            if (createTypeSupplyDto.getModelRequired() == null) {
                throw new InputValidationException("Se debe especificar si el insumo requiere versión del modelo.");
            }

            responseDto = providerBusiness.createTypeSupply(createTypeSupplyDto.getName(),
                    createTypeSupplyDto.getDescription(), createTypeSupplyDto.getMetadataRequired(), providerId,
                    createTypeSupplyDto.getProviderProfileId(), createTypeSupplyDto.getModelRequired(),
                    createTypeSupplyDto.getExtensions());

            httpStatus = HttpStatus.CREATED;
        } catch (InputValidationException e) {
            log.error("Error ProviderV1Controller@createTypeSupply#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@createTypeSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@createTypeSupply#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "/{providerId}/type-supplies/{typeSupplyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update type supply")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Update type supply", response = TypeSupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> updateTypeSupply(@PathVariable Long providerId, @PathVariable Long typeSupplyId,
            @RequestBody CreateTypeSupplyDto createTypeSupplyDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("updateTypeSupply");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, createTypeSupplyDto.toString());

            // validation input data
            if (createTypeSupplyDto.getName() == null || createTypeSupplyDto.getName().isEmpty()) {
                throw new InputValidationException("El nombre del insumo es requerido.");
            }
            if (createTypeSupplyDto.getDescription() == null || createTypeSupplyDto.getDescription().isEmpty()) {
                throw new InputValidationException("La descripción del insumo es requerida.");
            }
            if (providerId == null) {
                throw new InputValidationException("El proveedor es requerido.");
            }
            if (createTypeSupplyDto.getProviderProfileId() == null) {
                throw new InputValidationException("El perfil del proveedor es requerido.");
            }
            if (createTypeSupplyDto.getExtensions() == null || createTypeSupplyDto.getExtensions().size() == 0) {
                throw new InputValidationException("Las extensiones son requeridas.");
            }
            if (createTypeSupplyDto.getMetadataRequired() == null) {
                throw new InputValidationException("Se debe especificar si el insumo requiere metadata.");
            }
            if (createTypeSupplyDto.getModelRequired() == null) {
                throw new InputValidationException("Se debe especificar si el insumo requiere versión del modelo.");
            }

            responseDto = providerBusiness.updateTypeSupply(createTypeSupplyDto.getName(),
                    createTypeSupplyDto.getDescription(), createTypeSupplyDto.getMetadataRequired(), providerId,
                    createTypeSupplyDto.getProviderProfileId(), createTypeSupplyDto.getModelRequired(),
                    createTypeSupplyDto.getExtensions(), typeSupplyId);

            httpStatus = HttpStatus.OK;
        } catch (InputValidationException e) {
            log.error("Error ProviderV1Controller@updateTypeSupply#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@updateTypeSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@updateTypeSupply#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @DeleteMapping(value = "/{providerId}/type-supplies/{typeSupplyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete type supply")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Type supply deleted", response = TypeSupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> deleteTypeSupply(@PathVariable Long providerId, @PathVariable Long typeSupplyId) {

        HttpStatus httpStatus;
        Object responseDto = null;

        try {

            SCMTracing.setTransactionName("deleteTypeSupply");

            providerBusiness.deleteTypeSupply(providerId, typeSupplyId);
            httpStatus = HttpStatus.NO_CONTENT;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@deleteTypeSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@deleteTypeSupply#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/{providerId}/supplies-requested", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get supplies requested by provider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get supplies requested by provider", response = SupplyRequestedDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getSuppliesRequestedForProvider(@PathVariable Long providerId,
            @RequestParam(name = "states") List<Long> states) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getSuppliesRequestedForProvider");

            responseDto = supplyRequestedBusiness.getSuppliesRequestedByProvider(providerId, states);
            httpStatus = HttpStatus.OK;
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getSuppliesRequestedForProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getSuppliesRequestedForProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PostMapping(value = "/{providerId}/petitions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create petition")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Petition created", response = PetitionDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> createPetition(@PathVariable Long providerId,
            @RequestBody CreatePetitionDto createPetitionDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("createPetition");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, createPetitionDto.toString());

            // validation input data
            if (createPetitionDto.getObservations() == null || createPetitionDto.getObservations().isEmpty()) {
                throw new InputValidationException("Las observaciones son requeridas.");
            }
            if (createPetitionDto.getManagerCode() == null || createPetitionDto.getManagerCode() <= 0) {
                throw new InputValidationException("El código del gestor es requerido.");
            }

            responseDto = petitionBusiness.createPetition(providerId, createPetitionDto.getManagerCode(),
                    createPetitionDto.getObservations());
            httpStatus = HttpStatus.CREATED;

        } catch (InputValidationException e) {
            log.error("Error ProviderV1Controller@createPetition#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@createPetition#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@createPetition#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/{providerId}/petitions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get petitions by provider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Petitions", response = PetitionDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getPetitionsByProvider(@PathVariable Long providerId,
            @RequestParam(name = "states") List<Long> states) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getPetitionsByProvider");

            responseDto = petitionBusiness.getPetitionsByProvider(providerId, states);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getPetitionsByProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getPetitionsByProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "/{providerId}/petitions/{petitionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update petition")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Petition updated", response = PetitionDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> updatePetition(@PathVariable Long providerId, @PathVariable Long petitionId,
            @RequestBody UpdatePetitionDto updatePetitionDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("updatePetition");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, updatePetitionDto.toString());

            responseDto = petitionBusiness.updatePetition(providerId, petitionId,
                    updatePetitionDto.getPetitionStateId(), updatePetitionDto.getJustitication());
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@updatePetition#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@updatePetition#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/{providerId}/petitions-manager/{managerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get petitions from manager")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Petitions from manager", response = PetitionDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getPetitionsFromManager(@PathVariable Long providerId, @PathVariable Long managerId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getPetitionsFromManager");

            responseDto = petitionBusiness.getPetitionsFromManager(providerId, managerId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getPetitionsFromManager#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getPetitionsFromManager#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/from-requested-manager/{managerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get petitions from manager")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Providers", response = PetitionDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getProvidersWhereManagerRequested(@PathVariable Long managerId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getProvidersWhereManagerRequested");

            responseDto = providerBusiness.getProvidersWhereManagerRequested(managerId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getProvidersWhereManagerRequested#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getProvidersWhereManagerRequested#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "/petitions-manager/{managerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get petitions from manager")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Petitions by manager", response = PetitionDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getPetitionsByManager(@PathVariable Long managerId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getPetitionsByManager");

            responseDto = petitionBusiness.getPetitionsByManagerCode(managerId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error ProviderV1Controller@getPetitionsByManager#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error ProviderV1Controller@getPetitionsByManager#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
