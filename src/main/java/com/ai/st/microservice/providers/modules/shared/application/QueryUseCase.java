package com.ai.st.microservice.providers.modules.shared.application;

public interface QueryUseCase<Q extends Query, R extends Response> {

    R handle(Q query);

}
