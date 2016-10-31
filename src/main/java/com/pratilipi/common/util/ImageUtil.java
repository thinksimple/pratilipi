package com.pratilipi.common.util;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesService.OutputEncoding;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.OutputSettings;
import com.google.appengine.api.images.Transform;
import com.pratilipi.data.type.BlobEntry;

public class ImageUtil {

	private static final ImagesService imagesService = ImagesServiceFactory.getImagesService();
	

	public static int getHeight( byte[] imageData ) {
		return ImagesServiceFactory.makeImage( imageData ).getHeight();
	}
	
	public static int getWidth( byte[] imageData ) {
		return ImagesServiceFactory.makeImage( imageData ).getWidth();
	}
	
	public static byte[] resize( byte[] imageData, int width ) {
		Image image = ImagesServiceFactory.makeImage( imageData );
		Transform resize = ImagesServiceFactory.makeResize(
				width < 4000 ? width : 4000,
				4000,
				false );
		return imagesService.applyTransform( resize, image, OutputEncoding.JPEG ).getImageData();
	}

	@Deprecated
	public static byte[] resize( byte[] imageData, int width, int height ) {
		Image image = ImagesServiceFactory.makeImage( imageData );
		Transform resize = ImagesServiceFactory.makeResize(
				width < 4000 ? width : 4000,
				height < 4000 ? height : 4000,
				true );
		return imagesService.applyTransform( resize, image, OutputEncoding.JPEG ).getImageData();
	}
	
	public static BlobEntry resize( BlobEntry blobEntry, int width, int height ) {
		Image image = ImagesServiceFactory.makeImage( blobEntry.getData() );
		Transform resize = ImagesServiceFactory.makeResize(
				width < 4000 ? width : 4000,
				height < 4000 ? height : 4000,
				true );
		if( blobEntry.getMimeType().equalsIgnoreCase( "image/png" ) ) {
			image = imagesService.applyTransform( resize, image, OutputEncoding.PNG );
		} else {
			OutputSettings settings = new OutputSettings( ImagesService.OutputEncoding.JPEG );
			settings.setQuality( 50 );
			image = imagesService.applyTransform( resize, image, settings );
			blobEntry.setMimeType( "image/jpeg" );
		}
		blobEntry.setData( image.getImageData() );
		return blobEntry;
	}
	
}
