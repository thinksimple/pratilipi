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

		private String summary;
		
		private String pageUrl;
		private String coverImageUrl;
		private String readPageUrl;
	
		private PratilipiContentType contentType;

		private String index;
		
		private Long ratingCount;
		private Float averageRating;

		private Boolean hasAccessToUpdate;

		
		public Pratilipi( PratilipiData pratilipi ) {
			this.pratilipiId = pratilipi.getId();
			this.title = pratilipi.getTitle() == null ? pratilipi.getTitleEn() : pratilipi.getTitle();
			if( UxModeFilter.isAndroidApp() )
				this.language = pratilipi.getLanguage();
			if( pratilipi.getAuthor() != null )
				this.author = new Author( pratilipi.getAuthor() );
			if( UxModeFilter.isAndroidApp() )
				this.summary = pratilipi.getSummary();
			this.pageUrl = pratilipi.getPageUrl();
			this.coverImageUrl = pratilipi.getCoverImageUrl();
			this.readPageUrl = pratilipi.getReadPageUrl();
			if( UxModeFilter.isAndroidApp() )
				this.contentType = pratilipi.getContentType();
			if( UxModeFilter.isAndroidApp() )
				this.index = pratilipi.getIndex();
			this.ratingCount = pratilipi.getRatingCount();
			this.averageRating = pratilipi.getAverageRating();
			this.hasAccessToUpdate = pratilipi.hasAccessToUpdate();
		}
		
		
		public Long getId() {
			return pratilipiId;
		}
		
		
		public String getTitle() {
			return title;
		}
		
		public Language getLanguage() {
			return language;
		}
		
		public Author getAuthor() {
			return author;
		}

		public String getSummary() {
			return summary;
		}

		
		public String getPageUrl() {
			return pageUrl;
		}
		
		public String getCoverImageUrl() {
			return coverImageUrl;
		}
		
		public String getReadPageUrl() {
			return readPageUrl;
		}

		
		public PratilipiContentType getContentType() {
			return contentType;
		}

		
		public String getIndex() {
			return index;
		}

		
		public Long getRatingCount() {
			return ratingCount;
		}
		
		public Float getAverageRating() {
			return averageRating;
		}

		
		public Boolean hasAccessToUpdate() {
			return hasAccessToUpdate;
		}
		
	}
	
	public static class Author {
		
		private String name;
		private String pageUrl;
		
		
		private Author( AuthorData authorData ) {
			this.name = authorData.getName() == null ? authorData.getNameEn() : authorData.getName();
			this.pageUrl = authorData.getPageUrl();
		}
		
		
		public String getName() {
			return name;
		}
		
		public String getPageUrl() {
			return pageUrl;
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
