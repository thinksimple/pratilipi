package com.pratilipi.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.gae.PratilipiEntity;


public class DataAccessorGaeImpl implements DataAccessor {

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );

	protected final PersistenceManager pm;
	
	
	// Constructor

	public DataAccessorGaeImpl() {
		this.pm = pmfInstance.getPersistenceManager();
	}


	// Helper Methods
	
	private <T> T getEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		return pm.detachCopy( entity );
	}

	private <T> T createOrUpdateEntity( T entity ) {
		entity = pm.makePersistent( entity );
		return pm.detachCopy( entity );
	}
	
	private <T> List<T> createOrUpdateEntityList( List<T> entityList ) {
		entityList = (List<T>) pm.makePersistentAll( entityList );
		return (List<T>) pm.detachCopyAll( entityList );
	}
	
	private <T> void deleteEntity( Class<T> clazz, Object id ) {
		T entity = (T) pm.getObjectById( clazz, id );
		pm.deletePersistent( entity );
	}

	
	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		try {
			return getEntity( PratilipiEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		List<Pratilipi> pratilipiList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			pratilipiList.add( getPratilipi( id ) );
		return pratilipiList;
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		return createOrUpdateEntity( pratilipi );
	}
	
	
	
	@Override
	public void destroy() {
		pm.close();
	}

}
