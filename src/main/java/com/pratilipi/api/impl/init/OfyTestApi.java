package com.pratilipi.api.impl.init;

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
import com.pratilipi.common.type.AccessType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GetOfyResponse get( GetOfyRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		Gson gson = new Gson();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( 6237336205524992L );
		
		AuditLog auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );
		
		pratilipi.setReadCount( 59L );
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		
		
		
		pratilipi = dataAccessor.getPratilipi( 6317189579669504L );
		
		auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );
		
		pratilipi.setReadCount( 53L );
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		
		
		
		pratilipi = dataAccessor.getPratilipi( 5077420224806912L );
		
		auditLog = dataAccessor.newAuditLogOfy();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
		auditLog.setEventDataOld( gson.toJson( pratilipi ) );
		
		pratilipi.setReadCount( 1996L );
		auditLog.setEventDataNew( gson.toJson( pratilipi ) );
		pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		
		
		return new GetOfyResponse( "done" );
		
	}
	
}
