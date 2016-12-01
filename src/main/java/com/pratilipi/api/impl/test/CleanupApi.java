package com.pratilipi.api.impl.test;

import java.io.IOException;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;

@SuppressWarnings("serial")
@Bind( uri = "/cleanup" )
public class CleanupApi extends GenericApi {
	
	@Get
	public GenericResponse get( GenericRequest request ) throws IOException {
		
		GcsService gcsService = GcsServiceFactory.createGcsService( RetryParams.getDefaultInstance() );
		
		String bucketName = "backup.pratilipi.com";
		
		ListOptions.Builder opt = new ListOptions.Builder();
		opt.setPrefix( "static.pratilipi.com/2015" );
		
		ListResult result = gcsService.list( bucketName, opt.build() );
		
		int count = 0;
		while( result.hasNext() ) {
			String fileName = result.next().getName();
			gcsService.delete( new GcsFilename( bucketName, fileName ) );
			System.out.println( fileName );
			count++;
			if( count == 1000 )
				break;
		}

		return new GenericResponse();
		
	}
	
}
