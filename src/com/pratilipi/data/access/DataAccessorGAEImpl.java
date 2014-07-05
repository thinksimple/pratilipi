package com.pratilipi.data.access;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class DataAccessorGAEImpl implements DataAccessor {
	
	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );

	
	private final PersistenceManager pm;
	
	
	public DataAccessorGAEImpl() {
		
		this.pm = pmfInstance.getPersistenceManager();
		
	}

	@Override
	public void destroy() {
		this.pm.close();
	}
	
}
