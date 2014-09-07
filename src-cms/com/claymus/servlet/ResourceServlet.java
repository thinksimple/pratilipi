package com.claymus.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

@SuppressWarnings("serial")
public class ResourceServlet extends HttpServlet {
	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		String fileName = request.getRequestURI().substring( 10 );
		if( ! DataAccessorFactory.getBlobAccessor().createBlob( request, fileName ) )
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
	}
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		String blobName = request.getRequestURI().substring( 10 );
		BlobEntry blobEntry =
				DataAccessorFactory.getBlobAccessor().getBlob( blobName );
		
		String eTag = request.getHeader( "If-None-Match" );
		if( eTag != null && eTag.equals( blobEntry.getETag() ) ) {
			response.setStatus( HttpServletResponse.SC_NOT_MODIFIED );
		
		} else {
			response.setContentType( blobEntry.getMimeType() );
			response.setHeader( "ETag", blobEntry.getETag() );

			OutputStream out = response.getOutputStream();
			ServletConfig servletConfig = getServletConfig();
			String resizeImageParam = servletConfig.getInitParameter( "ResizeImage" );
			
			if( resizeImageParam != null ) {
				ImagesService imagesService = ImagesServiceFactory.getImagesService();
		        Transform resize = ImagesServiceFactory.makeResize(
		        		Integer.parseInt( resizeImageParam ), 1000 );
		        Image oldImage = ImagesServiceFactory.makeImage( blobEntry.getData() );
		        Image newImage = imagesService.applyTransform( resize, oldImage );
		        out.write( newImage.getImageData() );
			
			} else {
				out.write( blobEntry.getData() );

			}
			
			out.close();
		}
	}
	
}
