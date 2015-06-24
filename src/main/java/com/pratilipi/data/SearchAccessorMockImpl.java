package com.pratilipi.data;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;

public class SearchAccessorMockImpl implements SearchAccessor {

	// Constructor
	
	public SearchAccessorMockImpl( String indexName ) {}


	// PRATILIPI Data
	
	@Override
	public DataListCursorTuple<Long> searchPratilipi( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		List<Long> pratilipiIdList = new LinkedList<>();
		
		for( Object object : MockData.GLOBAL_INDEX ) {
			if( object.getClass() != PratilipiData.class )
				continue;

			PratilipiData pratilipiData = (PratilipiData) object;
			
			if( pratilipiFilter.getLanguage() != null
					&& !pratilipiFilter.getLanguage().equals( pratilipiData.getLanguage() ) )
				continue;
			
			if( pratilipiFilter.getAuthorId() != null
					&& !pratilipiFilter.getAuthorId().equals( pratilipiData.getAuthorId() ) )
				continue;
			
			if( pratilipiFilter.getType() != null
					&& !pratilipiFilter.getType().equals( pratilipiData.getType() ) )
				continue;
			
			pratilipiIdList.add( ( (PratilipiData) object ).getId() );
		}
		
		return new DataListCursorTuple<Long>( pratilipiIdList, null );
	}

	@Override
	public void indexPratilipiData( PratilipiData pratilipiData ) throws UnexpectedServerException {
		MockData.GLOBAL_INDEX.add( pratilipiData );
	}

	@Override
	public void indexPratilipiDataList( List<PratilipiData> pratilipiDataList ) throws UnexpectedServerException {
		MockData.GLOBAL_INDEX.addAll( pratilipiDataList );
	}

	@Override
	public void deletePratilipiDataIndex( Long pratilipiId ) {
		for( Object object : MockData.GLOBAL_INDEX ) {
			if( object.getClass() == PratilipiData.class ) {
				if( ( (PratilipiData) object ).getId() == pratilipiId ) {
					MockData.GLOBAL_INDEX.remove( object );
					break;
				}
			}
		}
	}

	
	// AUTHOR Data
	
	@Override
	public void indexAuthorData( AuthorData authorData ) throws UnexpectedServerException {
		// TODO: Implementation
	}

}
