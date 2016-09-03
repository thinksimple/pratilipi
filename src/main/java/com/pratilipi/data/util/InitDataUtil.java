package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pratilipi.data.DocAccessor;
import com.pratilipi.data.client.InitBannerData;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.InitBannerDoc;
import com.pratilipi.data.type.InitDoc;
import com.pratilipi.data.type.Navigation;
import com.pratilipi.filter.AccessTokenFilter;

public class InitDataUtil {
	
	private static Map<Language, InitDoc> langInitDocs = new HashMap<>();
	
	
	static {
		
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		
		InitBannerDoc[] initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-24.jpg", null, "/self-help" ),
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-25.jpg", null, "/mythology" ),
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-29.jpg", null, "/lovestories" ),
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-27.jpg", null, "/suspense-aur-thriller" ),
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-28.jpg", null, "/events" ),
		};
		
		InitDoc hiInitDoc = docAccessor.newInitDoc();
		hiInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.HINDI, hiInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-31.jpg", null, "/yangistaan" ),
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-28.jpg", null, "/rahasyamay-ane-romanchak" ),
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-29.jpg", null, "/romance" ),
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-30.jpg", null, "/bal-sahitya" ),
		};
			
		InitDoc guInitDoc = docAccessor.newInitDoc();
		guInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.GUJARATI, guInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-11.jpg", null, "/ministories" ),
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-12.jpg", null, "/cinema" ),
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-13.jpg", null, "/fiveminstories" ),
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-14.jpg", null, "/politics" ),
		};
			
		InitDoc taInitDoc = docAccessor.newInitDoc();
		taInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TAMIL, taInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-29.jpg", null, "/aai-vina-jag-sare" ),
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-26.jpg", null, "/search?q=poems" ),
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-27.jpg", null, "/bodhkatha" ),
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-28.jpg", null, "/events" ),
		};
			
		InitDoc mrInitDoc = docAccessor.newInitDoc();
		mrInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MARATHI, mrInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-malayalam-carousel-05.jpg", null, "/event/fr" ),
				docAccessor.newInitBannerDoc( "pratilipi-malayalam-carousel-06.jpg", null, "/love" ),
				docAccessor.newInitBannerDoc( "pratilipi-malayalam-carousel-07.jpg", null, "/memory" ),
		};
			
		InitDoc mlInitDoc = docAccessor.newInitDoc();
		mlInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MALAYALAM, mlInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-26.jpg", null, "/bhoutik-galpo" ),
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-27.jpg", null, "/rahashyogalpo" ),
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-28.jpg", null, "/aleek-kahini" ),
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-29.jpg", null, "/events" ),
		};
			
		InitDoc bnInitDoc = docAccessor.newInitDoc();
		bnInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.BENGALI, bnInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-telugu-carousel-14.jpg", null, "/children" ),
				docAccessor.newInitBannerDoc( "pratilipi-telugu-carousel-15.jpg", null, "/love" ),
				docAccessor.newInitBannerDoc( "pratilipi-telugu-carousel-09.jpg", null, "/social" ),
				docAccessor.newInitBannerDoc( "pratilipi-telugu-carousel-10.jpg", null, "/women" ),
		};
			
		InitDoc teInitDoc = docAccessor.newInitDoc();
		teInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TELUGU, teInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-05.jpg", null, "/event/fday" ),
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-01.jpg", null, "/love" ),
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-02.jpg", null, "/society" ),
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-03.jpg", null, "/spiritual" ),
		};
			
		InitDoc knInitDoc = docAccessor.newInitDoc();
		knInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.KANNADA, knInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {};
			
		InitDoc enInitDoc = docAccessor.newInitDoc();
		enInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.ENGLISH, enInitDoc );
		
	}
	
	
	public static boolean hasAccessToUpdateInit( Language language ) {

		return UserAccessUtil.hasUserAccess(
				AccessTokenFilter.getAccessToken().getUserId(),
				language,
				AccessType.INIT_UPDATE );
		
	}
	

	
	public static String createInitBannerUrl( Language language, String bannerId ) {
		return createInitBannerUrl( language, bannerId, null );
	}
	
	public static String createInitBannerUrl( Language language, String bannerId, Integer width ) {
		String url = "/init/banner?language=" + language + "&bannerId=" + bannerId;
		if( width != null )
			url = url + "&width=" + width;
		if( SystemProperty.CDN != null )
			url = SystemProperty.CDN.replace( "*", "0" ) + url;
		return url;
	}


	public static BlobEntry getInitBanner( Language language, String bannerId, Integer width )
			throws InvalidArgumentException, UnexpectedServerException {
		
		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor()
				.getBlob( "init/banners/" + language.getCode() + "/" + bannerId );
		
		if( blobEntry == null )
			throw new InvalidArgumentException( "{ \"bannerId\":\"Invalid bannerId.\" }" );
		
		if( width != null )
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width ) );
		
		return blobEntry;
		
	}

	public static List<InitBannerData> getInitBannerList( Language language ) {
		
		List<InitBannerDoc> initBanners = langInitDocs.get( language ).getBanners();
		List<InitBannerData> initBannerDataList = new ArrayList<>( initBanners.size() );

		List<Navigation> navigationList = DataAccessorFactory.getDataAccessor().getNavigationList( language );
		Map<String, Navigation.Link> links = new HashMap<>();
		for( Navigation navigation : navigationList )
			for( Navigation.Link link : navigation.getLinkList() )
				links.put( link.getUrl(), link );
		
		for( InitBannerDoc initBanner : initBanners ) {
			InitBannerData initBannerData = new InitBannerData( initBanner.getId() );
			initBannerData.setTitle( initBanner.getTitle() );
			initBannerData.setImageUrl( createInitBannerUrl( language, initBanner.getId() ) );
			initBannerData.setActionUrl( initBanner.getActionUrl() );
			Navigation.Link link = links.get( initBanner.getActionUrl() );
			initBannerData.setTitle( link.getName() );
			initBannerData.setApiName( link.getApiName() );
			initBannerData.setApiRequest( (String) link.getApiRequest() );
			initBannerDataList.add( initBannerData );
		}
		
		return initBannerDataList;
	
	}
	
	public static void saveInitBanner( Language language, String name, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! hasAccessToUpdateInit( language ) )
			throw new InsufficientAccessException();
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		blobEntry.setName( "init/banners/" + language.getCode() + "/" + new Date().getTime() );
		blobAccessor.createOrUpdateBlob( blobEntry );
		
	}
	
	
}
