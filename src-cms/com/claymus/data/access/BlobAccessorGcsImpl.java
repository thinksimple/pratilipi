package com.claymus.data.access;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.claymus.data.access.gcs.BlobEntryGcsImpl;
import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFileOptions.Builder;
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
	public boolean createBlob( HttpServletRequest request, String fileName ) {
		
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
	public void createBlob( String fileName, String mimeType, byte[] bytes )
			throws IOException {
		
		GcsFilename gcsFileName
				= new GcsFilename( bucketName, fileName );
		
		Builder builder = new GcsFileOptions.Builder();
		if( mimeType != null )
			builder.mimeType( mimeType );
		GcsFileOptions gcsFileOptions = builder.build();
		
		GcsOutputChannel gcsOutputChannel = gcsService
				.createOrReplace( gcsFileName , gcsFileOptions );
		
		gcsOutputChannel.write( ByteBuffer.wrap( bytes ) );
		gcsOutputChannel.close();
	}

	@Override
	public void createBlob( String fileName, String mimeType, String content, Charset charset )
				throws IOException {
		
		createBlob( fileName, mimeType, content.getBytes( charset ) );
	}

	@Override
	public void updateBlob( BlobEntry blobEntry, byte[] bytes )
			throws IOException {
		
		GcsFilename gcsFileName
				= new GcsFilename( bucketName, blobEntry.getName() );

		Builder builder = new GcsFileOptions.Builder();
		if( blobEntry.getMimeType() != null )
			builder.mimeType( blobEntry.getMimeType() );
		GcsFileOptions gcsFileOptions = builder.build();

		GcsOutputChannel gcsOutputChannel = gcsService
				.createOrReplace( gcsFileName , gcsFileOptions );
		
		gcsOutputChannel.write( ByteBuffer.wrap( bytes ) );
		gcsOutputChannel.close();
	}

	@Override
	public void updateBlob(
			BlobEntry blobEntry, String content, Charset charset )
					throws IOException {
		
		updateBlob( blobEntry, content.getBytes( charset ) );
	}

	@Override
	public BlobEntry getBlob( String fileName ) throws IOException {

		GcsFilename gcsFileName
				= new GcsFilename( bucketName, fileName );
		GcsFileMetadata gcsFileMetadata
				= gcsService.getMetadata( gcsFileName );
		GcsInputChannel gcsInputChannel
				= gcsService.openReadChannel( gcsFileName, 0 );
		
		ByteBuffer byteBuffer
				= ByteBuffer.allocate( (int) gcsFileMetadata.getLength() );
		gcsInputChannel.read( byteBuffer );
	
		return new BlobEntryGcsImpl( byteBuffer, gcsFileMetadata );
	}

	@Override
	public void serveBlob( String fileName, HttpServletResponse response )
			throws IOException {

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
