package com.pratilipi.data.util;

import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.AppProperty;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;


public class PratilipiDataUtil {
	
	public static String createCoverImageUrl( Pratilipi pratilipi ) {
		if( pratilipi.hasCustomCover() ) {
			String domain = "//" + pratilipi.getId() % 10 + "." + AppProperty.get( "cdn" );
			String uri = "/pratilipi-cover/150/" + pratilipi.getId() + "?" + pratilipi.getLastUpdated().getTime();
			return domain + uri;
		} else {
			String domain = "//10." + AppProperty.get( "cdn" );
			String uri = "/pratilipi-cover/150/pratilipi";
			return domain + uri;
		}
	}

	public static double calculateRelevance( Pratilipi pratilipi, Author author ) {
		double relevance = pratilipi.getReadCount();
		if( author != null && author.getContentPublished() > 1L )
			relevance = relevance + (double) author.getTotalReadCount() / (double) author.getContentPublished();
		return relevance;
	}

	public static PratilipiData createData( Pratilipi pratilipi, Author author ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page pratilipiPage = dataAccessor.getPage( PageType.PRATILIPI, pratilipi.getId() );
		
		
		PratilipiData pratilipiData = new PratilipiData();

		pratilipiData.setId( pratilipi.getId() );
		pratilipiData.setTitle( pratilipi.getTitle() );
		pratilipiData.setTitleEn( pratilipi.getTitleEn() );
		pratilipiData.setLanguage( pratilipi.getLanguage() );
		pratilipiData.setAuthorId( pratilipi.getAuthorId() );
		pratilipiData.setAuthor( AuthorDataUtil.createData( author ) );
		pratilipiData.setSummary( pratilipi.getSummary() );
		pratilipiData.setPublicationYear( pratilipi.getPublicationYear() );
		
		pratilipiData.setPageUrl( pratilipiPage.getUri() );
		pratilipiData.setPageUrlAlias( pratilipiPage.getUriAlias() );
		pratilipiData.setCoverImageUrl( createCoverImageUrl( pratilipi ) );
		pratilipiData.setReaderPageUrl( PageType.READ.getUrlPrefix() + pratilipi.getId() );
		pratilipiData.setWriterPageUrl( PageType.WRITE.getUrlPrefix() + pratilipi.getId() );
		
		pratilipiData.setType( pratilipi.getType() );
		pratilipiData.setState( pratilipi.getState() );
		pratilipiData.setListingDate( pratilipi.getListingDate() );
		pratilipiData.setLastUpdated( pratilipi.getLastUpdated() );
		
		pratilipiData.setIndex( pratilipi.getIndex() );
		pratilipiData.setWordCount( pratilipi.getWordCount() );
		pratilipiData.setPageCount( pratilipi.getPageCount() );
		
		pratilipiData.setReviewCount( pratilipi.getReviewCount() );
		pratilipiData.setRatingCount( pratilipi.getRatingCount() );
		pratilipiData.setAverageRating(
				pratilipi.getRatingCount() == 0L
						? 5F : (float) ( (double) pratilipi.getTotalRating() / pratilipi.getRatingCount() ) );
		pratilipiData.setRelevance( calculateRelevance( pratilipi, author ) );
		
		pratilipiData.setReadCount( pratilipi.getReadCount() );

		return pratilipiData;
	}

}
