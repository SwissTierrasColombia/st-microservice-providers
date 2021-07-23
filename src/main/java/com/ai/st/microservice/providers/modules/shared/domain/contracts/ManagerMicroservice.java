package com.ai.st.microservice.providers.modules.shared.domain.contracts;

import com.ai.st.microservice.providers.modules.shared.domain.Manager;
import com.ai.st.microservice.providers.modules.shared.domain.ManagerCode;

public interface ManagerMicroservice {

    Manager getManager(ManagerCode managerCode);

}
