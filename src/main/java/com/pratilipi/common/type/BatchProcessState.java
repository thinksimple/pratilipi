package com.pratilipi.common.type;

public enum BatchProcessState {
	
	INIT								( null,				null ),
	
	GET_USER_IDS_BY_AUTHOR_FILTER		( "authorFilter",	"userIds" ),
	
	CREATE_NOTIFICATIONS_FOR_USER_IDS	( "userIds",		"notificationIds" ),
	
	COMPLETED							( null,				null );
	
	
	private String inputName;
	private String outputName;
	
	private BatchProcessState( String inputName, String outputName ) {
		this.inputName = inputName;
		this.outputName = outputName;
	}
	
	
	public String getInputName() {
		return inputName;
	}
	
	public String getOutputName() {
		return outputName;
	}
	
}