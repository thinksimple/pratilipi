package com.pratilipi.commons.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import org.apache.commons.io.IOUtils;

import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.pratilipi.data.access.DataAccessorFactory;

public class ConvertWordToHtml {
	
	private static final Logger logger = 
			Logger.getLogger( ConvertWordToHtml.class.getName() );
	
	// TODO: Move these credentials to a more secure location
	private static final String SERVICE_ACCOUNT_EMAIL =
			"562691904657-ef96v23igs7p4rb49fdhshcd2udev71s@developer.gserviceaccount.com";
	private static final String SERVICE_ACCOUNT_PKCS12_FILE_PATH =
			"WEB-INF/PratilipiDevo-8a02ceae5f67.p12";
	
	private static Drive driveService;
	
	private Long pratilipiId;
	
	public ConvertWordToHtml( Long pratilipiId ){
		this.pratilipiId = pratilipiId;
	}
	
	public void convert() throws IOException{
		if( driveService == null )
			driveService = getDriveService();
		
		if( driveService == null ) {
			//TODO : THROWS 500 ERROR. AND REMOVE IOException FROM FUNCTION DEFINITION.
			return;
		}
		

		// Fetching word content blob store
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		String fileName = PratilipiHelper.getContentWord( pratilipiId );
		BlobEntry blobEntry = blobAccessor.getBlob( fileName );


		// Uploading word content to Drive
		File file = new File();
		ByteArrayContent mediaContent = new ByteArrayContent(
				blobEntry.getMimeType(), blobEntry.getData() );
		file = driveService.files().insert( file, mediaContent )
				.setConvert( true ).execute();
		
		
		logger.log( Level.INFO, "File created on Drive -\n"
				+ "\tFile Id: " + file.getId() + "\n"
				+ "\tMimeType: " + file.getMimeType() );
		
		
		// Downloading exported html content from Drive
		GenericUrl url = new GenericUrl(file.getExportLinks().get( "text/html" ));
		HttpResponse resp = driveService.getRequestFactory().buildGetRequest( url ).execute();
		InputStream inputStream = resp.getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy( inputStream, writer );
		String html = writer.toString();

		
		// Deleting word content on Drive
		driveService.files().delete( file.getId() ).execute();
		
		
		logger.log( Level.INFO, "Raw content length: " + html.length() );

		
		// Processing html content to Pratilipi content
		Matcher matcher =  PratilipiHelper.REGEX_HTML_BODY.matcher( html );
		if( matcher.matches() ) {
			logger.log( Level.INFO, "Discarding pre-<body> content (lenght: " + matcher.group( 1 ).length() + "):\n" + matcher.group( 1 ) );
			logger.log( Level.INFO, "Discarding post-</body> content (lenght: " + matcher.group( 3 ).length() + "):\n" + matcher.group( 3 ) );
			html = matcher.group( 2);
		}
		
		
		System.out.println( "Processed content lenght: " + html.length() );
				  

		// Saving Pratilipi content to blob store
		blobEntry = blobAccessor.newBlob(
				PratilipiHelper.getContent( pratilipiId ),
				html.getBytes( Charset.forName( "UTF-8" ) ),
				"text/html", "private" );
		blobAccessor.createOrUpdateBlob( blobEntry );
	}
	
	private Drive getDriveService() throws IOException {
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			JacksonFactory jsonFactory = new JacksonFactory();
			LinkedList<String> scopes = new LinkedList<>();
			scopes.add( DriveScopes.DRIVE );
			GoogleCredential credential = new GoogleCredential.Builder()
					.setTransport( httpTransport )
					.setJsonFactory( jsonFactory )
					.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
					.setServiceAccountPrivateKeyFromP12File(
							new java.io.File( SERVICE_ACCOUNT_PKCS12_FILE_PATH ) )
					.setServiceAccountScopes( scopes )
					.build();
			
			return new Drive.Builder( httpTransport, jsonFactory, null )
					.setHttpRequestInitializer(credential).build();
		} catch ( GeneralSecurityException e ) {
			logger.log( Level.SEVERE, "Failed to create credentials for Google Drive access !", e );
			return null;
		}
		
	}
}
