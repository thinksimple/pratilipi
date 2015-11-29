package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.Author;

@PersistenceCapable( table = "AUTHOR" )
public class AuthorEntity implements Author {
	
	private static final long serialVersionUID = -3668863380117991344L;

	@PrimaryKey
	@Persistent( column = "AUTHOR_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;

	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "FIRST_NAME" )
	private String firstName;
	
	@Persistent( column = "LAST_NAME" )
	private String lastName;

	@Persistent( column = "PEN_NAME" )
	private String penName;
	
	@Persistent( column = "FIRST_NAME_EN" )
	private String firstNameEn;
	
	@Persistent( column = "LAST_NAME_EN" )
	private String lastNameEn;

	@Persistent( column = "PEN_NAME_EN" )
	private String penNameEn;
	
	@Persistent( column = "EMAIL" )
	private String email;

	@Persistent( column = "LANGUAGE" )
	private Language language;

	@Deprecated
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	@Persistent( column = "SUMMARY" )
	private Text summary;
	

	@Deprecated
	@Persistent( column = "CUSTOM_COVER" )
	private Boolean customCover;

	@Persistent( column = "CUSTOM_IMAGE" )
	private Boolean customImage;

	@Persistent( column = "REGISTRATION_DATE" )
	private Date registrationDate;

	@Persistent( column = "LAST_UPDATED" )
	private Date lastUpdated;

	
	@Persistent( column = "CONTENT_PUBLISHED" )
	private Integer contentPublished;
	
	@Persistent( column = "TOTAL_READ_COUNT" )
	private Long totalReadCount;

	@Persistent( column = "TOTAL_FB_LIKE_SHARE_COUNT" )
	private Long totalFbLikeShareCount;

	
	@Persistent( column = "LAST_PROCESS_DATE" )
	private Date lastProcessDate;

	@Persistent( column = "NEXT_PROCESS_DATE" )
	private Date nextProcessDate;
	
	
	public AuthorEntity() {}
	
	public AuthorEntity( Long id ) {
		this.id = id;
	}

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	@Override
	public String getPenName() {
		return penName;
	}

	@Override
	public void setPenName( String penName ) {
		this.penName = penName;
	}

	@Override
	public String getFirstNameEn() {
		return firstNameEn;
	}

	@Override
	public void setFirstNameEn( String firstNameEn ) {
		this.firstNameEn = firstNameEn;
	}

	@Override
	public String getLastNameEn() {
		return lastNameEn;
	}

	@Override
	public void setLastNameEn( String lastNameEn ) {
		this.lastNameEn = lastNameEn;
	}

	@Override
	public String getPenNameEn() {
		return penNameEn;
	}

	@Override
	public void setPenNameEn( String penNameEn ) {
		this.penNameEn = penNameEn;
	}

	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public void setEmail( String email ) {
		this.email = email;
	}

	@Override
	public Language getLanguage() {
		if( language == null ) {
			if( languageId == 5130467284090880L || languageId == 5750790484393984L )
				return Language.HINDI;
			else if( languageId == 5965057007550464L || languageId == 5746055551385600L )
				return Language.GUJARATI;
			else if( languageId == 6319546696728576L || languageId == 5719238044024832L )
				return Language.TAMIL;
			else if( languageId == 5173513199550464L )
				return Language.MARATHI;
			else if( languageId == 5752669171875840L )
				return Language.MALAYALAM;
			else if( languageId == 6235363433512960L )
				return Language.BENGALI;
			else if( languageId == 6213615354904576L || languageId == 5688424874901504L )
				return Language.ENGLISH;
		}
		return language;
	}

	@Override
	public void setLanguage( Language language ) {
		this.language = language;
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
	public Boolean hasCustomImage() {
		if( customImage == null )
			customImage = customCover;
		return customImage == null ? false : customImage;
	}

	@Override
	public void setCustomImage( Boolean customImage ) {
		this.customImage = customImage;
	}
	
	@Override
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Override
	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate;
	}

	@Override
	public Date getLastUpdated() {
		return lastUpdated == null ? registrationDate : lastUpdated;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated;
	}
	
	
	@Override
	public Integer getContentPublished() {
		return contentPublished == null ? 0 : contentPublished;
	}
	
	@Override
	public void setContentPublished( Integer contentPublished ) {
		this.contentPublished = contentPublished;
	}
	
	@Override
	public Long getTotalReadCount() {
		return totalReadCount == null ? 0L : totalReadCount;
	}
	
	@Override
	public void setTotalReadCount( Long totalReadCount ) {
		this.totalReadCount = totalReadCount;
	}
	
	@Override
	public Long getTotalFbLikeShareCount() {
		return totalFbLikeShareCount == null ? 0L : totalFbLikeShareCount;
	}
	
	@Override
	public void setTotalFbLikeShareCount( Long totalFbLikeShareCount ) {
		this.totalFbLikeShareCount = totalFbLikeShareCount;
	}
	
	
	@Override
	public Date getLastProcessDate() {
		return lastProcessDate == null ? registrationDate : lastProcessDate;
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
