package com.ai.st.microservice.providers.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.providers.dto.ProviderCategoryDto;
import com.ai.st.microservice.providers.entities.ProviderCategoryEntity;
import com.ai.st.microservice.providers.exceptions.BusinessException;
import com.ai.st.microservice.providers.services.IProviderCategoryService;

@Component
public class ProviderCategoryBusiness {

	public static final Long PROVIDER_CATEGORY_CADASTRAL = (long) 1;
	public static final Long PROVIDER_CATEGORY_REGISTRY = (long) 2;
	public static final Long PROVIDER_CATEGORY_LAND = (long) 3;
	public static final Long PROVIDER_CATEGORY_GENERAL = (long) 4;

	@Autowired
	private IProviderCategoryService providerCategoryService;

	public List<ProviderCategoryDto> getCategories() throws BusinessException {

		List<ProviderCategoryDto> listCategoriesDto = new ArrayList<ProviderCategoryDto>();

		List<ProviderCategoryEntity> listCategoriesEntity = providerCategoryService.getAllProvidersCategories();

		for (ProviderCategoryEntity categoryEntity : listCategoriesEntity) {
			listCategoriesDto.add(new ProviderCategoryDto(categoryEntity.getId(), categoryEntity.getName()));
		}

		return listCategoriesDto;
	}

}
