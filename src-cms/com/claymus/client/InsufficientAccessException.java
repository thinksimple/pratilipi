package com.claymus.client;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class InsufficientAccessException extends Exception implements IsSerializable {

	public InsufficientAccessException() {
		super( "User does not have enough access to perform this operation." );
	}

	public InsufficientAccessException( String msg ) {
		super( msg );
	}

}
