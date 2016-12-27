package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
	private static Gson gson = new Gson();

	static {

		DocAccessor docAccessor = DataAccessorFactory.getDocAccessor();

		InitBannerDoc[] initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "November Hits", 
						"pratilipi-hindi-carousel-39.jpg", 
						"pratilipi-hindi-carousel-39-small.jpg", 
						"/november-hits",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"HINDI\", \"state\":\"PUBLISHED\", \"listName\":\"november-hits\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "सस्पेंस और थ्रिलर कथाए", 
						"pratilipi-hindi-carousel-40.jpg", 
						"pratilipi-hindi-carousel-40-small.jpg", 
						"/suspense-aur-thriller",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"HINDI\", \"state\":\"PUBLISHED\", \"listName\":\"suspense-aur-thriller\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "प्रेम कथाएं", 
						"pratilipi-hindi-carousel-41.jpg", 
						"pratilipi-hindi-carousel-41-small.jpg", 
						"/lovestories",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"HINDI\", \"state\":\"PUBLISHED\", \"listName\":\"lovestories\" }", JsonElement.class ).getAsJsonObject() ),
						
				docAccessor.newInitBannerDoc( "Vyastata", 
						"pratilipi-hindi-carousel-42.jpg", 
						"pratilipi-hindi-carousel-42-small.jpg", 
						"/vyastata",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"HINDI\", \"state\":\"PUBLISHED\", \"listName\":\"vyastata\" }", JsonElement.class ).getAsJsonObject() )

		};
		
		InitDoc hiInitDoc = docAccessor.newInitDoc();
		hiInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.HINDI, hiInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "November Hits", 
						"pratilipi-gujarati-carousel-41.jpg", 
						"pratilipi-gujarati-carousel-41-small.jpg", 
						"/november-hits",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"november-hits\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "રહસ્યમય અને રોમાંચક વાર્તાઓ", 
						"pratilipi-gujarati-carousel-37.jpg", 
						"pratilipi-gujarati-carousel-37-small.jpg", 
						"/rahasyamay-ane-romanchak",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"rahasyamay-ane-romanchak\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "રોમાંસ વાર્તાઓ", 
						"pratilipi-gujarati-carousel-38.jpg", 
						"pratilipi-gujarati-carousel-38-small.jpg", 
						"/romance",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"romance\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "Five Minutes Story", 
						"pratilipi-gujarati-carousel-39.jpg", 
						"pratilipi-gujarati-carousel-39-small.jpg", 
						"/five-minutes-story",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"five-minutes-story\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ફિલ્મ અને મ્યુઝીક", 
						"pratilipi-gujarati-carousel-40.jpg", 
						"pratilipi-gujarati-carousel-40-small.jpg", 
						"/film-and-music",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"film-and-music\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc guInitDoc = docAccessor.newInitDoc();
		guInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.GUJARATI, guInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "New Year", 
						"pratilipi-tamil-carousel-22.jpg", 
						"pratilipi-tamil-carousel-22-small.jpg", 
						"/newyear",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TAMIL\", \"state\":\"PUBLISHED\", \"listName\":\"newyear\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "மர்மம் / திகில்", 
						"pratilipi-tamil-carousel-23.jpg", 
						"pratilipi-tamil-carousel-23-small.jpg", 
						"/horror",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TAMIL\", \"state\":\"PUBLISHED\", \"listName\":\"horror\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "காதல்", 
						"pratilipi-tamil-carousel-20.jpg", 
						"pratilipi-tamil-carousel-20-small.jpg", 
						"/romance",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TAMIL\", \"state\":\"PUBLISHED\", \"listName\":\"romance\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc taInitDoc = docAccessor.newInitDoc();
		taInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TAMIL, taInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "November Hit", 
						"pratilipi-marathi-carousel-34.jpg", 
						"pratilipi-marathi-carousel-34-small.jpg", 
						"/november-hit",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MARATHI\", \"state\":\"PUBLISHED\", \"listName\":\"november-hit\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "रहस्य कथा", 
						"pratilipi-marathi-carousel-35.jpg", 
						"pratilipi-marathi-carousel-35-small.jpg", 
						"/rahasyakatha",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MARATHI\", \"state\":\"PUBLISHED\", \"listName\":\"rahasyakatha\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "प्रेम कथा", 
						"pratilipi-marathi-carousel-36.jpg", 
						"pratilipi-marathi-carousel-36-small.jpg", 
						"/lovestories",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MARATHI\", \"state\":\"PUBLISHED\", \"listName\":\"lovestories\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "Five Minutes Read", 
						"pratilipi-marathi-carousel-37.jpg", 
						"pratilipi-marathi-carousel-37-small.jpg", 
						"/five-minutes-read",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MARATHI\", \"state\":\"PUBLISHED\", \"listName\":\"five-minutes-read\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc mrInitDoc = docAccessor.newInitDoc();
		mrInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MARATHI, mrInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "2016 Most Read", 
						"pratilipi-malayalam-carousel-20.jpg", 
						"pratilipi-malayalam-carousel-20-small.jpg", 
						"/2016mostread",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MALAYALAM\", \"state\":\"PUBLISHED\", \"listName\":\"2016mostread\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ദുരൂഹം", 
						"pratilipi-malayalam-carousel-21.jpg", 
						"pratilipi-malayalam-carousel-21-small.jpg", 
						"/mystery",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MALAYALAM\", \"state\":\"PUBLISHED\", \"listName\":\"mystery\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "5 മിനിറ്റ് കഥകള്‍", 
						"pratilipi-malayalam-carousel-19.jpg", 
						"pratilipi-malayalam-carousel-19-small.jpg", 
						"/fiveminute",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MALAYALAM\", \"state\":\"PUBLISHED\", \"listName\":\"fiveminute\" }", JsonElement.class ).getAsJsonObject() ),

		};

		InitDoc mlInitDoc = docAccessor.newInitDoc();
		mlInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MALAYALAM, mlInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "রহস্যগল্প", 
						"pratilipi-bengali-carousel-35.jpg", 
						"pratilipi-bengali-carousel-35-small.jpg", 
						"/rahashyogalpo",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"listName\":\"rahashyogalpo\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "November Hits", 
						"pratilipi-bengali-carousel-36.jpg", 
						"pratilipi-bengali-carousel-36-small.jpg", 
						"/november-hits",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"listName\":\"november-hits\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "প্রেমকাহিনী", 
						"pratilipi-bengali-carousel-37.jpg", 
						"pratilipi-bengali-carousel-37-small.jpg", 
						"/premkahini",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"listName\":\"premkahini\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "A lot in few minutes", 
						"pratilipi-bengali-carousel-38.jpg", 
						"pratilipi-bengali-carousel-38-small.jpg", 
						"/a-lot-in-few-minutes",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"listName\":\"a-lot-in-few-minutes\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "Nohi daanob nohi mahamaanob", 
						"pratilipi-bengali-carousel-34.jpg", 
						"pratilipi-bengali-carousel-34-small.jpg", 
						"/event/nohi-daanob-nohi-mahamaanob",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"eventId\":5593263756017664 }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc bnInitDoc = docAccessor.newInitDoc();
		bnInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.BENGALI, bnInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "New Year 2017", 
						"pratilipi-telugu-carousel-24.jpg", 
						"pratilipi-telugu-carousel-24-small.jpg", 
						"/newyear2017",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TELUGU\", \"state\":\"PUBLISHED\", \"listName\":\"newyear2017\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "అయిదు నిమిషాల కథలు", 
						"pratilipi-telugu-carousel-23.jpg", 
						"pratilipi-telugu-carousel-23-small.jpg", 
						"/fivemin",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TELUGU\", \"state\":\"PUBLISHED\", \"listName\":\"fivemin\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ప్రేమ", 
						"pratilipi-telugu-carousel-21.jpg", 
						"pratilipi-telugu-carousel-21-small.jpg", 
						"/love",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TELUGU\", \"state\":\"PUBLISHED\", \"listName\":\"love\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc teInitDoc = docAccessor.newInitDoc();
		teInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TELUGU, teInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "New Year 2017", 
						"pratilipi-kannada-carousel-16.jpg", 
						"pratilipi-kannada-carousel-16-small.jpg", 
						"/newyear2017",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"KANNADA\", \"state\":\"PUBLISHED\", \"listName\":\"newyear2017\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ಪ್ರೀತಿ", 
						"pratilipi-kannada-carousel-13.jpg", 
						"pratilipi-kannada-carousel-13-small.jpg", 
						"/love",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"KANNADA\", \"state\":\"PUBLISHED\", \"listName\":\"love\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ಸ್ಫೂರ್ತಿದಾಯಕ", 
						"pratilipi-kannada-carousel-15.jpg", 
						"pratilipi-kannada-carousel-15-small.jpg", 
						"/inspirational",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"KANNADA\", \"state\":\"PUBLISHED\", \"listName\":inspirational }", JsonElement.class ).getAsJsonObject() ),

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

	public static List<InitBannerData> getInitBannerList( Language language ) {
		
		List<InitBannerDoc> initBanners = langInitDocs.get( language ).getBanners();
		List<InitBannerData> initBannerDataList = new ArrayList<>( initBanners.size() );

		for( InitBannerDoc initBanner : initBanners ) {
			InitBannerData initBannerData = new InitBannerData();
			initBannerData.setTitle( initBanner.getTitle() );
			initBannerData.setImageUrl( createInitBannerUrl( language, initBanner.getBanner() ) );
			initBannerData.setMiniImageUrl( createInitBannerUrl( language, initBanner.getBannerMini() ) );
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
