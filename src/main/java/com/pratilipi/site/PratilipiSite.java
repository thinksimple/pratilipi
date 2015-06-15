package com.pratilipi.site;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.common.util.LanguageUtil;
import com.pratilipi.common.util.ThirdPartyResource;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	private static final Language defaulLang = Language.ENGLISH;
	private static final String languageFilePrefix = "WEB-INF/classes/com/pratilipi/site/i18n/language.";
	
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		Language lang = UxModeFilter.getUserLanguage();
		
		List<String> resourceList = new LinkedList<>();
		resourceList.add( ThirdPartyResource.JQUERY.getTag() );
		resourceList.add( ThirdPartyResource.BOOTSTRAP.getTag() );
		resourceList.add( ThirdPartyResource.POLYMER.getTag() );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "_strings", LanguageUtil.getStrings( languageFilePrefix + lang.getCode(), languageFilePrefix + defaulLang.getCode() ) );
		dataModel.put( "lang", lang.getCode() );
		dataModel.put( "resourceList", resourceList );
		dataModel.put( "featuredList", getFeaturedListList() );
		
		String html = "";
		try {
			html = FreeMarkerUtil.processTemplate( dataModel, "com/pratilipi/site/page/Home.ftl" );
		} catch( UnexpectedServerException e ) {
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			try {
				html = FreeMarkerUtil.processTemplate( dataModel, "com/pratilipi/site/page/error/ServerError.ftl" );
			} catch( UnexpectedServerException ex ) { }
		}
		response.setCharacterEncoding( "UTF-8" );
		response.getWriter().write( html );
		response.getWriter().close();
	}

	public String[] getFeaturedListList() {
		AuthorData authorData = new AuthorData();
		authorData.setName( "Author Name" );
		authorData.setPageUrlAlias( "/author-name" );
		
		PratilipiData pratilipiData = new PratilipiData();
		pratilipiData.setTitle( "Book Title" );
		pratilipiData.setPageUrlAlias( "/author-name/book-name" );
		pratilipiData.setAuthor( authorData );
		pratilipiData.setRatingCount( 10L );
		pratilipiData.setAverageRating( 3.5F );
		pratilipiData.setCoverImageUrl( "//4.pratilipi.info/pratilipi-cover/150/4648777960914944?1433827138942" );
		
		Gson gson = new Gson();
		
		String[] featuredList = new String[] {
				gson.toJson( pratilipiData ).toString(),
				gson.toJson( pratilipiData ).toString(),
				gson.toJson( pratilipiData ).toString(),
				gson.toJson( pratilipiData ).toString(),
				gson.toJson( pratilipiData ).toString(),
				gson.toJson( pratilipiData ).toString(),
		};
		
		return featuredList;
	}

}