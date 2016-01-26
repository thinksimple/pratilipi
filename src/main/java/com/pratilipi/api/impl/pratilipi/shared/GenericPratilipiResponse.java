package com.pratilipi.api.impl.pratilipi.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class GenericPratilipiResponse extends GenericResponse {
	
	public static class Author {
		
		private String name;
		private String pageUrl;

		public String getName() {
			return name;
		}
		
		public String getPageUrl() {
			return pageUrl;
		}
		
	}
	

	private Long pratilipiId;
	
	private String title;
	private String titleEn;
	private Language language;
	private Author author;
	@Deprecated
	private String summary;
	private Integer publicationYear;
	
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

	public Integer getPublicationYear() {
		return publicationYear;
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

	
	public Date getListingDate() {
		return listingDateMillis == null ? null : new Date( listingDateMillis );
	}

	public Date getLastUpdated() {
		return lastUpdatedMillis == null ? null : new Date( lastUpdatedMillis );
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
