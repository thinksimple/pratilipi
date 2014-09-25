package com.claymus.data.access;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemcacheClaymusImpl implements Memcache {

	private static final Logger logger = 
			Logger.getLogger( Memcache.class.getName() );

	private static final int cacheSize = 128;
	@SuppressWarnings("serial")
	private static final Map<Object, Object> cache
			= new LinkedHashMap<Object, Object>( 16, 0.75f, true ) {
	
		@Override
		protected boolean removeEldestEntry( Map.Entry<Object, Object> eldest ) {
			return size() >= cacheSize;
		}		
	
	};

	public static int putCount = 0;
	public static int hitCount = 0;
	public static int missCount = 0;
	
	
	@SuppressWarnings("unchecked")
	public <K, T extends Serializable> T get( K key ) {
		T value = (T) cache.get(key);

		if( value != null )
			hitCount++;
		else
			missCount++;
		
		logStats();

		return value;
	}

	public <K, T extends Serializable> void put(
			K key, T value, long expirationDeltaMillis ) {

		cache.put( key, value );
		putCount++;

		logStats();
	}

	public <K, T extends Serializable> void put( K key, T value ) {
		cache.put(key, value);
		putCount++;

		logStats();
	}

	public <K> void remove( K key ) {
		cache.remove( key );
	}

	public void flush() {
		cache.clear();
	}
	
	public void logStats() {
		logger.log( Level.INFO, "Cache stats:\n"
				+ "\tPut Count = " + putCount
				+ "\tHit Count = " + hitCount
				+ "\tMiss Count = " + missCount
				+ "\tHit/Put = " + (double) hitCount / (double) putCount
				+ "\tHit Ratio = " + (double) hitCount / (double) ( hitCount + missCount )
				);
	}

}
