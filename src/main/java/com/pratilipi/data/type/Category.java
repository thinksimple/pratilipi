package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.CategoryType;
import com.pratilipi.common.util.PratilipiFilter;

public interface Category extends Serializable {

	@Deprecated
	Long getId();

	@Deprecated
	int getSerialNumber();
	
	@Deprecated
	void setSerialNumber( int serialNumber );
	
	String getName();
	
	@Deprecated
	void setName( String name );
	
	@Deprecated
	String getPlural();
	
	@Deprecated
	void setPlural( String plural );
	
	@Deprecated
	Long getLanguageId();
	
	@Deprecated
	void setLangugeId( Long languageId );
	
	@Deprecated
	CategoryType getType();
	
	@Deprecated
	void setType( CategoryType type );
	
	@Deprecated
	Date getCreationData();
	
	@Deprecated
	void setCreationDate( Date creationDate );
	
	@Deprecated
	Boolean isHidden();
	
	@Deprecated
	void setHidden( Boolean hidden );
	
	PratilipiFilter getPratilipiFilter();
	
}
