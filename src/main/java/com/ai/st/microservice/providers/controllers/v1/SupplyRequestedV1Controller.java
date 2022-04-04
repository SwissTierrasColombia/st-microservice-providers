package com.ai.st.microservice.providers.controllers.v1;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.providers.services.tracing.SCMTracing;
import com.ai.st.microservice.providers.services.tracing.TracingKeyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.providers.business.SupplyRequestedBusiness;
import com.ai.st.microservice.providers.business.SupplyRevisionBusiness;
import com.ai.st.microservice.providers.dto.CreateSupplyRevisionDto;
import com.ai.st.microservice.providers.dto.SupplyRevisionDto;
import com.ai.st.microservice.providers.dto.UpdateSupplyRevisionDto;
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

    private final SupplyRequestedBusiness supplyRequestedBusiness;
    private final SupplyRevisionBusiness supplyRevisionBusiness;

    public SupplyRequestedV1Controller(SupplyRequestedBusiness supplyRequestedBusiness,
            SupplyRevisionBusiness supplyRevisionBusiness) {
        this.supplyRequestedBusiness = supplyRequestedBusiness;
        this.supplyRevisionBusiness = supplyRevisionBusiness;
    }

    @GetMapping(value = "/{supplyRequestedId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get supply requested by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get supply requested", response = SupplyRequestedDto.class),
            @ApiResponse(code = 404, message = "Supply not found", response = BasicResponseDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getSupplyRequestedById(@PathVariable Long supplyRequestedId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getSupplyRequestedById");

            responseDto = supplyRequestedBusiness.getSupplyRequestedById(supplyRequestedId);
            httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        } catch (Exception e) {
            log.error("Error SupplyRequestedV1Controller@getSupplyRequestedById#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PostMapping(value = "{supplyRequestedId}/revision", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create revision")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Revision created", response = SupplyRevisionDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> createRevision(@PathVariable Long supplyRequestedId,
            @RequestBody CreateSupplyRevisionDto createRevisionDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("createRevision");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, createRevisionDto.toString());

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
            log.error("Error SupplyRequestedV1Controller@createRevision#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (BusinessException e) {
            log.error("Error SupplyRequestedV1Controller@createRevision#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyRequestedV1Controller@createRevision#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @DeleteMapping(value = "{supplyRequestedId}/revision/{supplyRevisionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete revision")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "Revision deleted", response = SupplyRevisionDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> deleteRevision(@PathVariable Long supplyRequestedId, @PathVariable Long supplyRevisionId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("deleteRevision");

            supplyRevisionBusiness.deleteRevision(supplyRequestedId, supplyRevisionId);
            responseDto = new BasicResponseDto("Revision borrada");
            httpStatus = HttpStatus.NO_CONTENT;

        } catch (Exception e) {
            log.error("Error SupplyRequestedV1Controller@deleteRevision#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "{supplyRequestedId}/revision", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get revision from supply requested")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get revision from supply requested", response = SupplyRevisionDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getRevisionFromSupply(@PathVariable Long supplyRequestedId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getRevisionFromSupply");

            responseDto = supplyRevisionBusiness.getSupplyRevisionFromSupplyRequested(supplyRequestedId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error SupplyRequestedV1Controller@getRevisionFromSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyRequestedV1Controller@getRevisionFromSupply#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "{supplyRequestedId}/revision/{revisionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update revision")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Revision updated", response = SupplyRevisionDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> updateRevision(@PathVariable Long supplyRequestedId, @PathVariable Long revisionId,
            @RequestBody UpdateSupplyRevisionDto updateRevisionDto) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("updateRevision");
            SCMTracing.addCustomParameter(TracingKeyword.BODY_REQUEST, updateRevisionDto.toString());

            responseDto = supplyRevisionBusiness.updateSupplyRevision(supplyRequestedId, revisionId,
                    updateRevisionDto.getFinishedBy());
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error SupplyRequestedV1Controller@updateRevision#Validation ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error SupplyRequestedV1Controller@updateRevision#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
