package com.claymus.data.access;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class BlobAccessorGcsImpl implements BlobAccessor {

	private static final Logger logger = 
			Logger.getLogger( BlobAccessorGcsImpl.class.getName() );

	private static final GcsService gcsService =
			GcsServiceFactory.createGcsService( RetryParams.getDefaultInstance() );

	private static final int BUFFER_SIZE = 1024 * 1024;
	
	private final String bucketName;
	
	
	public BlobAccessorGcsImpl( String bucketName ) {
		this.bucketName = bucketName;
	}
	
	
	@Override
	public String createUploadUrl( String fileName ) {
		return "/service.upload/" + fileName;
	}

	@Override
	public boolean createBlob( HttpServletRequest request ) {
		
		boolean blobCreated = false;
		
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iterator = upload.getItemIterator( request );
			while( iterator.hasNext() ) {
				
				FileItemStream fileItemStream = iterator.next();
				InputStream inputStream = fileItemStream.openStream();
	
				if( fileItemStream.isFormField() ) {
					logger.log(
							Level.WARNING,
							"Ignoring form field -"
									+ "\n\tField Name: " + fileItemStream.getFieldName() );
	
				} else if( blobCreated ) {
					logger.log(
							Level.WARNING,
							"A blob is already created ! Ignoring uploaded file -"
									+ "\n\tField Name: " + fileItemStream.getFieldName()
									+ "\n\tFile Name: " + fileItemStream.getName() );
				} else {
					logger.log(
							Level.INFO,
							"Got an uploaded file: "
									+ "\n\tField Name: " + fileItemStream.getFieldName()
									+ "\n\tFile Name: " + fileItemStream.getName() );
	
					String fileName = request.getRequestURI().substring( 16 );
					GcsFilename gcsFileName
							= new GcsFilename( bucketName, fileName );
					GcsFileOptions gcsFileOptions
							= new GcsFileOptions.Builder()
									.mimeType( fileItemStream.getContentType() )
									.addUserMetadata( "ORIGINAL_NAME", fileItemStream.getName() )
									.build();
					GcsOutputChannel gcsOutputChannel
							= gcsService.createOrReplace( gcsFileName , gcsFileOptions );
					
					int length;
					byte[] buffer = new byte[BUFFER_SIZE];
					while( ( length = inputStream.read( buffer, 0, buffer.length ) ) != -1 )
						gcsOutputChannel.write( ByteBuffer.wrap( buffer, 0, length ) );
					
					gcsOutputChannel.close();
					
					blobCreated = true;
				}
			}
		
		} catch( IOException | FileUploadException e ) {
			if( blobCreated )
				logger.log(
						Level.SEVERE,
						"Exception occured but the blob was created successfully !",
						e );
			else
				logger.log( Level.SEVERE, "Failed to ceate blob !", e );
		}
		
		return blobCreated;
	}

	@Override
	public void serveBlob(
			String fileName,
			HttpServletResponse response ) throws IOException {

		GcsFilename gcsFileName
				= new GcsFilename( bucketName, fileName );
		GcsFileMetadata gcsFileMetadata
				= gcsService.getMetadata( gcsFileName );
		GcsInputChannel gcsInputChannel
				= gcsService.openReadChannel( gcsFileName, 0 );
		
		ByteBuffer byteBuffer
				= ByteBuffer.allocate( (int) gcsFileMetadata.getLength() );
		gcsInputChannel.read( byteBuffer );
	
		response.setContentType( gcsFileMetadata.getOptions().getMimeType() );
		response.getOutputStream().write( byteBuffer.array() );
		response.getOutputStream().close();
	}
	
}
