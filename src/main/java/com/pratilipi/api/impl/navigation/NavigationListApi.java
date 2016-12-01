package com.pratilipi.api.impl.navigation;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.client.NavigationData;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.data.util.NavigationDataUtil;
import com.pratilipi.filter.UxModeFilter;

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

		@SuppressWarnings("unused")
		private List<NavigationData> navigationList;
		
		
		public Response( List<NavigationData> navigationList ) {
			this.navigationList = navigationList;
		}

	}

	
	@Get
	public Response get( GetRequest request ) throws UnexpectedServerException {
		
		Gson gson = new Gson();
		
		List<NavigationData> navigationList = NavigationDataUtil.getNavigationDataList( request.language );
		
		if( UxModeFilter.isAndroidApp() ) {
			
			for( NavigationData navigationData : navigationList ) {
				for( Navigation.Link link : navigationData.getLinkList() ) {
					if( link.getApiRequest() == null )
						continue;
					JsonObject apiRequest = gson.fromJson(
							(String) link.getApiRequest(),
							JsonElement.class ).getAsJsonObject();
					link.setApiRequest( apiRequest );
				}
			}
		
			if( navigationList.size() > 2 ) {
				for( Navigation.Link link : navigationList.get( 2 ).getLinkList() )
					navigationList.get( 0 ).addLink( link );
				navigationList = navigationList.subList( 0, 2 );
			}

			NavigationData navigationData = navigationList.remove( 0 );
			navigationList.add( navigationData );
		
		} else {
			
			for( NavigationData navigationData : navigationList ) {
				for( Navigation.Link link : navigationData.getLinkList() ) {
					link.setApiName( null );
					link.setApiRequest( null );
				}
			}
			
		}
		
		return new Response( navigationList );
		
	}

}
