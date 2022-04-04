package com.ai.st.microservice.providers.controllers.v1;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ai.st.microservice.providers.business.RequestBusiness;
import com.ai.st.microservice.providers.business.RequestStateBusiness;
import com.ai.st.microservice.providers.dto.CreateRequestDto;
import com.ai.st.microservice.providers.dto.ErrorDto;
import com.ai.st.microservice.providers.dto.RequestDto;
import com.ai.st.microservice.providers.dto.RequestEmitterDto;
import com.ai.st.microservice.providers.dto.TypeSupplyRequestedDto;
import com.ai.st.microservice.providers.dto.UpdateSupplyRequestedDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.exceptions.InputValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Requests", description = "Manage Requests", tags = { "Requests" })
@RestController
@RequestMapping("api/providers-supplies/v1/requests")
public class RequestV1Controller {

    private final Logger log = LoggerFactory.getLogger(RequestV1Controller.class);

    @Autowired
    private RequestBusiness requestBusiness;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create request")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Create request", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> createRequest(@RequestBody CreateRequestDto createRequestDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            // validation provider
            Long providerId = createRequestDto.getProviderId();
            if (providerId == null || providerId <= 0) {
                throw new InputValidationException("El proveedor de insumo es requerido.");
            }

            // validation municipality code
            String municipalityCode = createRequestDto.getMunicipalityCode();
            if (municipalityCode.isEmpty()) {
                throw new InputValidationException("El código del municipio es requerido.");
            }

            // validation package
            String packageLabel = createRequestDto.getPackageLabel();
            if (packageLabel.isEmpty()) {
                throw new InputValidationException("El código del paquete es requerido.");
            }

            // validation deadline
            String deadlineString = createRequestDto.getDeadline();
            Date deadline = null;
            if (deadlineString != null && !deadlineString.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    deadline = sdf.parse(deadlineString);
                } catch (Exception e) {
                    throw new InputValidationException("La fecha límite es inválida.");
                }
            } else {
                throw new InputValidationException("La fecha límite es requerida.");
            }

            // validation emitters
            List<RequestEmitterDto> emitters = createRequestDto.getEmitters();
            if (emitters.size() > 0) {
                for (RequestEmitterDto emitterDto : emitters) {
                    if (emitterDto.getEmitterCode() == null || emitterDto.getEmitterCode() <= 0) {
                        throw new InputValidationException("El código del emisor es inválido.");
                    }
                    if (emitterDto.getEmitterType() == null || emitterDto.getEmitterType().isEmpty()) {
                        throw new InputValidationException("El tipo del emisor es requerido.");
                    }
                }
            } else {
                throw new InputValidationException("La solicitud debe contener al menos un emisor.");
            }

            // validation supplies
            List<TypeSupplyRequestedDto> supplies = createRequestDto.getSupplies();
            if (supplies.size() > 0) {
                for (TypeSupplyRequestedDto supplyDto : supplies) {
                    if (supplyDto.getTypeSupplyId() == null || supplyDto.getTypeSupplyId() <= 0) {
                        throw new InputValidationException("El tipo de insumo es inválido.");
                    }
                }
            } else {
                throw new InputValidationException(
                        "La solicitud debe contener al menos un tipo de insumo a solicitar.");
            }

            responseDto = requestBusiness.createRequest(deadline, providerId, municipalityCode, packageLabel, emitters,
                    supplies);

            httpStatus = HttpStatus.CREATED;

        } catch (InputValidationException e) {
            log.error("Error RequestV1Controller@createRequest#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new ErrorDto(e.getMessage(), 1);
        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@createRequest#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@createRequest#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/{requestId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get request by id")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Get request by id", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getRequestById(@PathVariable Long requestId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            responseDto = requestBusiness.getRequestById(requestId);
            httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@getRequestById#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@getRequestById#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);

    }

    @RequestMapping(value = "/{requestId}/supplies/{supplyRequestedId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update supply requested by request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update supply requested by request", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> updateSupplyRequested(@PathVariable Long requestId,
            @PathVariable Long supplyRequestedId, @RequestBody UpdateSupplyRequestedDto updateSupply) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            // validation delivered
            Long stateId = updateSupply.getSupplyRequestedStateId();
            if (stateId == null) {
                throw new InputValidationException("El estado del insumo cargado es requerido.");
            }

            responseDto = requestBusiness.updateSupplyRequested(requestId, supplyRequestedId, stateId,
                    updateSupply.getJustification(), updateSupply.getDeliveryBy(), updateSupply.getUrl(),
                    updateSupply.getObservations(), updateSupply.isValidated(), updateSupply.getLog(),
                    updateSupply.getExtraFile(), updateSupply.getErrors(), updateSupply.getFtp());
            httpStatus = HttpStatus.OK;

        } catch (InputValidationException e) {
            log.error("Error RequestV1Controller@updateSupplyRequested#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@updateSupplyRequested#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@updateSupplyRequested#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/{requestId}/delivered", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update state from request to delivered")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update state from request to delivered", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> updateRequestStateToDelivered(@PathVariable Long requestId,
            @RequestParam(name = "closed_by") Long userCode) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            responseDto = requestBusiness.updateRequestState(requestId, RequestStateBusiness.REQUEST_STATE_DELIVERED,
                    userCode);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@updateRequestStateToDelivered#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@updateRequestStateToDelivered#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/emmiters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get requests by filters")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get requests", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getRequestByEmmiters(
            @RequestParam(name = "emmiter_code", required = true) Long managerCode,
            @RequestParam(name = "emmiter_type", required = true) String emmiterType) {

        HttpStatus httpStatus = null;
        Object responseDto = null;

        try {

            responseDto = requestBusiness.getRequestByEmmiters(managerCode, emmiterType.toUpperCase());
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@getRequestByEmmiters#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@getRequestByEmmiters#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/search-manager-municipality", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get requests by manager and municipality")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get requests", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getRequestByManagerAndMunicipality(
            @RequestParam(name = "manager", required = true) Long managerCode,
            @RequestParam(name = "municipality", required = true) String municipalityCode,
            @RequestParam(name = "page", required = true) Integer page) {

        HttpStatus httpStatus = null;
        Object responseDto = null;

        try {

            responseDto = requestBusiness.getRequestByManagerAndMunicipality(page, municipalityCode, managerCode);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@getRequestByManagerAndMunicipality#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@getRequestByManagerAndMunicipality#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/search-manager-provider", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get requests by manager and provider")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get requests", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getRequestByManagerAndProvider(
            @RequestParam(name = "manager", required = true) Long managerCode,
            @RequestParam(name = "provider", required = true) Long providerId,
            @RequestParam(name = "page", required = true) Integer page) {

        HttpStatus httpStatus = null;
        Object responseDto = null;

        try {

            responseDto = requestBusiness.getRequestByManagerAndProvider(page, providerId, managerCode);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@getRequestByManagerAndProvider#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@getRequestByManagerAndProvider#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/search-manager-package", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get requests by manager and package")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Get requests", response = RequestDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getRequestByManagerAndPackage(
            @RequestParam(name = "manager", required = true) Long managerCode,
            @RequestParam(name = "package_label", required = true) String packageLabel) {

        HttpStatus httpStatus = null;
        Object responseDto = null;

        try {

            responseDto = requestBusiness.getRequestByManagerAndPackage(managerCode, packageLabel);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@getRequestByManagerAndPackage#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@getRequestByManagerAndPackage#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @RequestMapping(value = "/search-package", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get requests by package")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get requests", response = RequestDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<Object> getRequestByPackage(
            @RequestParam(name = "package_label", required = true) String packageLabel) {

        HttpStatus httpStatus = null;
        Object responseDto = null;

        try {

            responseDto = requestBusiness.getRequestByPackage(packageLabel);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestV1Controller@getRequestByPackage#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new ErrorDto(e.getMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestV1Controller@getRequestByPackage#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new ErrorDto(e.getMessage(), 3);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
