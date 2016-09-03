package com.pratilipi.api.impl.init;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.client.InitBannerData;
import com.pratilipi.data.util.InitDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/init/banner/list" )
public class InitBannerListApi extends GenericApi {
	
	public static class GetRequest extends GenericRequest {
		
		@Validate( required = true )
		private Language language;
		
	}
	
	public static class Response extends GenericResponse {
		
		@SuppressWarnings("unused")
		private List<InitBannerData> bannerList;
		
		
		private Response() {}
		
		private Response( List<InitBannerData> bannerList ) {
			Gson gson = new Gson();
			for( InitBannerData banner : bannerList )
				banner.setApiRequest( gson.fromJson( (String) banner.getApiRequest(), JsonElement.class ).getAsJsonObject() );
			this.bannerList = bannerList;
		}
		
	}
	
	
	@Get
	public GenericResponse get( GetRequest request ) {
		return new Response( InitDataUtil.getInitBannerList( request.language ) );
	}
	
}
