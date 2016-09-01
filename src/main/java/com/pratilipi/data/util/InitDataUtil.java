package com.pratilipi.data.util;

import java.util.UUID;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.ImageUtil;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.filter.AccessTokenFilter;

public class InitDataUtil {
	
	public static boolean hasAccessToUpdateHome( Language language ) {

		return UserAccessUtil.hasUserAccess(
				AccessTokenFilter.getAccessToken().getUserId(),
				language,
				AccessType.HOME_UPDATE );
		
	}
	

	public static String createPratilipiCoverUrl( Language language, String name ) {
		return createHomeBannerUrl( language, name, null );
	}
	
	public static String createHomeBannerUrl( Language language, String name, Integer width ) {
		String url = "/init/banner?language=" + language + "&name=" + name;
		if( width != null )
			url = url + "&width=" + width;
		if( SystemProperty.CDN != null )
			url = SystemProperty.CDN.replace( "*", "10" ) + url;
		return url;
	}


	public static BlobEntry getHomeBanner( Language language, String bannerId, Integer width )
			throws InvalidArgumentException, UnexpectedServerException {
		
		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor()
				.getBlob( "init/bannners/" + language.getCode() + "/" + bannerId );
		
		if( blobEntry == null )
			throw new InvalidArgumentException( "{ \"bannerId\":\"Invalid bannerId.\" }" );
		
		if( width != null )
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width ) );
		
		return blobEntry;
		
	}

	public static void saveHomeBanner( Language language, String name, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! hasAccessToUpdateHome( language ) )
			throw new InsufficientAccessException();
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		blobEntry.setName( "home/" + language.getCode() + "/banner/" + UUID.randomUUID().toString() );
		blobAccessor.createOrUpdateBlob( blobEntry );
		
	}
	
	
}
