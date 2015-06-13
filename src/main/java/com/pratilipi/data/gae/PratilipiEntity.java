package com.pratilipi.data.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.type.Pratilipi;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PRATILIPI" )
public class PratilipiEntity implements Pratilipi {

	@PrimaryKey
	@Persistent( column = "PRATILIPI_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "TITLE" )
	private String title;
	
	@Persistent( column = "TITLE_EN" )
	private String titleEn;
	
	@Persistent( column = "LANGUAGE" )
	private Language language;

	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	@Persistent( column = "AUTHOR_ID" )
	private Long authorId;
	
	@Persistent( column = "PUBLISHER_ID" )
	private Long publisherId;

	@Persistent( column = "SUMMARY" )
	private Text summary;

	@Persistent( column = "PUBLICATION_YEAR" )
	private Long publicationYear;

	
	@Persistent( column = "PUBLIC_DOMAIN" )
	private Boolean publicDomain;
	
	@Persistent( column = "PRATILIPI_TYPE" )
	private PratilipiType type;
	
	@Persistent( column = "CONTENT_TYPE" )
	private PratilipiContentType contentType;
	
	@Persistent( column = "STATE" )
	private PratilipiState state;
	
	@Persistent( column = "CUSTOM_COVER" )
	private Boolean customCover;

	@Persistent( column = "LISTING_DATE" )
	private Date listingDate;

	@Persistent( column = "LAST_UPDATED" )
	private Date lastUpdated;

	
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

	@Persistent( column = "TOTAL_RATING" )
	private Long totalRating;

	@Persistent( column = "RELEVANCE_OFFSET" )
	private Long relevanceOffset;
	

	@Persistent( column = "READ_COUNT_OFFSET" )
	private Long readCountOffset;
	
	@Persistent( column = "READ_COUNT" )
	private Long readCount;
	
	@Persistent( column = "FB_LIKE_SHARE_COUNT" )
	private Long fbLikeShareCount;

	
	@Persistent( column = "LAST_PROCESS_DATE" )
	private Date lastProcessDate;

	@Persistent( column = "NEXT_PROCESS_DATE" )
	private Date nextProcessDate;

	
	public PratilipiEntity() {}
	
	public PratilipiEntity( Long id ) {
		this.id = id;
	}

	
	@Override
	public Long getId() {
		return id;
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
	public Language getLanguage() {
		return language;
	}

	@Override
	public void setLanguage( Language language ) {
		this.language = language;
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
	public String getSummary() {
		return summary == null ? null : summary.getValue();
	}

	@Override
	public void setSummary( String summary ) {
		this.summary = summary == null ? null : new Text( summary );
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
	public PratilipiType getType() {
		return type;
	}
	
	@Override
	public void setType( PratilipiType pratilipiType ) {
		this.type = pratilipiType;
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
	public Boolean hasCustomCover() {
		return customCover == null ? false : customCover;
	}

	@Override
	public void setCustomCover( Boolean customCover ) {
		this.customCover = customCover;
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
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated;
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
		return ratingCount == null ? 0 : ratingCount;
	}

	@Override
	public void setRatingCount( Long ratingCount ) {
		this.ratingCount = ratingCount;
	}
	
	@Override
	public Long getTotalRating() {
		return totalRating == null ? 0 : totalRating;
	}

	@Override
	public void setTotalRating( Long totalRating ) {
		this.totalRating = totalRating;
	}
	
	
	@Override
	public Long getReadCount() {
		return readCount == null
				? ( readCountOffset == null ? 0L : readCountOffset )
				: ( readCountOffset == null ? readCount : readCount + readCountOffset );
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