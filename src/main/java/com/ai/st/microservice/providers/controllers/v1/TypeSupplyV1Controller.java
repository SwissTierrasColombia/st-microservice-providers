package com.ai.st.microservice.providers.controllers.v1;

import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.providers.services.tracing.SCMTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.st.microservice.providers.business.TypeSupplyBusiness;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.exceptions.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Manage Types Supplies", tags = { "Types Supplies" })
@RestController
@RequestMapping("api/providers-supplies/v1/types-supplies")
public class TypeSupplyV1Controller {

    private final Logger log = LoggerFactory.getLogger(TypeSupplyV1Controller.class);

    private final TypeSupplyBusiness typeSupplyBusiness;

    public TypeSupplyV1Controller(TypeSupplyBusiness typeSupplyBusiness) {
        this.typeSupplyBusiness = typeSupplyBusiness;
    }

    @GetMapping(value = "{typeSupplyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get type supply")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Get type supply", response = TypeSupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> getTypeSupplyById(@PathVariable Long typeSupplyId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("getTypeSupplyById");

            responseDto = typeSupplyBusiness.getTypeSupplyById(typeSupplyId);
            httpStatus = (responseDto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        } catch (BusinessException e) {
            log.error("Error TypeSupplyV1Controller@getTypeSupplyById#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error TypeSupplyV1Controller@getTypeSupplyById#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "{typeSupplyId}/enable", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enable supply")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Supply enabled", response = TypeSupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> enableSupply(@PathVariable Long typeSupplyId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("enableSupply");

            responseDto = typeSupplyBusiness.enableTypeSupply(typeSupplyId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error TypeSupplyV1Controller@enableSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error TypeSupplyV1Controller@enableSupply#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @PutMapping(value = "{typeSupplyId}/disable", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Disable supply")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Supply disabled", response = TypeSupplyDto.class),
            @ApiResponse(code = 500, message = "Error Server", response = String.class) })
    @ResponseBody
    public ResponseEntity<?> disableSupply(@PathVariable Long typeSupplyId) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            SCMTracing.setTransactionName("disableSupply");

            responseDto = typeSupplyBusiness.disableTypeSupply(typeSupplyId);
            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error TypeSupplyV1Controller@disableSupply#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        } catch (Exception e) {
            log.error("Error TypeSupplyV1Controller@disableSupply#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage());
            SCMTracing.sendError(e.getMessage());
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
