package com.pratilipi.pagecontent.pratilipis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FacebookApi;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.server.SerializationUtil;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.service.shared.data.PratilipiData;

public class PratilipisContentProcessor extends PageContentProcessor<PratilipisContent> {

	@Override
	protected CacheLevel getCacheLevel( PratilipisContent pratilipisContent, HttpServletRequest request ) {
		return pratilipisContent.getPratilipiIdList() != null  ? CacheLevel.GLOBAL : CacheLevel.NONE;
	}
	
	@Override
	public Resource[] getDependencies( PratilipisContent pratilipisContent, HttpServletRequest request){
	
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Language language = null;
		if( pratilipisContent.getLanguageId() != null ){
			language = dataAccessor.getLanguage( pratilipisContent.getLanguageId() );
		}
		
		String ogFbAppId = FacebookApi.getAppId( request );
		String ogLocale = language == null ? 
									"hi_IN" : 
									language.getNameEn().toLowerCase().substring( 0,2 ) + "_IN";
		String ogTitle = "";
		String ogUrl = "";
		String ogDescription = "";
		if( request.getRequestURI().equals( "/" )){
			ogTitle = "Pratilipi.com";
			ogUrl = "www.pratilipi.com";
			ogDescription = "A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.";
		} else {
			ogTitle = ( language == null ? "" : language.getNameEn() + " " ) 
							+ ( pratilipisContent.getPratilipiType() == null ?
									"" : pratilipisContent.getPratilipiType().toString().toLowerCase() );
			ogUrl = request.getRequestURI();
			ogDescription = "List of all " + ogTitle;
		}
		String ogPublisher = null;
		if( language != null && language.getNameEn().equals( "Tamil" ))
			ogPublisher = "https://www.facebook.com/pages/%E0%AE%AA%E0%AF%8D%E0%AE%B0%E0%AE%A4%E0%AE%BF%E0%AE%B2%E0%AE%BF%E0%AE%AA%E0%AE%BF/448203822022932";
		else if( language != null && language.getNameEn().equals( "Gujarati" ))
			ogPublisher = "https://www.facebook.com/pratilipiGujarati";
		else
			ogPublisher = "https://www.facebook.com/Pratilipidotcom";
		
		final String fbOgTags = "<meta property='fb:app_id' content='" + ogFbAppId + "' />"
				+ "<meta property='og:locale' content='" + ogLocale + "' />"
				+ "<meta property='og:type' content='website' />"
				+ "<meta property='og:title' content='" + ogTitle + "' />"
				+ "<meta property='og:url' content='" + ogUrl + "' />"
				+ "<meta property='og:description' content='" + ogDescription + "' />"
				+ "<meta property='og:publisher' content='" + ogPublisher + "' />";
		
		
		return new Resource[] {

			new Resource() {
				@Override
				public String getTag() {
					return fbOgTags;
				}
			},

		};
	}
	
	@Override
	public String generateTitle( PratilipisContent pratilipisContent, HttpServletRequest request ) {
		if( pratilipisContent.getTitle() != null )
			return pratilipisContent.getTitle();
		
		PratilipiType pratilipiType = pratilipisContent.getPratilipiType();
		if( pratilipiType != null && pratilipisContent.getLanguageId() != null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			Language language = dataAccessor.getLanguage( pratilipisContent.getLanguageId() );
			return language.getNameEn() + " " + pratilipiType.getNamePlural();

		} else if( pratilipiType != null ) {
			return pratilipiType.getNamePlural();
			
		} else {
			return null;
		}
	
	}

	@Override
	public String generateHtml( PratilipisContent pratilipisContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		Map<String, Object> dataModel = new HashMap<>();

		List<PratilipiData> pratilipiDataList; // TODO
		if( pratilipisContent.getPratilipiIdList() != null ) {

			pratilipiDataList = pratilipiHelper.createPratilipiDataList(
					dataAccessor.getPratilipiList( pratilipisContent.getPratilipiIdList() ),
					false, true, false );

		} else {

			PratilipiFilter pratilipiFilter = pratilipisContent.toFilter();
			pratilipiFilter.setOrderByReadCount( false );
			
			DataListCursorTuple<Pratilipi> pratilipiListCursorTuple =
					dataAccessor.getPratilipiList( pratilipiFilter, null, 20 );
			pratilipiDataList = pratilipiHelper.createPratilipiDataList(
					pratilipiListCursorTuple.getDataList(), false, true, false );
		
			dataModel.put( "pratilipiFilterEncodedStr", SerializationUtil.encode( pratilipiFilter ) );
		
		}
		

		// Creating data model required for template processing
		dataModel.put( "title", getTitle( pratilipisContent, request ) );
		dataModel.put( "pratilipiDataList", pratilipiDataList );
		

		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
}
