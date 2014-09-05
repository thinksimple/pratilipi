package com.pratilipi.data.transfer;

import com.pratilipi.commons.shared.PratilipiType;

public interface PratilipiAuthor {

	Long getId();
	
	Long getPratilipiId();
	
	void setPratilipiId( Long bookId );
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
}
