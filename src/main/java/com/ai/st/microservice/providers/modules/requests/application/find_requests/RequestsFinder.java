package com.ai.st.microservice.providers.modules.requests.application.find_requests;

import com.ai.st.microservice.providers.modules.requests.application.RequestResponse;
import com.ai.st.microservice.providers.modules.requests.domain.Request;
import com.ai.st.microservice.providers.modules.shared.domain.FederationCode;
import com.ai.st.microservice.providers.modules.requests.domain.RequestOrderNumber;
import com.ai.st.microservice.providers.modules.requests.domain.contracts.RequestRepository;
import com.ai.st.microservice.providers.modules.shared.application.PageableResponse;
import com.ai.st.microservice.providers.modules.shared.application.QueryUseCase;
import com.ai.st.microservice.providers.modules.shared.domain.PageableDomain;
import com.ai.st.microservice.providers.modules.shared.domain.Service;
import com.ai.st.microservice.providers.modules.shared.domain.criteria.*;
import com.ai.st.microservice.providers.modules.shared.domain.exceptions.ErrorFromInfrastructure;

import java.util.*;
import java.util.stream.Collectors;

@Service
public final class RequestsFinder implements QueryUseCase<RequestsFinderQuery, PageableResponse<RequestResponse>> {

    private final RequestRepository requestRepository;

    private final static int PAGE_DEFAULT = 1;
    private final static int LIMIT_DEFAULT = 10;

    public RequestsFinder(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public PageableResponse<RequestResponse> handle(RequestsFinderQuery query) {

        List<Filter> filters = new ArrayList<>(
                Arrays.asList(
                        filterByProvider(query.provider()),
                        filterByStatus(query.status())
                )
        );

        String municipality = query.municipality();
        if (municipality != null && !municipality.isEmpty()) {
            filters.add(filterByMunicipality(municipality));
        }

        String orderNumber = query.orderNumber();
        if (orderNumber != null && !orderNumber.isEmpty()) {
            filters.add(filterByOrderNumber(orderNumber));
        }

        Criteria criteria = new Criteria(
                filters,
                Order.fromValues(Optional.of("requestDate"), Optional.of("DESC")),
                Optional.of(verifyPage(query.page())),
                Optional.of(verifyLimit(query.limit()))
        );

        PageableDomain<Request> pageableDomain = requestRepository.matching(criteria);

        return buildResponse(pageableDomain);
    }

    private int verifyPage(int page) {
        return (page <= 0) ? PAGE_DEFAULT : page;
    }

    private int verifyLimit(int limit) {
        return (limit <= 9) ? LIMIT_DEFAULT : limit;
    }

    private Filter filterByProvider(Long providerId) {
        FilterField filterField = new FilterField("provider");
        return new Filter(filterField, FilterOperator.EQUAL, new FilterValue(providerId.toString()));
    }

    private Filter filterByStatus(Long statusId) {
        FilterField filterField = new FilterField("requestStatus");
        return new Filter(filterField, FilterOperator.EQUAL, new FilterValue(statusId.toString()));
    }

    private Filter filterByMunicipality(String municipality) {
        FilterField filterField = new FilterField("municipality");
        return new Filter(filterField, FilterOperator.EQUAL, new FilterValue(FederationCode.fromValue(municipality).value()));
    }

    private Filter filterByOrderNumber(String orderNumber) {
        FilterField filterField = new FilterField("orderNumber");
        return new Filter(filterField, FilterOperator.LIKE, new FilterValue(RequestOrderNumber.fromValue(orderNumber).value()));
    }

    private PageableResponse<RequestResponse> buildResponse(PageableDomain<Request> pageableDomain) {

        if (!pageableDomain.numberOfElements().isPresent() || !pageableDomain.totalElements().isPresent()
                || !pageableDomain.totalPages().isPresent() || !pageableDomain.size().isPresent()
                || !pageableDomain.currentPage().isPresent()) {
            throw new ErrorFromInfrastructure();
        }

        List<RequestResponse> requestResponses = pageableDomain.items()
                .stream().map(RequestResponse::fromAggregate).collect(Collectors.toList());

        return new PageableResponse<>(
                requestResponses,
                pageableDomain.currentPage().get(),
                pageableDomain.numberOfElements().get(),
                pageableDomain.totalElements().get(),
                pageableDomain.totalPages().get(),
                pageableDomain.size().get()
        );
    }

}
