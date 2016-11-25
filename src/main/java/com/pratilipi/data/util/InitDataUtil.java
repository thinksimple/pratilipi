package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
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
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-39.jpg", null, "/november-hits" ),
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-40.jpg", null, "/suspense-aur-thriller" ),
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-41.jpg", null, "/lovestories" ),
				docAccessor.newInitBannerDoc( "pratilipi-hindi-carousel-42.jpg", null, "/vyastata" ),
		};
		
		InitDoc hiInitDoc = docAccessor.newInitDoc();
		hiInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.HINDI, hiInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-41.jpg", null, "/november-hits" ),
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-37.jpg", null, "/rahasyamay-ane-romanchak" ),
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-38.jpg", null, "/romance" ),
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-39.jpg", null, "/five-minutes-story" ),
				docAccessor.newInitBannerDoc( "pratilipi-gujarati-carousel-40.jpg", null, "/film-and-music" ),
		};
			
		InitDoc guInitDoc = docAccessor.newInitDoc();
		guInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.GUJARATI, guInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-19.jpg", null, "/event/ore-oru-oorla" ),
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-17.jpg", null, "/horror" ),
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-18.jpg", null, "/education" ),
				docAccessor.newInitBannerDoc( "pratilipi-tamil-carousel-13.jpg", null, "/fiveminstories" ),
		};
			
		InitDoc taInitDoc = docAccessor.newInitDoc();
		taInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TAMIL, taInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-34.jpg", null, "/november-hit" ),
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-35.jpg", null, "/rahasyakatha" ),
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-36.jpg", null, "/lovestories" ),
				docAccessor.newInitBannerDoc( "pratilipi-marathi-carousel-37.jpg", null, "/five-minutes-read" ),
		};
			
		InitDoc mrInitDoc = docAccessor.newInitDoc();
		mrInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MARATHI, mrInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-malayalam-carousel-15.jpg", null, "/event/tellastory" ),
				docAccessor.newInitBannerDoc( "pratilipi-malayalam-carousel-11.jpg", null, "/society" ),
				docAccessor.newInitBannerDoc( "pratilipi-malayalam-carousel-12.jpg", null, "/mystery" ),
				docAccessor.newInitBannerDoc( "pratilipi-malayalam-carousel-13.jpg", null, "/lives" ),
		};
			
		InitDoc mlInitDoc = docAccessor.newInitDoc();
		mlInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MALAYALAM, mlInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-35.jpg", null, "/rahashyogalpo" ),
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-36.jpg", null, "/november-hits" ),
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-37.jpg", null, "/premkahini" ),
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-38.jpg", null, "/a-lot-in-few-minutes" ),
				docAccessor.newInitBannerDoc( "pratilipi-bengali-carousel-34.jpg", null, "/event/nohi-daanob-nohi-mahamaanob" ),
		};
			
		InitDoc bnInitDoc = docAccessor.newInitDoc();
		bnInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.BENGALI, bnInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-telugu-carousel-18.jpg", null, "/life" ),
				docAccessor.newInitBannerDoc( "pratilipi-telugu-carousel-10.jpg", null, "/women" ),
				docAccessor.newInitBannerDoc( "pratilipi-telugu-carousel-14.jpg", null, "/children" ),
		};
			
		InitDoc teInitDoc = docAccessor.newInitDoc();
		teInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TELUGU, teInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-09.jpg", null, "/cinema" ),
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-10.jpg", null, "/society" ),
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-11.jpg", null, "/women" ),
				docAccessor.newInitBannerDoc( "pratilipi-kannada-carousel-12.jpg", null, "/event/kr" ),
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
		
		if( blobEntry == null ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "bannerId", "Invalid bannerId." );
			throw new InvalidArgumentException( errorMessages );
		}
			
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
			if( link == null )
				continue;
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
