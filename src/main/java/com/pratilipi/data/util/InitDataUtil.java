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
import com.pratilipi.filter.AccessTokenFilter;

public class InitDataUtil {
	
	private static Map<Language, InitDoc> langInitDocs = new HashMap<>();
	
	
	static {
		
		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		
		InitBannerDoc[] initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-hindi-carousel-39.jpg", null, "/november-hits", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-hindi-carousel-40.jpg", null, "/suspense-aur-thriller", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-hindi-carousel-41.jpg", null, "/lovestories", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-hindi-carousel-42.jpg", null, "/vyastata", null, null ),
		};
		
		InitDoc hiInitDoc = docAccessor.newInitDoc();
		hiInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.HINDI, hiInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-gujarati-carousel-41.jpg", null, "/november-hits", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-gujarati-carousel-37.jpg", null, "/rahasyamay-ane-romanchak", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-gujarati-carousel-38.jpg", null, "/romance", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-gujarati-carousel-39.jpg", null, "/five-minutes-story", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-gujarati-carousel-40.jpg", null, "/film-and-music", null, null ),
		};
			
		InitDoc guInitDoc = docAccessor.newInitDoc();
		guInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.GUJARATI, guInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-tamil-carousel-19.jpg", null, "/event/ore-oru-oorla", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-tamil-carousel-20.jpg", null, "/romance", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-tamil-carousel-21.jpg", null, "/humour", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-tamil-carousel-17.jpg", null, "/horror", null, null ),
		};
			
		InitDoc taInitDoc = docAccessor.newInitDoc();
		taInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TAMIL, taInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-marathi-carousel-34.jpg", null, "/november-hit", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-marathi-carousel-35.jpg", null, "/rahasyakatha", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-marathi-carousel-36.jpg", null, "/lovestories", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-marathi-carousel-37.jpg", null, "/five-minutes-read", null, null ),
		};
			
		InitDoc mrInitDoc = docAccessor.newInitDoc();
		mrInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MARATHI, mrInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-malayalam-carousel-15.jpg", null, "/event/tellastory", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-malayalam-carousel-16.jpg", null, "/fiveminute", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-malayalam-carousel-17.jpg", null, "/love", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-malayalam-carousel-18.jpg", null, "/two-minute", null, null ),
		};

		InitDoc mlInitDoc = docAccessor.newInitDoc();
		mlInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MALAYALAM, mlInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-bengali-carousel-35.jpg", null, "/rahashyogalpo", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-bengali-carousel-36.jpg", null, "/november-hits", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-bengali-carousel-37.jpg", null, "/premkahini", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-bengali-carousel-38.jpg", null, "/a-lot-in-few-minutes", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-bengali-carousel-34.jpg", null, "/event/nohi-daanob-nohi-mahamaanob", null, null ),
		};
			
		InitDoc bnInitDoc = docAccessor.newInitDoc();
		bnInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.BENGALI, bnInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-telugu-carousel-21.jpg", null, "/love", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-telugu-carousel-22.jpg", null, "/ministories", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-telugu-carousel-23.jpg", null, "/fivemin", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-telugu-carousel-18.jpg", null, "/life", null, null ),
		};
			
		InitDoc teInitDoc = docAccessor.newInitDoc();
		teInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TELUGU, teInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {
				docAccessor.newInitBannerDoc( null, "pratilipi-kannada-carousel-09.jpg", null, "/cinema", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-kannada-carousel-10.jpg", null, "/society", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-kannada-carousel-11.jpg", null, "/women", null, null ),
				docAccessor.newInitBannerDoc( null, "pratilipi-kannada-carousel-12.jpg", null, "/event/kr", null, null ),
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
		String url = "/init/banner?language=" + language + "&name=" + bannerId;
		if( width != null )
			url = url + "&width=" + width;
		if( SystemProperty.CDN != null )
			url = SystemProperty.CDN.replace( "*", "0" ) + url;
		return url;
	}


	public static BlobEntry getInitBanner( Language language, String name, Integer width )
			throws InvalidArgumentException, UnexpectedServerException {
		
		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor()
				.getBlob( "init/banners/" + language.getCode() + "/" + name );
		
		if( blobEntry == null ) {
			JsonObject errorMessages = new JsonObject();
			errorMessages.addProperty( "name", "Invalid banner name." );
			throw new InvalidArgumentException( errorMessages );
		}
			
		if( width != null )
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width ) );
		
		return blobEntry;
		
	}

	public static List<InitBannerData> getInitBannerList( Language language, boolean isMini ) {
		
		List<InitBannerDoc> initBanners = langInitDocs.get( language ).getBanners();
		List<InitBannerData> initBannerDataList = new ArrayList<>( initBanners.size() );

		for( InitBannerDoc initBanner : initBanners ) {
			InitBannerData initBannerData = new InitBannerData();
			initBannerData.setTitle( initBanner.getTitle() );
			initBannerData.setImageUrl( createInitBannerUrl( language, isMini ? initBanner.getBannerMini() : initBanner.getBanner() ) );
			initBannerData.setActionUrl( initBanner.getActionUrl() );
			initBannerData.setApiName( initBanner.getApiName() );
			initBannerData.setApiRequest( initBanner.getApiRequest() );
			initBannerDataList.add( initBannerData );
		}
		
		return initBannerDataList;
	
	}
	
	public static String saveInitBanner( Language language, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! hasAccessToUpdateInit( language ) )
			throw new InsufficientAccessException();
		
		
		String name = new Date().getTime() + "";
		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		blobEntry.setName( "init/banners/" + language.getCode() + "/" + name );
		blobAccessor.createOrUpdateBlob( blobEntry );
		
		return name;
		
	}
	
	
}
