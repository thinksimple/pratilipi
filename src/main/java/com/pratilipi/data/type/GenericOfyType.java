package com.pratilipi.data.type;

import com.googlecode.objectify.Key;

public interface GenericOfyType {

	<T> void setKey( Key<T> key );
	
}
