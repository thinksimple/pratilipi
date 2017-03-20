package com.pratilipi.api.impl.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListIterator;
import com.pratilipi.data.type.Email;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );

	public static class GetRequest extends GenericRequest {

		@Validate( required = true )
		private Integer resultCount;

	}

	@Get
	public static GenericResponse get( GetRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		DataListIterator<Email> it = dataAccessor.getEmailListIteratorForStatePending( null, true );
		List<Email> emailList = new ArrayList<>();
		while( it.hasNext() ) {
			emailList.add( it.next() );
			if( emailList.size() >= request.resultCount ) break;
		}

		List<Email> emailListToBeDropped = new ArrayList<>();
		for( Email email : emailList ) {
			System.out.println( email.getScheduledDate() );
			if( ! _isToday( email.getScheduledDate() ) ) {
				email.setState( EmailState.DROPPED );
				email.setLastUpdated( new Date() );
				emailListToBeDropped.add( email );
			}
		}

		logger.log( Level.INFO, "Dropping " + emailListToBeDropped.size() + " emails." );
		emailListToBeDropped = dataAccessor.createOrUpdateEmailList( emailListToBeDropped );

		return new GenericResponse();

	}
	
	private static boolean _isToday( Date date ) {
		Long time = new Date().getTime();
		time = time - time % TimeUnit.DAYS.toMillis( 1 ); // 00:00 AM GMT
		time = time - TimeUnit.MINUTES.toMillis( 330 ); // 00:00 AM IST
		return date.getTime() > time;
	}

}
