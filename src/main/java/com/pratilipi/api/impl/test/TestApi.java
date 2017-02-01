package com.pratilipi.api.impl.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListIterator;
import com.pratilipi.data.RtdbAccessor;
import com.pratilipi.data.type.Email;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.EmailEntity;

@SuppressWarnings( "serial" )
@Bind( uri = "/test" )
public class TestApi extends GenericApi {

	private static final Logger logger = Logger.getLogger( TestApi.class.getName() );

	@Get
	public static GenericResponse get( GenericRequest request ) throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		RtdbAccessor rtdbAccessor = DataAccessorFactory.getRtdbAccessor();
		List<Long> userIds = new ArrayList<>( rtdbAccessor.getUserPreferences( 24 ).keySet() );
		
		// Method 1 - get emails of all users from above
		Long x = new Date().getTime();
		for( Long userId : userIds ) {
			List<Email> emailList = dataAccessor.getEmailList( userId, null, (String) null, null, 1000 ); // safe assumption - 1000 emails per user
		}

		Long y = new Date().getTime();
		
		logger.log( Level.INFO, "Time taken for method 1 = " + ( y-x ) + " ms." );
		
		
		// Method 2
		
		DataListIterator<Email> itr = new DataListIterator<Email>( ObjectifyService.ofy().load()
										.type( EmailEntity.class )
										.filter( "STATE", EmailState.SENT )
										.chunk( 1000 )
										.iterator() );
		
		int count = 0;
		Set<Long> userSet = new HashSet<>();
		while( itr.hasNext() ) {
			Email email = itr.next();
			userSet.add( email.getUserId() );
			count++;
		}
		
		logger.log( Level.INFO, "Email Entities size : " + count );
		logger.log( Level.INFO, "UserSet size: " + userSet.size() );
		
		Map<Long, User> userMap  = dataAccessor.getUsers( userSet );
		
		Long z = new Date().getTime();
		
		logger.log( Level.INFO, "Time taken for method 2 = " + ( z-y ) + " ms." );
		
		// Finding out count
		
		int number = ObjectifyService.ofy().load()
				.type( EmailEntity.class )
				.filter( "STATE", EmailState.SENT )
				.count();
		
		Long a = new Date().getTime();
		
		
		logger.log( Level.INFO, "Time taken for finding count = " + ( a-z ) + " ms." );
		
		return new GenericResponse();

	}

}
