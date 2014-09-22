package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;

public class PratilipiData implements IsSerializable {

	private Long id;
	
	private PratilipiType type;

	private String pageUrl;
	private String coverImageUrl;
	
	private boolean isPublicDomain;
	private boolean hasPublicDomain;
	
	private String isbn;
	private boolean hasIsbn;
	
	private String title;
	private boolean hasTitle;
	
	private String titleEn;
	private boolean hasTitleEn;
	
	private Long languageId;
	private boolean hasLanguageId;

	private String languageName;
	private String languageNameEn;
	
	
	private Long authorId;
	private boolean hasAuthorId;
	
	private String authorName;
	private String authorNameEn;
	
	private String authorPageUrl;
	
	private Long publisherId;
	private boolean hasPublisherId;

	private String publisherName;
		
	private Long publicationYear;
	private boolean hasPublicationYear;
	
	private Date listingDate;

	
	private String summary;
	private boolean hasSummary;
	
	private String content;
	private boolean hasContent;
	
	private Long wordCount;
	private boolean hasWordCount;
	
	private Long pageCount;
	private boolean hasPageCount;
	
	private Long reviewCount;
	
	private Long ratingCount;
	
	private Long starCount;
	
	private PratilipiState state;
	private boolean hasState;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public PratilipiType getType(){
		return this.type;
	}
	
	public void setType( PratilipiType type ){
		this.type = type;
	}
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl( String coverImageUrl ) {
		this.coverImageUrl = coverImageUrl;
	}

	public boolean isPublicDomain() {
		return isPublicDomain;
	}

	public void setPublicDomain( boolean isPublicDomain ) {
		this.isPublicDomain = isPublicDomain;
		this.hasPublicDomain = true;
	}

	public boolean hasPublicDomain() {
		return hasPublicDomain;
	}

	public String getIsbn(){
		return isbn;
	}
	
	public void setIsbn( String isbn ){
		this.isbn = isbn;
		this.hasIsbn = true;
	}
	
	public boolean hasIsbn(){
		return this.hasIsbn;
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

	
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
		this.hasLanguageId = true;
	}

	public boolean hasLanguageId() {
		return hasLanguageId;
	}
	
	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName( String languageName ) {
		this.languageName = languageName;
	}

	public String getLanguageNameEn() {
		return languageNameEn;
	}

	public void setLanguageNameEn( String languageNameEn ) {
		this.languageNameEn = languageNameEn;
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
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName( String authorName ) {
		this.authorName = authorName;
	}
	
	public String getAuthorNameEn() {
		return authorNameEn;
	}

	public void setAuthorNameEn( String authorNameEn ) {
		this.authorNameEn = authorNameEn;
	}
	
	public String getAuthorPageUrl() {
		return authorPageUrl;
	}

	public void setAuthorPageUrl( String authorPageUrl ) {
		this.authorPageUrl = authorPageUrl;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
		this.hasPublisherId = true;
	}

	public boolean hasPublisherId() {
		return hasPublisherId;
	}
	
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName( String publisherName ) {
		this.publisherName = publisherName;
	}
	
	public Long getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear( Long publicationYear ) {
		this.publicationYear = publicationYear;
		this.hasPublicationYear = true;
	}

	public boolean hasPublicationYear() {
		return hasPublicationYear;
	}
	
	public Date getListingDate() {
		return listingDate;
	}

	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate;
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
	
	public String getContent() {
		return content;
	}

	public void setContent( String content ) {
		this.content = content;
		this.hasContent = true;
	}

	public boolean hasContent() {
		return hasContent;
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
	
	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount( Long pageCount ) {
		this.pageCount = pageCount;
		this.hasPageCount = true;
	}
	
	public boolean hasPageCount() {
		return hasPageCount;
	}
	
	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		//this.reviewCount = reviewCount;
	}

	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		//this.ratingCount = ratingCount;
	}

	public Long getStarCount() {
		return starCount;
	}

	public void setStarCount(Long starCount) {
		//this.starCount = starCount;
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
	
}
