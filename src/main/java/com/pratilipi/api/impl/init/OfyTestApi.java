package com.pratilipi.api.impl.init;

import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/ofy" )
public class OfyTestApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( OfyTestApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) throws InvalidArgumentException, InsufficientAccessException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( 4809728372768768L );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiFilter, null, null ).getDataList();
		
		
		Gson gson = new Gson();
		
		for( Pratilipi pratilipi : pratilipiList ) {
			AuditLog auditLog = dataAccessor.newAuditLogOfy();
			auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
			auditLog.setAccessType( AccessType.AUTHOR_UPDATE );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			
			pratilipi.setAuthorId( 5734588399747072L );
			
			auditLog.setEventDataNew( gson.toJson( pratilipi ) );

			dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
		}
		
		return new GenericResponse();
		
	}
	
}