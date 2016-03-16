package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Gender;
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


	@Persistent( column = "GENDER" )
	private Gender gender;
	
	@Persistent( column = "DATE_OF_BIRTH" )
	private String dateOfBirth;
	
	
	@Deprecated
	@Persistent( column = "EMAIL" )
	private String email;

	@Persistent( column = "LANGUAGE" )
	private Language language;

	@Deprecated
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	@Persistent( column = "SUMMARY" )
	private Text summary;
	

	@Persistent( column = "STATE" )
	private AuthorState state;
	
	@Deprecated
	@Persistent( column = "CUSTOM_COVER" )
	private Boolean customCover;

	@Persistent( column = "CUSTOM_IMAGE" )
	private Boolean customImage;

	@Persistent( column = "REGISTRATION_DATE" )
	private Date registrationDate;

	@Persistent( column = "LAST_UPDATED" )
	private Date lastUpdated;

	
	@Persistent( column = "FOLLOW_COUNT" )
	private Long followCount;

	@Persistent( column = "CONTENT_PUBLISHED" )
	private Integer contentPublished;
	
	@Persistent( column = "TOTAL_READ_COUNT" )
	private Long totalReadCount;

	@Persistent( column = "TOTAL_FB_LIKE_SHARE_COUNT" )
	private Long totalFbLikeShareCount;

	
	@Deprecated
	@Persistent( column = "LAST_PROCESS_DATE" )
	private Date lastProcessDate;

	@Deprecated
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

	public void setId( Long id ) {
		this.id = id;
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
	public Gender getGender() {
		return gender ;
	}
	
	@Override
	public void setGender( Gender gender ) {
		this.gender = gender;
	}
	
	@Override
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	@Override
	public void setDateOfBirth( String dateOfBirth ) {
		this.dateOfBirth = dateOfBirth;
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
			if( languageId == null )
				language = null;
			else if( languageId == 5130467284090880L || languageId == 5750790484393984L )
				language = Language.HINDI;
			else if( languageId == 5965057007550464L || languageId == 5746055551385600L )
				language = Language.GUJARATI;
			else if( languageId == 6319546696728576L || languageId == 5719238044024832L )
				language = Language.TAMIL;
			else if( languageId == 5173513199550464L )
				language = Language.MARATHI;
			else if( languageId == 5752669171875840L )
				language = Language.MALAYALAM;
			else if( languageId == 6235363433512960L )
				language = Language.BENGALI;
			else if( languageId == 6213615354904576L || languageId == 5688424874901504L )
				language = Language.ENGLISH;
		}
		return language;
	}

	@Override
	public void setLanguage( Language language ) {
		if( language == null )
			languageId = null;
		else
			switch( language ) {
				case HINDI:
					languageId = 5130467284090880L;
					break;
				case GUJARATI:
					languageId = 5965057007550464L;
					break;
				case TAMIL:
					languageId = 6319546696728576L;
					break;
				case MARATHI:
					languageId = 5173513199550464L;
					break;
				case MALAYALAM:
					languageId = 5752669171875840L;
					break;
				case BENGALI:
					languageId = 6235363433512960L;
					break;
				case ENGLISH:
					languageId = 6213615354904576L;
					break;
			}
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
	public AuthorState getState() {
		if( state == null )
			state = AuthorState.ACTIVE;
		return state;
	}
	
	@Override
	public void setState( AuthorState state ) {
		this.state = state;
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
		return lastUpdated;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated;
	}
	
	
	@Override
	public Long getFollowCount() {
		return followCount == null ? 0 : followCount;
	}
	
	@Override
	public void setFollowCount( Long followCount ) {
		this.followCount = followCount;
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
	
}
