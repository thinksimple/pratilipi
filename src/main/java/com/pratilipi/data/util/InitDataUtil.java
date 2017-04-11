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

				docAccessor.newInitBannerDoc( "प्रेम कथाएं", 
						"pratilipi-hindi-carousel-51.jpg", 
						"pratilipi-hindi-carousel-51-small.jpg", 
						"/lovestories",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"HINDI\", \"state\":\"PUBLISHED\", \"listName\":\"lovestories\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "सस्पेंस और थ्रिलर कथाए", 
						"pratilipi-hindi-carousel-52.jpg", 
						"pratilipi-hindi-carousel-52-small.jpg", 
						"/suspense-aur-thriller",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"HINDI\", \"state\":\"PUBLISHED\", \"listName\":\"suspense-aur-thriller\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "नई प्रकाशित रचनाएं", 
						"pratilipi-hindi-carousel-54.jpg", 
						"pratilipi-hindi-carousel-54-small.jpg", 
						"/nayi-prakashit-rachnaye",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"HINDI\", \"state\":\"PUBLISHED\", \"listName\":\"nayi-prakashit-rachnaye\" }", JsonElement.class ).getAsJsonObject() ),

		};
		
		InitDoc hiInitDoc = docAccessor.newInitDoc();
		hiInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.HINDI, hiInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "રોમાંસ", 
						"pratilipi-gujarati-carousel-47.jpg", 
						"pratilipi-gujarati-carousel-47-small.jpg", 
						"/romance",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"romance\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "રહસ્યમય અને રોમાંચક વાર્તાઓ", 
						"pratilipi-gujarati-carousel-48.jpg", 
						"pratilipi-gujarati-carousel-48-small.jpg", 
						"/rahasyamay-ane-romanchak",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"rahasyamay-ane-romanchak\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "નવી પ્રકાશિત રચનાઓ", 
						"pratilipi-gujarati-carousel-50.jpg", 
						"pratilipi-gujarati-carousel-50-small.jpg", 
						"/navi-prakashit-rachnao",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"GUJARATI\", \"state\":\"PUBLISHED\", \"listName\":\"navi-prakashit-rachnao\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc guInitDoc = docAccessor.newInitDoc();
		guInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.GUJARATI, guInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "காதல்", 
						"pratilipi-tamil-carousel-30.png", 
						"pratilipi-tamil-carousel-30-small.png", 
						"/romance",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TAMIL\", \"state\":\"PUBLISHED\", \"listName\":\"romance\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "சினிமா", 
						"pratilipi-tamil-carousel-31.png", 
						"pratilipi-tamil-carousel-31-small.png", 
						"/cinema",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TAMIL\", \"state\":\"PUBLISHED\", \"listName\":\"cinema\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "அறிவியல் புனைவு", 
						"pratilipi-tamil-carousel-32.png", 
						"pratilipi-tamil-carousel-32-small.png", 
						"/science-fiction",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TAMIL\", \"state\":\"PUBLISHED\", \"listName\":\"science-fiction\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "குடும்பம்", 
						"pratilipi-tamil-carousel-26.jpg", 
						"pratilipi-tamil-carousel-26-small.jpg", 
						"/family",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TAMIL\", \"state\":\"PUBLISHED\", \"listName\":\"family\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc taInitDoc = docAccessor.newInitDoc();
		taInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TAMIL, taInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "प्रेम कथा", 
						"pratilipi-marathi-carousel-44.jpg", 
						"pratilipi-marathi-carousel-44-small.jpg", 
						"/lovestories",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MARATHI\", \"state\":\"PUBLISHED\", \"listName\":\"lovestories\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "रहस्य कथा", 
						"pratilipi-marathi-carousel-43.jpg", 
						"pratilipi-marathi-carousel-43-small.jpg", 
						"/rahasyakatha",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MARATHI\", \"state\":\"PUBLISHED\", \"listName\":\"rahasyakatha\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "नवीन प्रकाशित साहित्य", 
						"pratilipi-marathi-carousel-46.jpg", 
						"pratilipi-marathi-carousel-46-small.jpg", 
						"/newly-published-content",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MARATHI\", \"state\":\"PUBLISHED\", \"listName\":\"newly-published-content\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc mrInitDoc = docAccessor.newInitDoc();
		mrInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MARATHI, mrInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "അവളുടെ കഥകള്‍", 
						"pratilipi-malayalam-carousel-31.jpg", 
						"pratilipi-malayalam-carousel-31-small.jpg", 
						"/event/herstory",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MALAYALAM\", \"state\":\"PUBLISHED\", \"eventId\":5746638655062016 }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "സ്ത്രീ", 
						"pratilipi-malayalam-carousel-29.jpg", 
						"pratilipi-malayalam-carousel-29-small.jpg", 
						"/women",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MALAYALAM\", \"state\":\"PUBLISHED\", \"listName\":\"women\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "കത്തുകള്‍", 
						"pratilipi-malayalam-carousel-30.jpg", 
						"pratilipi-malayalam-carousel-30-small.jpg", 
						"/letters",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MALAYALAM\", \"state\":\"PUBLISHED\", \"listName\":\"letters\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "സൗഹൃദം", 
						"pratilipi-malayalam-carousel-26.jpg", 
						"pratilipi-malayalam-carousel-26-small.jpg", 
						"/friendship",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"MALAYALAM\", \"state\":\"PUBLISHED\", \"listName\":\"friendship\" }", JsonElement.class ).getAsJsonObject() ),

		};

		InitDoc mlInitDoc = docAccessor.newInitDoc();
		mlInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.MALAYALAM, mlInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "প্রেমকাহিনী", 
						"pratilipi-bengali-carousel-44.jpg", 
						"pratilipi-bengali-carousel-44-small.jpg", 
						"/premkahini",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"listName\":\"premkahini\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "রহস্যগল্প", 
						"pratilipi-bengali-carousel-45.jpg", 
						"pratilipi-bengali-carousel-45-small.jpg", 
						"/rahashyogalpo",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"listName\":\"rahashyogalpo\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "নতুন প্রকাশিত রচনা", 
						"pratilipi-bengali-carousel-47.jpg", 
						"pratilipi-bengali-carousel-47-small.jpg", 
						"/natun-prakashito-rachona",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"BENGALI\", \"state\":\"PUBLISHED\", \"listName\":\"natun-prakashito-rachona\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc bnInitDoc = docAccessor.newInitDoc();
		bnInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.BENGALI, bnInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "ప్రేమ",
						"pratilipi-telugu-carousel-31a.jpg",
						"pratilipi-telugu-carousel-31a-small.jpg",
						"/life",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TELUGU\", \"state\":\"PUBLISHED\", \"listName\":\"life\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "జీవితం",
						"pratilipi-telugu-carousel-32a.jpg",
						"pratilipi-telugu-carousel-32a-small.jpg",
						"/love",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TELUGU\", \"state\":\"PUBLISHED\", \"listName\":\"love\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ఆధ్యాత్మికం",
						"pratilipi-telugu-carousel-33a.jpg",
						"pratilipi-telugu-carousel-33a-small.jpg",
						"/spiritual",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"TELUGU\", \"state\":\"PUBLISHED\", \"listName\":\"spiritual\" }", JsonElement.class ).getAsJsonObject() ),

		};
			
		InitDoc teInitDoc = docAccessor.newInitDoc();
		teInitDoc.setBanners( Arrays.asList( initBannerDocs ) );
		langInitDocs.put( Language.TELUGU, teInitDoc );
		
		
		initBannerDocs = new InitBannerDoc[] {

				docAccessor.newInitBannerDoc( "ಪ್ರೀತಿ", 
						"pratilipi-kannada-carousel-24.png", 
						"pratilipi-kannada-carousel-24-small.png", 
						"/love",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"KANNADA\", \"state\":\"PUBLISHED\", \"listName\":\"love\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ಕವಿತೆಗಳು", 
						"pratilipi-kannada-carousel-25.png", 
						"pratilipi-kannada-carousel-25-small.png", 
						"/poems",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"KANNADA\", \"state\":\"PUBLISHED\", \"listName\":\"poems\" }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ಪ್ರತಿಲಿಪಿ ಕಥಾ ಸ್ಪರ್ಧೆ-2017", 
						"pratilipi-kannada-carousel-23.jpg", 
						"pratilipi-kannada-carousel-23-small.jpg", 
						"/event/pks",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"KANNADA\", \"state\":\"PUBLISHED\", \"eventId\":6058375333806080 }", JsonElement.class ).getAsJsonObject() ),

				docAccessor.newInitBannerDoc( "ಮಹಿಳೆ", 
						"pratilipi-kannada-carousel-26.png", 
						"pratilipi-kannada-carousel-26-small.png", 
						"/women",
						"PratilipiListApi",
						gson.fromJson( "{ \"language\":\"KANNADA\", \"state\":\"PUBLISHED\", \"listName\":\"women\" }", JsonElement.class ).getAsJsonObject() ),

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
