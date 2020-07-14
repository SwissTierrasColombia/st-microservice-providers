package com.ai.st.microservice.providers.business;

import org.springframework.stereotype.Component;

@Component
public class SupplyRequestedStateBusiness {

	public static final Long SUPPLY_REQUESTED_STATE_ACCEPTED = (long) 1;
	public static final Long SUPPLY_REQUESTED_STATE_REJECTED = (long) 2;
	public static final Long SUPPLY_REQUESTED_STATE_VALIDATING = (long) 3;
	public static final Long SUPPLY_REQUESTED_STATE_PENDING = (long) 4;
	public static final Long SUPPLY_REQUESTED_STATE_UNDELIVERED = (long) 5;
	public static final Long SUPPLY_REQUESTED_STATE_PENDING_REVIEW = (long) 6;
	public static final Long SUPPLY_REQUESTED_STATE_SETTING_REVIEW = (long) 7;
	public static final Long SUPPLY_REQUESTED_STATE_IN_REVIEW = (long) 8;
	public static final Long SUPPLY_REQUESTED_STATE_CLOSING_REVIEW = (long) 9;

}
