package com.pratilipi.data.type;

import java.util.Date;

import com.pratilipi.common.type.BatchProcessState;
import com.pratilipi.common.type.BatchProcessType;

public interface BatchProcess extends GenericOfyType {

	Long getId();
	
	BatchProcessType getType();
	
	void setType( BatchProcessType type );

	String getInitDoc();
	
	void setInitDoc( String initDoc );
	
	
	BatchProcessState getStateInProgress();
	
	void setStateInProgress( BatchProcessState state );
	
	BatchProcessState getStateCompleted();
	
	void setStateCompleted( BatchProcessState state );
	

	Date getCreationDate();
	
	void setCreationDate( Date date );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date date );
	
}
