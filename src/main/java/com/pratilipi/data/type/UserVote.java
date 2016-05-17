package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.VoteType;

public interface UserVote extends GenericOfyType {

	String getId();
	
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	String getContentId();
	
	void setContentId( String contentId );
	
	
	VoteType getVote();
	
	void setVote( VoteType vote );
	
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

}