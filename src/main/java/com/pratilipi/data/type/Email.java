package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.EmailState;
import com.pratilipi.common.type.EmailType;

public interface Email extends GenericOfyType {

	Long getId();

	Long getUserId();

	void setUserId( Long userId );


	EmailType getType();

	void setType( EmailType type );

	Long getPrimaryContentId();

	void setPrimaryContentId( Long primaryContentId );


	EmailState getState();

	void setState( EmailState state );


	Date getCreationDate();

	void setCreationDate( Date date );

}
