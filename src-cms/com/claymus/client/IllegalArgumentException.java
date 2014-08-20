package com.claymus.client;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class IllegalArgumentException extends Exception implements IsSerializable {

	public IllegalArgumentException( String msg ) {
		super( msg );
	}

}
