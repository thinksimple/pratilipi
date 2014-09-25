package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum UserReviewState implements IsSerializable {
	
	NOT_SUBMITTED,
	PENDING_APPROVAL,
	APPROVED,
	AUTO_APPROVED
		
}
