package com.claymus.client;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class InitializationException extends RuntimeException implements IsSerializable {

	public InitializationException() {
		super( "Application failed to initialize." );
	}

	public InitializationException( Throwable throwable ) {
		super( "Application failed to initialize.", throwable );
	}

	public InitializationException( String msg ) {
		super( msg );
	}

}
