package com.ai.st.microservice.providers.controllers.v1;

import com.ai.st.microservice.common.business.AdministrationBusiness;
import com.ai.st.microservice.common.dto.administration.MicroserviceUserDto;
import com.ai.st.microservice.common.dto.general.BasicResponseDto;
import com.ai.st.microservice.common.exceptions.BusinessException;
import com.ai.st.microservice.common.exceptions.DisconnectedMicroserviceException;
import com.ai.st.microservice.common.exceptions.InputValidationException;

import com.ai.st.microservice.providers.business.ProviderUserBusiness;
import com.ai.st.microservice.providers.business.ReportBusiness;
import com.ai.st.microservice.providers.dto.ProviderDto;

import com.google.common.io.Files;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Api(value = "Reports", tags = {"Reports"})
@RestController
@RequestMapping("api/providers-supplies/v1/reports")
public final class ReportV1Controller {

    private final Logger log = LoggerFactory.getLogger(ReportV1Controller.class);

    private final static long MAX_DAYS_BETWEEN_DATES = 93;

    private final AdministrationBusiness administrationBusiness;
    private final ProviderUserBusiness providerUserBusiness;
    private final ReportBusiness reportBusiness;
    private final ServletContext servletContext;

    public ReportV1Controller(AdministrationBusiness administrationBusiness, ProviderUserBusiness providerUserBusiness,
                              ReportBusiness reportBusiness, ServletContext servletContext) {
        this.administrationBusiness = administrationBusiness;
        this.providerUserBusiness = providerUserBusiness;
        this.reportBusiness = reportBusiness;
        this.servletContext = servletContext;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping(value = "/supplies-delivered-snr", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Generate report with supplies delivered")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Report generated"),
            @ApiResponse(code = 500, message = "Error Server", response = String.class)})
    @ResponseBody
    public ResponseEntity<?> suppliesDelivered(
            @RequestParam(name = "from") Date from,
            @RequestParam(name = "to") Date to,
            @RequestHeader("authorization") String headerAuthorization) {

        MediaType mediaType;
        File file;
        InputStreamResource resource;

        try {

            // user session
            MicroserviceUserDto userDtoSession = administrationBusiness.getUserByToken(headerAuthorization);
            if (userDtoSession == null) {
                throw new DisconnectedMicroserviceException("Ha ocurrido un error consultando el usuario");
            }

            // get provider
            ProviderDto providerDto = providerUserBusiness.getProviderByUserCode(userDtoSession.getId());
            if (providerDto == null) {
                throw new BusinessException("No se ha encontrado el proveedor.");
            }

            if (!to.after(from)) {
                throw new InputValidationException("La fecha de inicio debe ser menor que la fecha de finalización.");
            }

            long difference = getDateDiff(from, to, TimeUnit.DAYS);
            if (difference > MAX_DAYS_BETWEEN_DATES) {
                throw new InputValidationException("La diferencia entre las fechas máximo debe ser de 3 meses.");
            }

            String pathFile = reportBusiness.generateSuppliesDeliveredReportSNR(from, to);

            Path path = Paths.get(pathFile);
            String fileName = path.getFileName().toString();

            String mineType = servletContext.getMimeType(fileName);
            try {
                mediaType = MediaType.parseMediaType(mineType);
            } catch (Exception e) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            file = new File(pathFile);
            resource = new InputStreamResource(new FileInputStream(file));

        } catch (DisconnectedMicroserviceException e) {
            log.error("Error ReportV1Controller@createRequest#Microservice ---> " + e.getMessage());
            return new ResponseEntity<>(new BasicResponseDto(e.getMessage(), 4), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InputValidationException e) {
            log.error("Error ReportV1Controller@suppliesDelivered#Validation ---> " + e.getMessage());
            return new ResponseEntity<>(new BasicResponseDto(e.getMessage(), 3), HttpStatus.BAD_REQUEST);
        } catch (BusinessException e) {
            log.error("Error ReportV1Controller@suppliesDelivered#Business ---> " + e.getMessage());
            return new ResponseEntity<>(new BasicResponseDto(e.getMessage(), 2), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("Error ReportV1Controller@suppliesDelivered#General ---> " + e.getMessage());
            return new ResponseEntity<>(new BasicResponseDto(e.getMessage(), 1), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType).contentLength(file.length())
                .header("extension", Files.getFileExtension(file.getName()))
                .header("filename", "reporte_entrega." + Files.getFileExtension(file.getName())).body(resource);
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

}
