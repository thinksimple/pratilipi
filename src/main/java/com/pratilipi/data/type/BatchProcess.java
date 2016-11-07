package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.ProcessState;
import com.pratilipi.common.type.ProcessType;

public interface BatchProcess extends GenericOfyType {

	Long getId();
	
	ProcessType getType();
	
	void setType( ProcessType type );

	String getInitDoc();
	
	void setInitDoc( String initDoc );
	
	
	ProcessState getStateInProgress();
	
	void setStateInProgress( ProcessState state );
	
	ProcessState getStateCompleted();
	
	void setStateCompleted( ProcessState state );
	

	Date getCreationDate();
	
	void setCreationDate( Date date );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date date );
	
}
