package com.pratilipi.data.access;

public class DataAccessorFactory {

	public static DataAccessor get() {
		
		return new DataAccessorGAEImpl();
	
	}
	
}
