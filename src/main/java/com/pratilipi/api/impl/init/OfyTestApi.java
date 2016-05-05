package com.pratilipi.api.impl.init;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetOfyRequest;
import com.pratilipi.api.impl.init.shared.GetOfyResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GetOfyResponse get( GetOfyRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		String appPropertyId = "Api.PratilipiProcess.UpdateStats";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 1420051500000L ) ); // 01 Jan 2015, 12:15 AM IST
		}
		
		Date date = (Date) appProperty.getValue();
		Gson gson = new Gson();
		
		String msg = "";
		
		while( date.getTime() < new Date().getTime() ) {
			
			DateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
			formatter.setTimeZone( TimeZone.getTimeZone( "IST" ) );
			String dateStr = formatter.format( new Date( date.getTime() - TimeUnit.MINUTES.toMillis( 15 ) ) );

			msg += dateStr + "\n";
			
			date = new Date( date.getTime() + TimeUnit.DAYS.toMillis( 1 ) );
			
		}
		
		return new GetOfyResponse( msg );
		
	}
	
}
