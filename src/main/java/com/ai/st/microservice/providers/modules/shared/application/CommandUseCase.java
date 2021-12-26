package com.ai.st.microservice.providers.modules.shared.application;

public interface CommandUseCase<C extends Command> {

    void handle(C command);

}
