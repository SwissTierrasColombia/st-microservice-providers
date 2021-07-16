package com.ai.st.microservice.providers.business;

import com.ai.st.microservice.common.clients.ManagerFeignClient;
import com.ai.st.microservice.common.clients.ReportFeignClient;
import com.ai.st.microservice.common.clients.WorkspaceFeignClient;
import com.ai.st.microservice.common.dto.managers.MicroserviceManagerDto;
import com.ai.st.microservice.common.dto.reports.MicroserviceReportInformationDto;
import com.ai.st.microservice.common.dto.reports.MicroserviceRequestReportSNRDto;
import com.ai.st.microservice.common.dto.reports.MicroserviceRequestReportSNRSuppliesDto;
import com.ai.st.microservice.common.dto.workspaces.MicroserviceMunicipalityDto;
import com.ai.st.microservice.common.exceptions.BusinessException;
import com.ai.st.microservice.providers.entities.EmitterEntity;
import com.ai.st.microservice.providers.entities.EmitterTypeEnum;
import com.ai.st.microservice.providers.entities.RequestEntity;
import com.ai.st.microservice.providers.services.IRequestService;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public final class ReportBusiness {

    private final ReportFeignClient reportClient;
    private final WorkspaceFeignClient workspaceClient;
    private final ManagerFeignClient managerClient;
    private final IRequestService requestService;

    public ReportBusiness(ReportFeignClient reportClient, WorkspaceFeignClient workspaceClient,
                          ManagerFeignClient managerClient, IRequestService requestService) {
        this.reportClient = reportClient;
        this.workspaceClient = workspaceClient;
        this.managerClient = managerClient;
        this.requestService = requestService;
    }

    public String generateSuppliesDeliveredReportSNR(Date from, Date to) throws BusinessException {

        List<RequestEntity> requests = requestService.getRequestForReportSNR(from, to);

        System.out.println("solicitudes: " + requests.size());

        if (requests.size() == 0) {
            throw new BusinessException("No se han encontrado solicitudes para el período de tiempo seleccionado.");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String reportDate = dateFormat.format(new Date());
        String namespace = "/reportes/SNR/";

        MicroserviceRequestReportSNRSuppliesDto reportData = new MicroserviceRequestReportSNRSuppliesDto();
        reportData.setCreatedAt(reportDate);
        reportData.setNamespace(namespace);

        List<MicroserviceRequestReportSNRDto> requestsReport = new ArrayList<>();
        for (RequestEntity requestEntity : requests) {

            MicroserviceRequestReportSNRDto dto = new MicroserviceRequestReportSNRDto();

            String department = "";
            String municipality = "";

            MicroserviceMunicipalityDto municipalityDto = workspaceClient.findMunicipalityByCode(requestEntity.getMunicipalityCode());
            if (municipalityDto != null) {
                municipality = municipalityDto.getName();
                department = (municipalityDto.getDepartment() != null) ? municipalityDto.getDepartment().getName() : "";
            }

            String manager = "";
            EmitterEntity emitterEntity =
                    requestEntity.getEmitters().stream().filter(e -> e.getEmitterType().equals(EmitterTypeEnum.ENTITY)).findAny().orElse(null);
            if (emitterEntity != null) {
                MicroserviceManagerDto managerDto = managerClient.findById(emitterEntity.getEmitterCode());
                if (managerDto != null) {
                    manager = managerDto.getAlias();
                }
            }

            String deliveryDate;
            if (requestEntity.getSentReviewAt() != null) {
                deliveryDate = dateFormat.format(requestEntity.getSentReviewAt());
            } else {
                deliveryDate = dateFormat.format(requestEntity.getClosedAt());
            }

            dto.setDepartment(department);
            dto.setMunicipality(municipality);
            dto.setManager(manager);
            dto.setDeliveryDate(deliveryDate);
            dto.setOrderNumber(requestEntity.getPackageLabel());
            dto.setRequestNumber(requestEntity.getId().toString());

            requestsReport.add(dto);
        }

        reportData.setRequests(requestsReport);

        MicroserviceReportInformationDto informationDto = reportClient.createReportSuppliesSNR(reportData);

        if (informationDto.getReportGenerated()) {
            return informationDto.getUrlReport();
        }

        throw new BusinessException("No se ha podido generar el reporte");
    }

}
