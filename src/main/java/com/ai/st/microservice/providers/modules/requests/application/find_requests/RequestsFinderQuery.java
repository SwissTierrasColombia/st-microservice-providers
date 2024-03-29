package com.ai.st.microservice.providers.modules.requests.application.find_requests;

import com.ai.st.microservice.providers.modules.shared.application.Query;

public final class RequestsFinderQuery implements Query {

    private final int page;
    private final int limit;
    private final Long provider;
    private final Long status;
    private final String municipality;
    private final String orderNumber;
    private final Long manager;
    private final Long user;
    private final boolean skipFilterAreas;

    public RequestsFinderQuery(int page, int limit, Long provider, Long status, String municipality, String orderNumber,
            Long manager, Long user, boolean skipFilterAreas) {
        this.page = page;
        this.limit = limit;
        this.provider = provider;
        this.status = status;
        this.municipality = municipality;
        this.orderNumber = orderNumber;
        this.manager = manager;
        this.user = user;
        this.skipFilterAreas = skipFilterAreas;
    }

    public int page() {
        return page;
    }

    public int limit() {
        return limit;
    }

    public Long provider() {
        return provider;
    }

    public Long status() {
        return status;
    }

    public String municipality() {
        return municipality;
    }

    public String orderNumber() {
        return orderNumber;
    }

    public Long manager() {
        return manager;
    }

    public Long user() {
        return user;
    }

    public boolean skipFilterAreas() {
        return skipFilterAreas;
    }
}
