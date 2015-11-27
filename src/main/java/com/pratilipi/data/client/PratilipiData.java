package com.pratilipi.data.client;

import java.util.Date;
import java.util.List;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class PratilipiData {

	private Long pratilipiId;
	
	private String title;
	private transient boolean hasTitle;
	
	private String titleEn;
	private transient boolean hasTitleEn;
	
	private Language language;
	private transient boolean hasLanguage;
	
	private Long authorId;
	private transient boolean hasAuthorId;
	
	private AuthorData author;

	private String summary;
	private transient boolean hasSummary;
	
	private Integer publicationYear;
	private transient boolean hasPublicationYear;
	
	
	private String pageUrl;
	private String pageUrlAlias;
	private String coverImageUrl;
	private String readerPageUrl;
	private String writerPageUrl;

	
	private PratilipiType type;
	private transient boolean hasType;

	private PratilipiContentType contentType;
	private transient boolean hasContentType;

	private PratilipiState state;
	private transient boolean hasState;
	
	private Date listingDate;
	private Date lastUpdated;

	
	private String index;
	private transient boolean hasIndex;

	
	private Long wordCount;
	private transient boolean hasWordCount;
	
	private Integer pageCount;
	private transient boolean hasPageCount;
	
	
	private List<Long> categoryIdList;
	private List<String> categoryNameList;


	private Long reviewCount;
	
	private Long ratingCount;
	
	private Float averageRating;
	
	private Double relevance;


	private Long readCount;
	
	private Long fbLikeShareCount;


	
	public PratilipiData() {}
	
	public PratilipiData( Long id ) {
		this.pratilipiId = id;
	}
	

	public Long getId() {
		return pratilipiId;
	}

	public void setId( Long id ) {
		this.pratilipiId = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
		this.hasTitle = true;
	}
	
	public boolean hasTitle() {
		return hasTitle;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn( String titleEn ) {
		this.titleEn = titleEn;
		this.hasTitleEn = true;
	}
	
	public boolean hasTitleEn() {
		return hasTitleEn;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage( Language language ) {
		this.language = language;
		this.hasLanguage = true;
	}

	public boolean hasLanguage() {
		return hasLanguage;
	}
	
	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
		this.hasAuthorId = true;
	}

	public boolean hasAuthorId() {
		return hasAuthorId;
	}
	
	public AuthorData getAuthor() {
		return author;
	}

	public void setAuthor( AuthorData author ) {
		this.author = author;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary( String summary ) {
		this.summary = summary;
		this.hasSummary = true;
	}

	public boolean hasSummary() {
		return hasSummary;
	}
	
	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear( Integer publicationYear ) {
		this.publicationYear = publicationYear;
		this.hasPublicationYear = true;
	}

	public boolean hasPublicationYear() {
		return hasPublicationYear;
	}
	
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	}

	public String getPageUrlAlias() {
		return pageUrlAlias;
	}

	public void setPageUrlAlias( String pageUrlAlias ) {
		this.pageUrlAlias = pageUrlAlias;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public String getCoverImageUrl( int width ) {
		return coverImageUrl.indexOf( '?' ) == -1
				? coverImageUrl + "?width=" + width
				: coverImageUrl + "&width=" + width;
	}

	public void setCoverImageUrl( String coverImageUrl ) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getReaderPageUrl() {
		return readerPageUrl;
	}

	public void setReaderPageUrl( String readerPageUrl ) {
		this.readerPageUrl = readerPageUrl;
	}
	
	public String getWriterPageUrl(){
		return this.writerPageUrl;
	}
	
	public void setWriterPageUrl( String writerPageUrl ){
		this.writerPageUrl = writerPageUrl;
	}


	public PratilipiType getType() {
		return this.type;
	}
	
	public void setType( PratilipiType type ) {
		this.type = type;
		this.hasType = true;
	}
	
	public boolean hasType() {
		return hasType;
	}
	
	public PratilipiContentType getContentType() {
		return this.contentType;
	}
	
	public void setContentType( PratilipiContentType contentType ) {
		this.contentType = contentType;
		this.hasContentType = true;
	}
	
	public boolean hasContentType() {
		return hasContentType;
	}
	
	public PratilipiState getState() {
		return state;
	}
	
	public void setState( PratilipiState state ) {
		this.state = state;
		this.hasState = true;
	}
	
	public boolean hasState() {
		return hasState;
	}
	
	public Date getListingDate() {
		return listingDate;
	}

	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate;
	}

	public Date getLastUpdated(){
		return this.lastUpdated;
	}
	
	public void setLastUpdated( Date lastUpdated ){
		this.lastUpdated = lastUpdated;
	}


	public String getIndex() {
		return index;
	}
	
	public void setIndex( String index ) {
		this.index = index;
		this.hasIndex = true;
	}

	public boolean hasIndex() {
		return hasIndex;
	}

	public Long getWordCount() {
		return wordCount;
	}

	public void setWordCount( Long wordCount ) {
		this.wordCount = wordCount;
		this.hasWordCount = true;
	}
	
	public boolean hasWordCount() {
		return hasWordCount;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount( Integer pageCount ) {
		this.pageCount = pageCount;
		this.hasPageCount = true;
	}
	
	public boolean hasPageCount() {
		return hasPageCount;
	}
	
	
	public List<Long> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList( List<Long> categoryIdList ) {
		this.categoryIdList = categoryIdList;
	}
	
	public List<String> getCategoryNameList() {
		return categoryNameList;
	}

	public void setCategoryNameList( List<String> categoryNameList ) {
		this.categoryNameList = categoryNameList;
	}
	

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating( Float averageRating ) {
		this.averageRating = averageRating;
	}
	
	public Double getRelevance() {
		return relevance;
	}
	
	public void setRelevance( Double relevance ) {
		this.relevance = relevance;
	}
	
	
	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount( Long readCount ) {
		this.readCount = readCount;
	}
	
	public Long getFbLikeShareCount() {
		return fbLikeShareCount;
	}

	public void setFbLikeShareCount( Long fbLikeShareCount ) {
		this.fbLikeShareCount = fbLikeShareCount;
	}
	
}
