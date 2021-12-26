package com.ai.st.microservice.providers.modules.requests.infrastructure.persistence;


import com.ai.st.microservice.providers.modules.requests.domain.*;
import com.ai.st.microservice.providers.modules.requests.domain.contracts.RequestRepository;
import com.ai.st.microservice.providers.modules.shared.domain.*;
import com.ai.st.microservice.providers.modules.shared.domain.contracts.ManagerMicroservice;
import com.ai.st.microservice.providers.modules.shared.domain.contracts.WorkspaceMicroservice;
import com.ai.st.microservice.providers.modules.shared.domain.criteria.*;
import com.ai.st.microservice.providers.modules.shared.domain.exceptions.ManagerNotFound;
import com.ai.st.microservice.providers.modules.shared.infrastructure.persistence.entities.*;
import com.ai.st.microservice.providers.repositories.RequestJPARepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public final class PostgresRequestRepository implements RequestRepository {

    private final RequestJPARepository requestJPARepository;
    private final ManagerMicroservice managerMicroservice;
    private final WorkspaceMicroservice workspaceMicroservice;

    public static final Map<String, String> MAPPING_FIELDS = new HashMap<>(
            Map.ofEntries(
                    new AbstractMap.SimpleEntry<>("requestDate", "createdAt"),
                    new AbstractMap.SimpleEntry<>("provider", "provider"),
                    new AbstractMap.SimpleEntry<>("requestStatus", "requestState"),
                    new AbstractMap.SimpleEntry<>("municipality", "municipalityCode"),
                    new AbstractMap.SimpleEntry<>("orderNumber", "packageLabel"),
                    new AbstractMap.SimpleEntry<>("manager", "emitters"),
                    new AbstractMap.SimpleEntry<>("workArea", "supplies")
            ));

    public PostgresRequestRepository(RequestJPARepository requestJPARepository, ManagerMicroservice managerMicroservice,
                                     WorkspaceMicroservice workspaceMicroservice) {
        this.requestJPARepository = requestJPARepository;
        this.managerMicroservice = managerMicroservice;
        this.workspaceMicroservice = workspaceMicroservice;
    }

    @Override
    public PageableDomain<Request> matching(Criteria criteria) {

        List<RequestEntity> requestEntities;
        Page<RequestEntity> page = null;

        if (criteria.hasFilters()) {

            Specification<RequestEntity> specification = addFilters(criteria.filters());

            if (criteria.hasPagination()) {
                Pageable pageable = addPageable(criteria.page().get(), criteria.limit().get(), criteria.order());
                page = requestJPARepository.findAll(specification, pageable);
                requestEntities = page.getContent();
            } else {
                if (criteria.order().hasOrder()) {
                    Sort sort = addSorting(criteria.order().orderBy(), criteria.order().orderType());
                    requestEntities = requestJPARepository.findAll(specification, sort);
                } else {
                    requestEntities = requestJPARepository.findAll(specification);
                }
            }

        } else {

            if (criteria.hasPagination()) {
                Pageable pageable = addPageable(criteria.page().get(), criteria.limit().get(), criteria.order());
                page = requestJPARepository.findAll(pageable);
                requestEntities = page.getContent();
            } else {
                if (criteria.order().hasOrder()) {
                    Sort sort = addSorting(criteria.order().orderBy(), criteria.order().orderType());
                    requestEntities = requestJPARepository.findAll(sort);
                } else {
                    requestEntities = requestJPARepository.findAll();
                }
            }
        }

        List<Request> requests = requestEntities.stream().map(this::mapping).collect(Collectors.toList());

        return new PageableDomain<>(
                requests,
                page != null ? Optional.of(page.getNumber() + 1) : Optional.empty(),
                page != null ? Optional.of(page.getNumberOfElements()) : Optional.empty(),
                page != null ? Optional.of(page.getTotalElements()) : Optional.empty(),
                page != null ? Optional.of(page.getTotalPages()) : Optional.empty(),
                page != null ? Optional.of(page.getSize()) : Optional.empty()
        );
    }

    private Specification<RequestEntity> addFilters(List<Filter> filters) {
        Specification<RequestEntity> specification = where(createSpecification(filters.remove(0)));
        for (Filter filter : filters) {
            specification = specification != null ? specification.and(createSpecification(filter)) : null;
        }
        return specification;
    }

    private Sort addSorting(OrderBy orderBy, OrderType orderType) {
        Sort sort = Sort.by(mappingField(orderBy.value()));
        sort = (orderType.isAsc()) ? sort.ascending() : sort.descending();
        return sort;
    }

    private Pageable addPageable(int page, int limit, Order order) {
        Pageable pageable;
        int numberPage = page - 1;
        if (order.hasOrder()) {
            Sort sort = addSorting(order.orderBy(), order.orderType());
            pageable = PageRequest.of(numberPage, limit, sort);
        } else {
            pageable = PageRequest.of(numberPage, limit);
        }
        return pageable;
    }

    private Specification<RequestEntity> createSpecification(Filter filter) {
        try {
            switch (filter.operator()) {
                case EQUAL:
                    return (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(buildPath(root, filter.field().value()), filter.value().value());
                case NOT_EQUAL:
                    return (root, query, criteriaBuilder) ->
                            criteriaBuilder.notEqual(buildPath(root, filter.field().value()), filter.value().value());
                case LIKE:
                    return (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(root.get(mappingField(filter.field().value())), "%" + filter.value().value() + "%");
                case CONTAINS:
                    return (root, query, criteriaBuilder) -> {
                        List<String> list = filter.values().stream().map(FilterValue::value).collect(Collectors.toList());
                        return buildPath(root, filter.field().value()).in(list);
                    };
                default:
                    throw new OperatorUnsupported();
            }
        } catch (Exception e) {
            throw new FieldUnsupported();
        }
    }

    private Path<?> buildPath(Root<RequestEntity> root, String fieldDomain) {
        String field = mappingField(fieldDomain);

        if (root.get(field).getJavaType().isAssignableFrom(RequestStateEntity.class)
                || root.get(field).getJavaType().isAssignableFrom(ProviderEntity.class)) {
            return root.get(field).get("id");
        } else if (field.equalsIgnoreCase("supplies")) {
            Join<RequestEntity, SupplyRequestedEntity> joinOptions = root.join(field, JoinType.LEFT);
            return joinOptions.get("typeSupply").get("providerProfile").get("id");
        }

        return root.get(field);
    }

    private String mappingField(String fieldDomain) {
        return MAPPING_FIELDS.get(fieldDomain);
    }

    private Request mapping(RequestEntity requestEntity) {

        EmitterEntity emitterManager = requestEntity.getEmitters().stream()
                .filter(e -> e.getEmitterType().equals(EmitterTypeEnum.ENTITY))
                .findFirst().orElseThrow(ManagerNotFound::new);
        Manager manager = managerMicroservice.getManager(ManagerCode.fromValue(emitterManager.getEmitterCode()));

        Federation federation = workspaceMicroservice.getFederation(FederationCode.fromValue(requestEntity.getMunicipalityCode()));

        RequestStateEntity requestStatusEntity = requestEntity.getRequestState();

        List<SupplyRequested> suppliesRequested = requestEntity.getSupplies().stream()
                .map(supply -> SupplyRequested.fromPrimitives(
                        supply.getId(), supply.getTypeSupply().getName(), supply.getTypeSupply().getProviderProfile().getName()))
                .collect(Collectors.toList());

        return new Request(
                RequestId.fromValue(requestEntity.getId()),
                RequestClosedAt.fromValue(requestEntity.getClosedAt()),
                RequestClosedBy.fromValue(requestEntity.getClosedBy()),
                RequestDate.fromValue(requestEntity.getCreatedAt()),
                RequestDeadline.fromValue(requestEntity.getDeadline()),
                RequestObservations.fromValue(requestEntity.getObservations()),
                RequestOrderNumber.fromValue(requestEntity.getPackageLabel()),
                RequestSentReviewAt.fromValue(requestEntity.getSentReviewAt()),
                RequestStatus.fromPrimivites(requestStatusEntity.getId(), requestStatusEntity.getName()),
                manager,
                federation,
                suppliesRequested
        );
    }

}
