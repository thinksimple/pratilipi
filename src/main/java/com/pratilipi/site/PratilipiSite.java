package com.pratilipi.site;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.gson.Gson;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.common.util.LanguageUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.ThirdPartyResource;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.SearchAccessor;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.site.page.data.Home;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	private static final Logger logger = Logger.getGlobal();
	private static final Language defaulLang = Language.ENGLISH;
	private static final String templateFilePrefix = "com/pratilipi/site/page/";
	private static final String dataFilePrefix = "page/data/";
	private static final String languageFilePrefix = "WEB-INF/classes/com/pratilipi/site/i18n/language.";
	
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Page entity
		String uri = request.getRequestURI();
		Page page = dataAccessor.getPage( uri );

		// Language
		Language lang = UxModeFilter.getUserLanguage();
		
		// Common resource list
		List<String> resourceList = new LinkedList<>();
		resourceList.add( ThirdPartyResource.JQUERY.getTag() );
		resourceList.add( ThirdPartyResource.BOOTSTRAP.getTag() );
		resourceList.add( ThirdPartyResource.POLYMER.getTag() );

		
		// Data Model for FreeMarker
		Map<String, Object> dataModel = null;
		String html = "";
		
		try {
			String templateName = null;
			
			if( uri.equals( "/" ) ) {
				dataModel = createDataModelForHomePage( lang );
				templateName = templateFilePrefix + "Home.ftl";
				
			} else if( uri.equals( "/books" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.BOOK, lang == Language.ENGLISH ? null : lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/stories" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.STORY, lang == Language.ENGLISH ? null : lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/poems" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.POEM, lang == Language.ENGLISH ? null : lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/articles" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.ARTICLE, lang == Language.ENGLISH ? null : lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/magazines" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.MAGAZINE, lang == Language.ENGLISH ? null : lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( page.getType() == PageType.PRATILIPI ) {
				dataModel = createDataModelForPratilipiPage( page.getPrimaryContentId() );
				templateName = templateFilePrefix + "Pratilipi.ftl";
				
			} else if( page.getType() == PageType.AUTHOR ) {
				dataModel = createDataModelForAuthorPage( page.getPrimaryContentId() );
				templateName = templateFilePrefix + "Author.ftl";
			}

			// Adding common data to the Data Model
			dataModel.put( "lang", lang.getCode() );
			dataModel.put( "_strings", LanguageUtil.getStrings(
					languageFilePrefix + lang.getCode(),
					languageFilePrefix + defaulLang.getCode() ) );
			dataModel.put( "resourceList", resourceList );
			
			// The magic
			html = FreeMarkerUtil.processTemplate( dataModel, templateName );

		} catch( UnexpectedServerException e ) {
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			try {
				html = FreeMarkerUtil.processTemplate( dataModel, "com/pratilipi/site/page/error/ServerError.ftl" );
			} catch( UnexpectedServerException ex ) { }
		}

		
		// Dispatching response
		response.setCharacterEncoding( "UTF-8" );
		response.getWriter().write( html );
		response.getWriter().close();
	}
	
	public Map<String, Object> createDataModelForHomePage( Language lang )
			throws UnexpectedServerException {

		Home home = getData( "home." + lang.getCode() + ".json", Home.class );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Gson gson = new Gson();

		List<String> featuredList = new ArrayList<>( home.getFeatured().length );
		for( String uri : home.getFeatured() ) {
			Page page = dataAccessor.getPage( uri );
			if( page == null )
				continue;
			Pratilipi pratilipi = dataAccessor.getPratilipi( page.getPrimaryContentId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			PratilipiData pratilipiData = PratilipiDataUtil.createData( pratilipi, author );
			featuredList.add( gson.toJson( pratilipiData ).toString() );
		}
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "featuredList", featuredList );
		
		return dataModel;
	}

	public Map<String, Object> createDataModelForListPage( PratilipiType type, Language lang )
			throws UnexpectedServerException {

		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Gson gson = new Gson();
		
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType( type );
		pratilipiFilter.setLanguageCode( lang == null ? null : lang.getCode() );
		
		DataListCursorTuple<Long> pratilipiIdListCursorTuple =
				searchAccessor.searchPratilipi( pratilipiFilter, null, 20 );
		List<Pratilipi> pratilipiList =
				dataAccessor.getPratilipiList( pratilipiIdListCursorTuple.getDataList() );
		String cursor = pratilipiIdListCursorTuple.getCursor();
		
		List<String> pratilipiJsonList = new ArrayList<>( pratilipiList.size() );
		for( Pratilipi pratilipi : pratilipiList ) {
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			PratilipiData pratilipiData = PratilipiDataUtil.createData( pratilipi, author );
			pratilipiJsonList.add( gson.toJson( pratilipiData ).toString() );
		}

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "pratilipiJsonList", pratilipiJsonList );
		dataModel.put( "pratilipiFilter", gson.toJson( pratilipiFilter ) );
		dataModel.put( "pratilipiListCursor", cursor );

		return dataModel;
	}

	public Map<String, Object> createDataModelForPratilipiPage( Long pratilipiId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor .getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createData( pratilipi, author );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "pratilipi", pratilipiData );
		dataModel.put( "pratilipiJson", gson.toJson( pratilipiData ).toString() );

		return dataModel;
	}
	
	public Map<String, Object> createDataModelForAuthorPage( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor .getAuthor( authorId );
		AuthorData authorData = AuthorDataUtil.createData( author );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "author", authorData );
		dataModel.put( "authorJson", gson.toJson( authorData ).toString() );

		return dataModel;
	}

	public <T> T getData( String fileName, Class<T> clazz )
			throws UnexpectedServerException {
		
		// Fetching content (json) from data file
		String jsonStr = "";
		try {
			File file = new File( getClass().getResource( dataFilePrefix + fileName ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			while( it.hasNext() )
				jsonStr += it.nextLine() + '\n';
			LineIterator.closeQuietly( it );
		} catch( URISyntaxException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from data file.", e );
			throw new UnexpectedServerException();
		}

		// The magic
		return new Gson().fromJson( jsonStr, clazz );
	}

}