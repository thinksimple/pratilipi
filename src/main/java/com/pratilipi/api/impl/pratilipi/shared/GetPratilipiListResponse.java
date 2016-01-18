package com.pratilipi.api.impl.pratilipi.shared;

import java.util.ArrayList;
import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.filter.UxModeFilter;

@SuppressWarnings("unused")
public class GetPratilipiListResponse extends GenericResponse { 

	public static class Pratilipi {
		
		private Long pratilipiId;
		
		private String title;
		private Language language;
		private Author author;

		private String pageUrl;
		private String coverImageUrl;
		private String readPageUrl;
	
		private PratilipiContentType contentType;

		private Long ratingCount;
		private Float averageRating;

		private Boolean hasAccessToUpdate;

		
		private Pratilipi( PratilipiData pratilipi ) {
			this.pratilipiId = pratilipi.getId();
			this.title = pratilipi.getTitle() == null ? pratilipi.getTitleEn() : pratilipi.getTitle();
			if( UxModeFilter.isAndroidApp() )
				this.language = pratilipi.getLanguage();
			this.author = new Author( pratilipi.getAuthor() );
			this.pageUrl = pratilipi.getPageUrl();
			this.coverImageUrl = pratilipi.getCoverImageUrl();
			this.readPageUrl = pratilipi.getReadPageUrl();
			if( UxModeFilter.isAndroidApp() )
				this.contentType = pratilipi.getContentType();
			this.ratingCount = pratilipi.getRatingCount();
			this.averageRating = pratilipi.getAverageRating();
			this.hasAccessToUpdate = pratilipi.hasAccessToUpdate();
		}
		
	}
	
	public static class Author {
		
		private String name;
		private String pageUrl;
		
		
		private Author( AuthorData authorData ) {
			this.name = authorData.getName() == null ? authorData.getNameEn() : authorData.getName();
			this.pageUrl = authorData.getPageUrl();
		}
		
	}
	
	
	private List<Pratilipi> pratilipiList;
	private String cursor;

	
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse( List<PratilipiData> pratilipiList, String cursor ) {
		this.pratilipiList = new ArrayList<>( pratilipiList.size() ); 
		for( PratilipiData pratilipi : pratilipiList )
			this.pratilipiList.add( new Pratilipi( pratilipi ) );
		this.cursor = cursor;
	}
	
}
