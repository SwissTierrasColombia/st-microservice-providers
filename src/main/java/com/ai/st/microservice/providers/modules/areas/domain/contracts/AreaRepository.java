package com.ai.st.microservice.providers.modules.areas.domain.contracts;

import com.ai.st.microservice.providers.modules.areas.domain.Area;
import com.ai.st.microservice.providers.modules.shared.domain.UserCode;

import java.util.List;

public interface AreaRepository {

    List<Area> findAresByUser(UserCode userCode);

}
