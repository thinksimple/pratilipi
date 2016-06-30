package com.pratilipi.api.impl.pratilipi.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;

public class GenericPratilipiResponse extends GenericResponse {
	
	public static class Author {
		
		private Long authorId;
		private String name;
		private String pageUrl;

		public String getName() {
			return name;
		}
		
		public String getPageUrl() {
			return pageUrl;
		}
	
		
		public Author( AuthorData authorData ) {
			if( authorData != null ) {
				authorId = authorData.getId();
				name = authorData.getName() == null
						? authorData.getNameEn()
						: authorData.getName();
				pageUrl = authorData.getPageUrl();
			}
		}
		
	}
	

	private Long pratilipiId;
	
	private String title;
	private String titleEn;
	private Language language;
	private Author author;
	@Deprecated
	private String summary;
	
	private String pageUrl;
	private String coverImageUrl;
	private String readPageUrl;
	private String writePageUrl;

	private PratilipiType type;
	private PratilipiState state;
	
	private Long listingDateMillis;
	private Long lastUpdatedMillis;
	
	private Long reviewCount;
	private Long ratingCount;
	private Float averageRating;
	private Long readCount;
	private Long fbLikeShareCount;
	
	private Boolean hasAccessToUpdate;
	
	
	@SuppressWarnings("unused")
	private GenericPratilipiResponse() { }
	
	public GenericPratilipiResponse( PratilipiData pratilipiData ) {
		
		this.pratilipiId = pratilipiData.getId();
		
		this.title = pratilipiData.getTitle();
		this.titleEn = pratilipiData.getTitleEn();
		this.language = pratilipiData.getLanguage();
		this.author = new Author( pratilipiData.getAuthor() );
		this.summary = pratilipiData.getSummary();
		
		this.pageUrl = pratilipiData.getPageUrl();
		this.coverImageUrl = pratilipiData.getCoverImageUrl();
		this.readPageUrl = pratilipiData.getReadPageUrl();
		this.writePageUrl = pratilipiData.getWritePageUrl();
		
		this.type = pratilipiData.getType();
		this.state = pratilipiData.getState();
		
		this.listingDateMillis = pratilipiData.getListingDate().getTime();
		if( pratilipiData.getLastUpdated() != null )
			this.lastUpdatedMillis = pratilipiData.getLastUpdated().getTime();
		
		this.reviewCount = pratilipiData.getReviewCount();
		this.ratingCount = pratilipiData.getRatingCount();
		this.averageRating = pratilipiData.getAverageRating();
		this.readCount = pratilipiData.getReadCount();
		this.fbLikeShareCount = pratilipiData.getFbLikeShareCount();
		
		this.hasAccessToUpdate = pratilipiData.hasAccessToUpdate();
		
	}

	
	public Long getId() {
		return pratilipiId;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public String getTitleEn() {
		return titleEn;
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
	
	public String getCoverImageUrl( int width ) {
		return coverImageUrl.indexOf( '?' ) == -1
				? coverImageUrl + "?width=" + width
				: coverImageUrl + "&width=" + width;
	}

	public String getReadPageUrl() {
		return readPageUrl;
	}

	public String getWritePageUrl() {
		return writePageUrl;
	}

	
	public PratilipiType getType() {
		return type;
	}
	
	public PratilipiState getState() {
		return state;
	}

	
	public Long getListingDateMillis() {
		return listingDateMillis;
	}

	public Long getLastUpdatedMillis() {
		return lastUpdatedMillis;
	}

	
	public Long getReviewCount() {
		return reviewCount;
	}

	public Long getRatingCount() {
		return ratingCount;
	}
	
	public Float getAverageRating() {
		return averageRating;
	}

	public Long getReadCount() {
		return readCount;
	}

	public Long getFbLikeShareCount() {
		return fbLikeShareCount;
	}

	
	public Boolean hasAccessToUpdate() {
		return hasAccessToUpdate;
	}
	
}
