package com.pratilipi.api.pratilipi.shared;

import java.util.Date;

import com.pratilipi.api.author.shared.GetAuthorResponse;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;

@SuppressWarnings( "unused")
public class GetPratilipiResponse extends GenericResponse {

	private Long pratilipiId;
	
	private String title;
	private String titleEn;
	
	private Language language;
	
	private Long authorId;
	private GetAuthorResponse author;

	private String summary;
	private Integer publicationYear;
	
	private String pageUrl;
	private String pageUrlAlias;
	private String coverImageUrl;
	private String readerPageUrl;
	private String writerPageUrl;

	private PratilipiType type;
	private PratilipiState state;
	
	private Long listingDate;
	private Long lastUpdated;
	
	private String index;
	private Long wordCount;
	private Integer pageCount;

	private Long reviewCount;
	private Long ratingCount;
	private Float averageRating;
	private Double relevance;

	private Long readCount;

	
	public void setPratilipiId(  Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}


	public void setTitle(  String title ) {
		this.title = title;
	}

	public void setTitleEn( String titleEn ) {
		this.titleEn = titleEn;
	}


	public void setLanguage( Language language ) {
		this.language = language;
	}


	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}

	public void setAuthor( GetAuthorResponse author ) {
		this.author = author;
	}


	public void setSummary( String summary ) {
		this.summary = summary;
	}

	public void setPublicationYear( Integer publicationYear ) {
		this.publicationYear = publicationYear;
	}


	public void setPageUrl( String pageUrl  ) {
		this.pageUrl = pageUrl;
	}

	public void setPageUrlAlias( String pageUrlAlias  ) {
		this.pageUrlAlias = pageUrlAlias;
	}

	public void setCoverImageUrl( String coverImageUrl  ) {
		this.coverImageUrl = coverImageUrl;
	}

	public void setReaderPageUrl( String readerPageUrl  ) {
		this.readerPageUrl = readerPageUrl;
	}

	public void setWriterPageUrl( String writerPageUrl  ) {
		this.writerPageUrl = writerPageUrl;
	}

	
	public void setType( PratilipiType type  ) {
		this.type = type;
	}

	public void setState( PratilipiState state ) {
		this.state = state;
	}


	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate.getTime();
	}

	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated.getTime();
	}


	public void setIndex( String index ) {
		this.index = index;
	}

	public void setWordCount( Long wordCount ) {
		this.wordCount = wordCount;
	}


	public void setPageCount( Integer pageCount ) {
		this.pageCount = pageCount;
	}

	public void setReviewCount( Long reviewCount ) {
		this.reviewCount = reviewCount;
	}

	public void setRatingCount( Long ratingCount ) {
		this.ratingCount = ratingCount;
	}

	public void setAverageRating( Float averageRating ) {
		this.averageRating = averageRating;
	}

	public void setRelevance( Double relevance ) {
		this.relevance = relevance;
	}


	public void setReadCount( Long readCount ) {
		this.readCount = readCount;
	}
	
}