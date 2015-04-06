package com.pratilipi.servlet;

import com.claymus.api.ApiRegistry;
import com.claymus.servlet.ClaymusService;
import com.pratilipi.api.DebugApi;
import com.pratilipi.api.InitApi;
import com.pratilipi.api.OAuthApi;
import com.pratilipi.api.PurchaseApi;
import com.pratilipi.pagecontent.author.api.AuthorApi;
import com.pratilipi.pagecontent.author.api.AuthorImageApi;
import com.pratilipi.pagecontent.author.api.AuthorListApi;
import com.pratilipi.pagecontent.author.api.AuthorProcessApi;
import com.pratilipi.pagecontent.language.api.LanguageListApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiContentApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiContentImageApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiCoverApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiListApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiProcessApi;
import com.pratilipi.pagecontent.pratilipi.api.PratilipiResourceApi;
import com.pratilipi.pagecontent.userpratilipi.api.UserPratilipiApi;

@SuppressWarnings("serial")
public class PratilipiService extends ClaymusService {
	
	static {
		ApiRegistry.register( DebugApi.class );
		ApiRegistry.register( InitApi.class );
		ApiRegistry.register( OAuthApi.class );
		ApiRegistry.register( PurchaseApi.class );
		
		ApiRegistry.register( PratilipiApi.class );
		ApiRegistry.register( PratilipiListApi.class );
		ApiRegistry.register( PratilipiCoverApi.class );
		ApiRegistry.register( PratilipiContentApi.class );
		ApiRegistry.register( PratilipiContentImageApi.class );
		ApiRegistry.register( PratilipiResourceApi.class );
		ApiRegistry.register( PratilipiProcessApi.class );
		ApiRegistry.register( UserPratilipiApi.class );

		ApiRegistry.register( AuthorApi.class );
		ApiRegistry.register( AuthorListApi.class );
		ApiRegistry.register( AuthorImageApi.class );
		ApiRegistry.register( AuthorProcessApi.class );
		
		ApiRegistry.register( LanguageListApi.class );
	}

}
