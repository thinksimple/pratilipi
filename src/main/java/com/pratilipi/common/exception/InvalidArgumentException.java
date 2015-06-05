package com.pratilipi.common.exception;


@SuppressWarnings("serial")
public class InvalidArgumentException extends Exception {

	@SuppressWarnings("unused")
	private InvalidArgumentException() {}
	
	public InvalidArgumentException( String msg ) {
		super( msg );
	}

}
