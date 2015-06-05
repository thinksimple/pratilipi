package com.pratilipi.data.transfer;

import java.io.Serializable;

import com.pratilipi.commons.shared.PratilipiType;

public interface PratilipiTag extends Serializable {

	Long getId();
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
	Long getTagId();
	
	void setTagId( Long tag );
	
}
