package com.ai.st.microservice.providers.modules.shared.domain.contracts;

import com.ai.st.microservice.providers.modules.shared.domain.Federation;
import com.ai.st.microservice.providers.modules.shared.domain.FederationCode;

public interface WorkspaceMicroservice {

    Federation getFederation(FederationCode federationCode);

}
