package com.pratilipi.service.shared.data;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;

public class PratilipiData implements IsSerializable {

	private Long id;
	
	private PratilipiType type;
	private boolean hasType;

	private String pageUrl;
	private String pageUrlAlias;
	private String coverImageUrl;
	private String coverImageUploadUrl;
	private String coverImage300UploadUrl;
	private String imageContentUploadUrl;
	private String wordContentUploadUrl;
	private String pdfContentUploadUrl;
	private String readerPageUrl;
	private String writerPageUrl;
	
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

	private LanguageData languageData;

	
	private Long authorId;
	private boolean hasAuthorId;
	
	private AuthorData authorData;

	private Long publisherId;
	private boolean hasPublisherId;

	private String publisherName;
		
	private Long publicationYear;
	private boolean hasPublicationYear;
	
	private Date listingDate;
	private Date LastUpdated;

	
	private String summary;
	private boolean hasSummary;
	
	private String index;
	private boolean hasIndex;

	private Long wordCount;
	private boolean hasWordCount;
	
	private Long pageCount;
	private boolean hasPageCount;
	
	private Long reviewCount;
	
	private Long ratingCount;
	
	private Long starCount;
	
	private PratilipiContentType contentType;
	private boolean hasContentType;
	
	private PratilipiState state;
	private boolean hasState;
	

	private List<Long> genreIdList;
	private List<String> genreNameList;
	
	
	public PratilipiData() {}
	
	public PratilipiData( Long id ) {
		this.id = id;
	}
	

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public PratilipiType getType(){
		return this.type;
	}
	
	public void setType( PratilipiType type ) {
		this.type = type;
		this.hasType = true;
	}
	
	public boolean hasType() {
		return hasType;
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

	public void setCoverImageUrl( String coverImageUrl ) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getCoverImageUploadUrl() {
		return coverImageUploadUrl;
	}

	public void setCoverImageUploadUrl( String coverImageUploadUrl ) {
		this.coverImageUploadUrl = coverImageUploadUrl;
	}

	public String getCoverImage300UploadUrl() {
		return coverImage300UploadUrl;
	}

	public void setCoverImage300UploadUrl( String coverImage300UploadUrl ) {
		this.coverImage300UploadUrl = coverImage300UploadUrl;
	}

	@Deprecated
	public String getImageContentUploadUrl() {
		return imageContentUploadUrl;
	}
	
	public void setImageContentUploadUrl( String imageContentUploadUrl ) {
		this.imageContentUploadUrl = imageContentUploadUrl;
	}
	
	public String getWordContentUploadUrl() {
		return wordContentUploadUrl;
	}
	
	public void setWordContentUplaodUrl( String wordContentUploadUrl ) {
		this.wordContentUploadUrl = wordContentUploadUrl;
	}
	
	public String getPdfContentUploadUrl() {
		return pdfContentUploadUrl;
	}
	
	public void setPdfContentUplaodUrl( String pdfContentUploadUrl ) {
		this.pdfContentUploadUrl = pdfContentUploadUrl;
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
	
	public LanguageData getLanguageData() {
		return languageData;
	}

	public void setLanguageData( LanguageData languageData ) {
		this.languageData = languageData;
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
	
	public AuthorData getAuthorData() {
		return authorData;
	}

	public void setAuthorData( AuthorData authorData ) {
		this.authorData = authorData;
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

	public Date getLastUpdated(){
		return this.LastUpdated;
	}
	
	public void setLastUpdated( Date lastUpdated ){
		this.LastUpdated = lastUpdated;
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
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	public void setContentType( PratilipiContentType contentType ){
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
	
	public List<Long> getGenreIdList() {
		return genreIdList;
	}

	public void setGenreIdList( List<Long> genreIdList ) {
		this.genreIdList = genreIdList;
	}
	
	public List<String> getGenreNameList() {
		return genreNameList;
	}

	public void setGenreNameList( List<String> genreNameList ) {
		this.genreNameList = genreNameList;
	}
	
}
