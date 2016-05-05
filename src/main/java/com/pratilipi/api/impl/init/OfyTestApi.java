package com.pratilipi.api.impl.init;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetOfyRequest;
import com.pratilipi.api.impl.init.shared.GetOfyResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GetOfyResponse get( GetOfyRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		DateFormat formatter = new SimpleDateFormat( "yyyy-dd-mm HH:mm:ss z" );    
		formatter.setTimeZone( TimeZone.getTimeZone( "IST" ) );
		String dateStr = formatter.format( new Date() );
		
		return new GetOfyResponse( dateStr );
		
	}
	
}
