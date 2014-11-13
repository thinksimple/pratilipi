package com.pratilipi.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.servlet.ResourceServlet;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;

//Servlet Version: 4.0; Owner Module: PratilipiContent;
@SuppressWarnings("serial")
public class ResourceImageContentServlet extends ResourceServlet {
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		String url = request.getRequestURI();
		String pratilipiIdStr = url
				.substring( 0, url.lastIndexOf( '/' ) + 1 )
				.substring( url.lastIndexOf( '/' ) + 1 );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );

		if( ! PratilipiContentHelper.hasRequestAccessToUpdatePratilipiData( request, pratilipi ) ) {
			response.setStatus( HttpServletResponse.SC_FORBIDDEN );
			return;
		}
		
		super.doPost( request, response );
	}
	
}
