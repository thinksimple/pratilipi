package com.pratilipi.api.impl.pratilipi.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.client.AuthorData;

@SuppressWarnings("unused")
public class GenericPratilipiResponse extends GenericResponse {
	
	public static class AuthorResponse {
		private String name;
		private String pageUrl;
	}
	

	private Long pratilipiId;
	
	private String title;
	private String titleEn;
	private Language language;
	private AuthorResponse author;
	@Deprecated
	private String summary;
	private Integer publicationYear;
	
	private String pageUrl;
	private String coverImageUrl;
	private String readPageUrl;
	private String writePageUrl;

	private PratilipiType type;
	private PratilipiState state;
	
	private Date listingDate;
	private Date lastUpdated;
	
	private Long reviewCount;
	private Long ratingCount;
	private Float averageRating;
	private Long readCount;
	private Long fbLikeShareCount;

}
