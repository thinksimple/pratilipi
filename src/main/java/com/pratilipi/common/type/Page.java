package com.pratilipi.common.type;

import java.io.Serializable;
import java.util.Date;

public interface Page extends Serializable {

	Long getId();

	String getType();

	void setType( String type );

	String getUri();

	void setUri( String uri );

	String getUriAlias();

	void setUriAlias( String uri );

	Long getPrimaryContentId();

	void setPrimaryContentId( Long pageContentId );

	Date getCreationDate();

	void setCreationDate( Date creationDate );

}