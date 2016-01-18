package com.pratilipi.api.impl.pratilipi.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

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
	}
	
	public static class Author {
		private String name;
		private String pageUrl;
	}
	
	
	private List<Pratilipi> pratilipiList;
	private String cursor;

	
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse( List<Pratilipi> pratilipiList, String cursor ) {
		this.pratilipiList = pratilipiList;
		this.cursor = cursor;
	}
	
}
