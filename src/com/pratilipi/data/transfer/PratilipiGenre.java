package com.pratilipi.data.transfer;

import com.pratilipi.commons.shared.PratilipiType;

public interface PratilipiGenre {

	Long getId();
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
	Long getGenreId();
	
	void setGenreId( Long genreId );
	
}
