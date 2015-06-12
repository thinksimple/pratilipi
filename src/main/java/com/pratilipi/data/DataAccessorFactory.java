package com.pratilipi.data;

public class DataAccessorFactory {
	
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
			dataAccessor = new DataAccessorGaeImpl();
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, cacheL2 );
			dataAccessor = new DataAccessorWithMemcache( dataAccessor, new MemcacheClaymusImpl() );
			threadLocalDataAccessor.set( dataAccessor );
		}
		return dataAccessor;
	}
	
}
