package com.pratilipi.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.pratilipi.common.type.PageType;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;


public class DataAccessorGaeImpl implements DataAccessor {

	private static final Logger logger = Logger.getGlobal();

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory( "transactions-optional" );

	private final PersistenceManager pm;
	
	
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

	
	// PAGE Table
	
	@Override
	public Page newPage() {
		return new PageEntity();
	}
	
	@Override
	public Page getPage( Long id ) {
		return getEntity( PageEntity.class, id );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getPage( String uri ) {
		Query query = new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "uriAlias", uri )
						.addOrdering( "creationDate", true )
						.build();

		List<Page> pageList = (List<Page>) query.execute( uri );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for uri " + uri );
		
		if( pageList.size() != 0 )
			return pm.detachCopy( pageList.get( 0 ) );

		
		query = new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "uri", uri )
						.addOrdering( "creationDate", true )
						.build();

		pageList = (List<Page>) query.execute( uri );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for uri alias " + uri );

		return pageList.size() == 0 ? null : pm.detachCopy( pageList.get( 0 ) );
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PageEntity.class ) )
						.addFilter( "type", pageType )
						.addFilter( "primaryContentId", primaryContentId )
						.addOrdering( "creationDate", true )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Page> pageList = (List<Page>) query.execute( pageType, primaryContentId );
		if( pageList.size() > 1 )
			logger.log( Level.SEVERE, "More than one page entities found for PageType " + pageType + ", PageContent " + primaryContentId );
		return pageList.size() == 0 ? null : pm.detachCopy( pageList.get( 0 ) );
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		return createOrUpdateEntity( page );
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
	
	
	// Destroy

	@Override
	public void destroy() {
		pm.close();
	}

}
