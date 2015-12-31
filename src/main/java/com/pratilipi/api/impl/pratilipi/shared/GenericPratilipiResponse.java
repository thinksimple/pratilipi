package com.pratilipi.api.impl.pratilipi.shared;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

@SuppressWarnings("unused")
public class GenericPratilipiResponse extends GenericResponse {
	
	public static class Author {
		private String name;
		private String pageUrl;
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

}
