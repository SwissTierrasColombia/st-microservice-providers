package com.ai.st.microservice.providers.modules.requests.domain.contracts;

import com.ai.st.microservice.providers.modules.requests.domain.Request;
import com.ai.st.microservice.providers.modules.shared.domain.PageableDomain;
import com.ai.st.microservice.providers.modules.shared.domain.criteria.Criteria;

public interface RequestRepository {

    PageableDomain<Request> matching(Criteria criteria);

}
