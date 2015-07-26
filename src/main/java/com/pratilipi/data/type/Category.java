package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.CategoryType;

public interface Category  extends Serializable {
	
	Long getId();

	int getSerialNumber();
	
	void setSerialNumber( int serialNumber );
	
	String getName();
	
	void setName( String name );
	
	String getPlural();
	
	void setPlural( String plural );
	
	Long getLanguageId();
	
	void setLangugeId( Long languageId );
	
	CategoryType getType();
	
	void setType( CategoryType type );
	
	Date getCreationData();
	
	void setCreationDate( Date creationDate );
	
	Boolean isHidden();
	
	void setHidden( Boolean hidden );
}
