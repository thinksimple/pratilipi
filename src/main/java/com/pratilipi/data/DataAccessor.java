package com.pratilipi.data;

import java.util.List;

import com.pratilipi.data.type.Pratilipi;


public interface DataAccessor {

	// PRATILIPI Table
	Pratilipi newPratilipi();
	Pratilipi getPratilipi( Long id );
	List<Pratilipi> getPratilipiList( List<Long> idList );
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi );
	
	
	// Destroy
	void destroy();
	
}
