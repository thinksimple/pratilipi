package com.pratilipi.data.access;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.access.MemcacheClaymusImpl;

public class DataAccessorFactory
		extends com.claymus.data.access.DataAccessorFactory {

	public static DataAccessor getDataAccessor( HttpServletRequest request ) {
		DataAccessor dataAccessor = cacheL1.get( "PratilipiDataAccessor-" + request.hashCode() );
		if( dataAccessor == null ) {
			dataAccessor = new DataAccessorGaeImpl();
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, cacheL2 );
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, new MemcacheClaymusImpl() );
			cacheL1.put( "DataAccessor-" + request.hashCode(), dataAccessor );
			cacheL1.put( "PratilipiDataAccessor-" + request.hashCode(), dataAccessor );
		}
		return dataAccessor;
	}
	
}
