package com.pratilipi.data.type;

import java.lang.reflect.Type;

public interface BatchProcessDoc {
	
	void setData( String name, Object data );
	
	<T> T getData( String name, Type type );
	
}
