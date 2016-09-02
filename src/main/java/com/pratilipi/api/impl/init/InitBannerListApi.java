package com.pratilipi.api.impl.init;

import java.util.List;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
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
			this.bannerList = bannerList;
		}
		
	}
	
	
	@Get
	public GenericResponse get( GetRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		return new Response( InitDataUtil.getInitBannerList( request.language ) );
		
	}
	
}
