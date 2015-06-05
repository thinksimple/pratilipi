package com.pratilipi.data.transfer;

import java.io.Serializable;

public interface PratilipiGenre extends Serializable {

	String getId();
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	Long getGenreId();
	
	void setGenreId( Long genreId );
	
}
