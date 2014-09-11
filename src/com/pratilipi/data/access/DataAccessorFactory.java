package com.pratilipi.data.access;

public class DataAccessorFactory
		extends com.claymus.data.access.DataAccessorFactory {

	public static DataAccessor getDataAccessor() {
		return new DataAccessorGaeImpl();
	}
	
}
