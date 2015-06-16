package com.pratilipi.data;

import com.pratilipi.common.util.AppProperty;

public class DataAccessorFactory {

	private static final String datasource = AppProperty.get( "datasource" );

	private static final Memcache cacheL1 = new MemcacheClaymusImpl();
	private static final Memcache cacheL2 = new MemcacheGaeImpl();
	private static final ThreadLocal<DataAccessor> threadLocalDataAccessor = new ThreadLocal<>();

	
	public static Memcache getL1CacheAccessor() {
		return cacheL1;
	}

	public static Memcache getL2CacheAccessor() {
		return cacheL2;
	}
	
	public static DataAccessor getDataAccessor() {
		DataAccessor dataAccessor = threadLocalDataAccessor.get();
		if( dataAccessor == null ) {
			dataAccessor = datasource.equals( "gae" ) ? new DataAccessorGaeImpl() : new DataAccessorMockImpl();
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, cacheL2 );
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, new MemcacheClaymusImpl() );
			threadLocalDataAccessor.set( dataAccessor );
		}
		return dataAccessor;
	}
	
}
