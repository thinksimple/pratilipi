package com.pratilipi.data.transfer;

import java.io.Serializable;

import com.pratilipi.commons.shared.PratilipiType;

public interface PratilipiGenre extends Serializable {

	Long getId();
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
	Long getGenreId();
	
	void setGenreId( Long genreId );
	
}
