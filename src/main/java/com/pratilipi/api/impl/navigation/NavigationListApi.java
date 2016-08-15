package com.pratilipi.api.impl.navigation;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.client.NavigationData;
import com.pratilipi.data.util.NavigationDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/navigation/list" )
public class NavigationListApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {
		
		@Validate( required = true )
		private Language language;
		
		
		public Language getLanguage() {
			return this.language;
		}
		
	}
	
	public static class Response extends GenericResponse {

		private List<NavigationData> navigationList;
		
		
		public Response( List<NavigationData> navigationList ) {
			this.navigationList = navigationList;
		}

	}

	
	@Get
	public Response get( GetRequest request ) throws UnexpectedServerException {
		
		return new Response( NavigationDataUtil.getNavigationDataList( request.language ) );
		
	}

}
