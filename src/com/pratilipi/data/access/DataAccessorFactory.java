package com.pratilipi.data.access;

import com.claymus.data.access.Memcache;
import com.claymus.data.access.MemcacheGaeImpl;

public class DataAccessorFactory
		extends com.claymus.data.access.DataAccessorFactory {

	private static Memcache memcache;
	
	public static DataAccessor getDataAccessor() {
		if( memcache == null )
			memcache = new MemcacheGaeImpl();
		return new DataAccessorWithMemcache( new DataAccessorGaeImpl(), memcache );
	}
	
}
