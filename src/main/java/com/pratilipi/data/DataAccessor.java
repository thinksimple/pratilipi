package com.pratilipi.data;

import java.util.List;

import com.pratilipi.common.type.PageType;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;

public interface DataAccessor {

	// PAGE Table
	Page newPage();
	Page getPage( Long id );
	Page getPage( String uri );
	Page getPage( PageType pageType, Long primaryContentId );
	Page createOrUpdatePage( Page page );

	
	// PRATILIPI Table
	Pratilipi newPratilipi();
	Pratilipi getPratilipi( Long id );
	List<Pratilipi> getPratilipiList( List<Long> idList );
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi );
	
	
	// Destroy
	void destroy();
	
}
