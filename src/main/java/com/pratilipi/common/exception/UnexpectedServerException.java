package com.pratilipi.common.exception;


@SuppressWarnings("serial")
public class UnexpectedServerException extends Exception {

	public UnexpectedServerException() {
		super( "Some exception occured at server. Please try again." );
	}

	public UnexpectedServerException( String msg ) {
		super( msg );
	}

}
