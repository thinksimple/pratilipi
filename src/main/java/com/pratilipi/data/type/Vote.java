package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.VoteParentType;
import com.pratilipi.common.type.VoteType;

public interface Vote extends GenericOfyType {

	String getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	VoteParentType getParentType();
	
	void setParentType( VoteParentType parentType );
	
	String getParentId();
	
	void setParentId( String parentId );
	
	
	VoteType getType();
	
	void setType( VoteType vote );
	
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

}