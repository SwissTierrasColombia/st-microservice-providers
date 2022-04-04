package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.ExtensionDto;
import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.dto.ProviderDto;
import com.ai.st.microservice.providers.dto.ProviderProfileDto;
import com.ai.st.microservice.providers.dto.TypeSupplyDto;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ExtensionEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.ProviderEntity;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.TypeSupplyEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.ITypeSupplyService;

@Component
public class TypeSupplyBusiness {

    @Autowired
    private ITypeSupplyService typeSupplyService;

    public TypeSupplyDto getTypeSupplyById(Long typeSupplyId) throws BusinessException {

        TypeSupplyDto typeSupplyDto = null;

        TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyId);
        if (typeSupplyEntity instanceof TypeSupplyEntity) {
            typeSupplyDto = entityParseDto(typeSupplyEntity);
        }

        return typeSupplyDto;
    }

    public TypeSupplyDto enableTypeSupply(Long typeSupplyId) throws BusinessException {

        TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyId);
        if (typeSupplyEntity == null) {
            throw new BusinessException("El tipo de insumo no existe.");
        }

        typeSupplyEntity.setActive(true);
        typeSupplyEntity = typeSupplyService.createTypeSupply(typeSupplyEntity);

        return entityParseDto(typeSupplyEntity);
    }

    public TypeSupplyDto disableTypeSupply(Long typeSupplyId) throws BusinessException {

        TypeSupplyEntity typeSupplyEntity = typeSupplyService.getTypeSupplyById(typeSupplyId);
        if (typeSupplyEntity == null) {
            throw new BusinessException("El tipo de insumo no existe.");
        }

        typeSupplyEntity.setActive(false);
        typeSupplyEntity = typeSupplyService.createTypeSupply(typeSupplyEntity);

        return entityParseDto(typeSupplyEntity);
    }

    public TypeSupplyDto entityParseDto(TypeSupplyEntity typeSupplyEntity) {

        TypeSupplyDto typeSupplyDto = new TypeSupplyDto();
        typeSupplyDto.setCreatedAt(typeSupplyEntity.getCreatedAt());
        typeSupplyDto.setDescription(typeSupplyEntity.getDescription());
        typeSupplyDto.setId(typeSupplyEntity.getId());
        typeSupplyDto.setMetadataRequired(typeSupplyEntity.getIsMetadataRequired());
        typeSupplyDto.setModelRequired(typeSupplyEntity.getIsModelRequired());
        typeSupplyDto.setName(typeSupplyEntity.getName());
        typeSupplyDto.setActive(typeSupplyEntity.getActive());

        ProviderEntity providerEntity = typeSupplyEntity.getProvider();
        ProviderDto providerDto = new ProviderDto();
        providerDto.setCreatedAt(providerEntity.getCreatedAt());
        providerDto.setId(providerEntity.getId());
        providerDto.setName(providerEntity.getName());
        providerDto.setTaxIdentificationNumber(providerEntity.getTaxIdentificationNumber());
        ProviderCategoryDto providerCategoryDto = new ProviderCategoryDto();
        providerCategoryDto.setId(providerEntity.getProviderCategory().getId());
        providerCategoryDto.setName(providerEntity.getProviderCategory().getName());
        providerDto.setProviderCategory(providerCategoryDto);
        typeSupplyDto.setProvider(providerDto);

        ProviderProfileDto providerProfileDto = new ProviderProfileDto();
        providerProfileDto.setDescription(typeSupplyEntity.getProviderProfile().getDescription());
        providerProfileDto.setId(typeSupplyEntity.getProviderProfile().getId());
        providerProfileDto.setName(typeSupplyEntity.getProviderProfile().getName());
        typeSupplyDto.setProviderProfile(providerProfileDto);

        List<ExtensionDto> listExtensionsDto = new ArrayList<ExtensionDto>();
        for (ExtensionEntity extensionEntity : typeSupplyEntity.getExtensions()) {
            ExtensionDto extensionDto = new ExtensionDto();
            extensionDto.setId(extensionEntity.getId());
            extensionDto.setName(extensionEntity.getName());
            listExtensionsDto.add(extensionDto);
        }
        typeSupplyDto.setExtensions(listExtensionsDto);

        return typeSupplyDto;
    }

}
