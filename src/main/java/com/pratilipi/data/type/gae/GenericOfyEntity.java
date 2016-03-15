package com.pratilipi.data.type.gae;

import com.googlecode.objectify.Key;

public abstract class GenericOfyEntity {

	public abstract <T extends GenericOfyEntity> void setKey( Key<T> key );
	
}
