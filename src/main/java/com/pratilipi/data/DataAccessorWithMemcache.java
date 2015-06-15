package com.pratilipi.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.type.PageType;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;

public class DataAccessorWithMemcache implements DataAccessor {
	
	private final static String PREFIX_PAGE = "Page-";
	private static final String PREFIX_PRATILIPI = "Pratilipi-";
	
	private final DataAccessor dataAccessor;
	private final Memcache memcache;
	
	
	public DataAccessorWithMemcache(
			DataAccessor dataAccessor, Memcache memcache ) {

		this.dataAccessor = dataAccessor;
		this.memcache = memcache;
	}
	
	
	// PAGE Table
	
	@Override
	public Page newPage() {
		return dataAccessor.newPage();
	}
	
	@Override
	public Page getPage( Long id ) {
		Page page = memcache.get( PREFIX_PAGE + id );
		if( page == null ) {
			page = dataAccessor.getPage( id );
			if( page != null )
				memcache.put( PREFIX_PAGE + id, page );
		}
		return page;
	}
	
	@Override
	public Page getPage( String uri ) {
		Page page = memcache.get( PREFIX_PAGE + uri );
		if( page == null ) {
			page = dataAccessor.getPage( uri );
			if( page != null )
				memcache.put( PREFIX_PAGE + uri, page );
		}
		return page;
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		Page page = memcache.get( PREFIX_PAGE + pageType + "-" + primaryContentId );
		if( page == null ) {
			page = dataAccessor.getPage( pageType, primaryContentId );
			if( page != null )
				memcache.put( PREFIX_PAGE + pageType + "-" + primaryContentId, page );
		}
		return page;
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		page = dataAccessor.createOrUpdatePage( page );
		memcache.put( PREFIX_PAGE + page.getId(), page );
		if( page.getUri() != null )
			memcache.put( PREFIX_PAGE + page.getUri(), page );
		if( page.getUriAlias() != null )
			memcache.put( PREFIX_PAGE + page.getUriAlias(), page );
		if( page.getPrimaryContentId() != null )
			memcache.put( PREFIX_PAGE + page.getType() + "-" + page.getPrimaryContentId(), page );
		return page;
	}


	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return dataAccessor.newPratilipi();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		Pratilipi pratilipi = memcache.get( PREFIX_PRATILIPI + id );
		if( pratilipi == null ) {
			pratilipi = dataAccessor.getPratilipi( id );
			if( pratilipi != null )
				memcache.put( PREFIX_PRATILIPI + id, pratilipi );
		}
		return pratilipi;
	}

	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		if( idList.size() == 0 )
			return new ArrayList<>( 0 );
		
		
		List<String> memcacheKeyList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			memcacheKeyList.add( PREFIX_PRATILIPI + id );
		Map<String, Pratilipi> memcacheKeyEntityMap = memcache.getAll( memcacheKeyList );

		
		List<Long> missingIdList = new LinkedList<>();
		for( Long id : idList )
			if( memcacheKeyEntityMap.get( PREFIX_PRATILIPI + id ) == null )
				missingIdList.add( id );
		List<Pratilipi> missingPratilipiList = dataAccessor.getPratilipiList( missingIdList );

		
		List<Pratilipi> pratilipiList = new ArrayList<>( idList.size() );
		for( Long id : idList ) {
			Pratilipi pratilipi = memcacheKeyEntityMap.get( PREFIX_PRATILIPI + id );
			if( pratilipi == null ) {
				pratilipi = missingPratilipiList.remove( 0 );
				if( pratilipi != null )
					memcache.put( PREFIX_PRATILIPI + id, pratilipi );
			}
			pratilipiList.add( pratilipi );
		}
		
		
		return pratilipiList;
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi );
		memcache.put( PREFIX_PRATILIPI + pratilipi.getId(), pratilipi );
		return pratilipi;
	}
	
	
	// Destroy
	
	@Override
	public void destroy() {
		dataAccessor.destroy();
	}

}
