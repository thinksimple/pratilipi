package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.data.transfer.Pratilipi;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PRATILIPI" )
public class PratilipiEntity implements Pratilipi {

	@PrimaryKey
	@Persistent( column = "PRATILIPI_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PRATILIPI_TYPE" )
	private PratilipiType type;
	
	@Persistent( column = "PUBLIC_DOMAIN" )
	private Boolean publicDomain;
	
	@Persistent( column = "TITLE" )
	private String title;
	
	@Persistent( column = "TITLE_EN" )
	private String titleEn;
	
	
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	
	@Persistent( column = "AUTHOR_ID" )
	private Long authorId;
	
	@Persistent( column = "PUBLISHER_ID" )
	private Long publisherId;

	@Persistent( column = "PUBLICATION_YEAR" )
	private Long publicationYear;

	@Persistent( column = "LISTING_DATE" )
	private Date listingDate;

	
	@Persistent( column = "CUSTOM_COVER" )
	private Boolean customCover;

	@Persistent( column = "SUMMARY" )
	private Text summary;

	@Persistent( column = "INDEX" )
	private Text index;

	@Persistent( column = "WORD_COUNT" )
	private Long wordCount;

	@Persistent( column = "PAGE_COUNT" )
	private Integer pageCount;
	
	@Persistent( column = "REVIEW_COUNT" )
	private Long reviewCount;
	
	@Persistent( column = "RATING_COUNT" )
	private Long ratingCount;
	
	@Persistent( column = "STAR_COUNT" )
	private Long starCount;

	@Persistent( column = "RELEVANCE_OFFSET" )
	private Long relevanceOffset;
	

	@Persistent( column = "CONTENT_TYPE" )
	private PratilipiContentType contentType;
	
	@Persistent( column = "STATE" )
	private PratilipiState state;
	
	@Persistent( column = "LAST_UPDATED" )
	private Date lastUpdated;

	
	@Persistent( column = "READ_COUNT" )
	private Long readCount;
	
	@Persistent( column = "FB_LIKE_SHARE_COUNT" )
	private Long fbLikeShareCount;
	
	@Persistent( column = "LAST_PROCESS_DATE" )
	private Date lastProcessDate;

	@Persistent( column = "NEXT_PROCESS_DATE" )
	private Date nextProcessDate;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public PratilipiType getType() {
		return type;
	}
	
	@Override
	public void setType( PratilipiType pratilipiType ) {
		this.type = pratilipiType;
	}

	@Override
	public boolean isPublicDomain() {
		return publicDomain == null ? false : publicDomain;
	}
	
	@Override
	public void setPublicDomain( boolean isPublicDomain ) {
		this.publicDomain = isPublicDomain;
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
	}

	@Override
	public String getTitleEn() {
		return this.titleEn;
	}

	@Override
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	
	@Override
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	@Override
	public Long getAuthorId() {
		return authorId;
	}

	@Override
	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}
	
	@Override
	public Long getPublisherId() {
		return publisherId;
	}

	@Override
	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}
	
	@Override
	public Long getPublicationYear() {
		return publicationYear;
	}

	@Override
	public void setPublicationYear( Long publicationYear ) {
		this.publicationYear = publicationYear;
	}

	@Override
	public Date getListingDate() {
		return listingDate;
	}

	@Override
	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate;
	}


	@Override
	public Boolean hasCustomCover() {
		return customCover == null ? false : customCover;
	}

	@Override
	public void setCustomCover( Boolean customCover ) {
		this.customCover = customCover;
	}
	
	@Override
	public String getSummary() {
		return summary == null ? null : summary.getValue();
	}

	@Override
	public void setSummary( String summary ) {
		this.summary = summary == null ? null : new Text( summary );
	}
	
	@Override
	public String getIndex() {
		return index == null ? null : index.getValue();
	}

	@Override
	public void setIndex( String index ) {
		this.index = index == null ? null : new Text( index );
	}
	
	@Override
	public Long getWordCount() {
		return wordCount;
	}

	@Override
	public void setWordCount( Long wordCount ) {
		this.wordCount = wordCount;
	}
	
	@Override
	public Integer getPageCount() {
		return pageCount == null ? 0 : pageCount;
	}

	@Override
	public void setPageCount( Integer pageCount ) {
		this.pageCount = pageCount;
	}
	
	@Override
	public Long getReviewCount() {
		return reviewCount;
	}

	@Override
	public void setReviewCount( Long reviewCount ) {
		this.reviewCount = reviewCount;
	}

	@Override
	public Long getRatingCount() {
		return ratingCount == null  || ratingCount == 0 ? 1 : ratingCount;
	}

	@Override
	public void setRatingCount( Long ratingCount ) {
		this.ratingCount = ratingCount;
	}
	
	@Override
	public Long getStarCount() {
		return starCount == null ? 0 : starCount;
	}

	@Override
	public void setStarCount( Long starCount ) {
		this.starCount = starCount;
	}
	
	@Override
	public Long getRelevanceOffset() {
		return relevanceOffset == null ? 0L : relevanceOffset;
	}

	@Override
	public void setRelevanceOffset( Long relevanceOffset ) {
		this.relevanceOffset = relevanceOffset;
	}
	
	@Override
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	@Override
	public void setContentType( PratilipiContentType contentType ) {
		this.contentType = contentType;
	}
	
	@Override
	public PratilipiState getState() {
		return state;
	}
	
	@Override
	public void setState( PratilipiState state ) {
		this.state = state;
	}
	
	@Override
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated;
	}


	@Override
	public Long getReadCount() {
		return readCount == null ? 0L : readCount;
	}

	@Override
	public void setReadCount( Long readCount ) {
		this.readCount = readCount;
	}
	
	@Override
	public Long getFbLikeShareCount() {
		return fbLikeShareCount == null ? 0L : fbLikeShareCount;
	}

	@Override
	public void setFbLikeShareCount( Long fbLikeShareCount ) {
		this.fbLikeShareCount = fbLikeShareCount;
	}
	
	@Override
	public Date getLastProcessDate() {
		return lastProcessDate == null ? lastUpdated : lastProcessDate;
	}

	@Override
	public void setLastProcessDate( Date lastProcessDate ) {
		this.lastProcessDate = lastProcessDate;
	}

	@Override
	public Date getNextProcessDate() {
		return nextProcessDate;
	}

	@Override
	public void setNextProcessDate( Date nextProcessDate ) {
		this.nextProcessDate = nextProcessDate;
	}

}
