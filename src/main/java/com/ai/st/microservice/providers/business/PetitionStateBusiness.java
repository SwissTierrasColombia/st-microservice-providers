package com.ai.st.microservice.providers.business;

import org.springframework.stereotype.Component;

@Component
public class PetitionStateBusiness {

	public static final Long PETITION_STATE_PENDING = (long) 1;
	public static final Long PETITION_STATE_ACCEPT = (long) 2;
	public static final Long PETITION_STATE_REJECT = (long) 3;

}
