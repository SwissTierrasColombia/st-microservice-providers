package com.ai.st.microservice.providers.entrypoints.controllers.requests;

import com.ai.st.microservice.common.business.AdministrationBusiness;
import com.ai.st.microservice.common.business.ManagerBusiness;
import com.ai.st.microservice.common.business.OperatorBusiness;
import com.ai.st.microservice.common.dto.general.BasicResponseDto;

import com.ai.st.microservice.providers.business.ProviderUserBusiness;
import com.ai.st.microservice.providers.entrypoints.controllers.ApiController;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.modules.requests.application.RequestResponse;
import com.ai.st.microservice.providers.modules.requests.application.find_requests.RequestsFinder;
import com.ai.st.microservice.providers.modules.requests.application.find_requests.RequestsFinderQuery;
import com.ai.st.microservice.providers.modules.requests.domain.RequestStatusId;
import com.ai.st.microservice.providers.modules.shared.domain.DomainError;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Manager Requests [GET]", tags = {"Requests"})
@RestController
public final class RequestGetController extends ApiController {

    private final Logger log = LoggerFactory.getLogger(RequestGetController.class);

    private final RequestsFinder requestsFinder;

    public RequestGetController(AdministrationBusiness administrationBusiness,
                                ManagerBusiness managerBusiness, OperatorBusiness operatorBusiness,
                                ProviderUserBusiness providerUserBusiness,
                                RequestsFinder requestsFinder) {
        super(administrationBusiness, managerBusiness, operatorBusiness, providerUserBusiness);
        this.requestsFinder = requestsFinder;
    }

    @GetMapping(value = "api/providers-supplies/v2/pending-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get pending requests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pending requests got", response = RequestResponse.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = BasicResponseDto.class)})
    @ResponseBody
    public ResponseEntity<?> findPendingRequests(
            @RequestHeader("authorization") String headerAuthorization,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "municipality", required = false) String municipality,
            @RequestParam(name = "orderNumber", required = false) String orderNumber,
            @RequestParam(name = "manager", required = false) Long manager) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            InformationSession session = this.getInformationSession(headerAuthorization);

            responseDto = requestsFinder.handle(
                    new RequestsFinderQuery(
                            page, limit, session.entityCode(), RequestStatusId.REQUESTED, municipality, orderNumber, manager, session.userCode()
                    )
            );

            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestGetController@findPendingRequests#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage(), 2);
        } catch (DomainError e) {
            log.error("Error RequestGetController@findPendingRequests#Domain ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.errorMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestGetController@findPendingRequests#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage(), 1);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @GetMapping(value = "api/providers-supplies/v2/attended-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get attended requests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Attended requests got", response = RequestResponse.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error Server", response = BasicResponseDto.class)})
    @ResponseBody
    public ResponseEntity<?> findAttendedRequests(
            @RequestHeader("authorization") String headerAuthorization,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "municipality", required = false) String municipality,
            @RequestParam(name = "orderNumber", required = false) String orderNumber,
            @RequestParam(name = "manager", required = false) Long manager) {

        HttpStatus httpStatus;
        Object responseDto;

        try {

            InformationSession session = this.getInformationSession(headerAuthorization);

            responseDto = requestsFinder.handle(
                    new RequestsFinderQuery(
                            page, limit, session.entityCode(), RequestStatusId.DELIVERED, municipality, orderNumber, manager, session.userCode()
                    )
            );

            httpStatus = HttpStatus.OK;

        } catch (BusinessException e) {
            log.error("Error RequestGetController@findAttendedRequests#Business ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.getMessage(), 2);
        } catch (DomainError e) {
            log.error("Error RequestGetController@findAttendedRequests#Domain ---> " + e.getMessage());
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            responseDto = new BasicResponseDto(e.errorMessage(), 2);
        } catch (Exception e) {
            log.error("Error RequestGetController@findAttendedRequests#General ---> " + e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseDto = new BasicResponseDto(e.getMessage(), 1);
        }

        return new ResponseEntity<>(responseDto, httpStatus);
    }

}
