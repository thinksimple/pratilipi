package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Gender;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.Author;

@Cache
@Entity( name = "AUTHOR" )
public class AuthorEntity implements Author {
	
	@Id
	private Long AUTHOR_ID;

	@Index
	private Long USER_ID;

	
	private String FIRST_NAME;
	
	private String LAST_NAME;

	private String PEN_NAME;
	
	private String FIRST_NAME_EN;
	
	private String LAST_NAME_EN;

	private String PEN_NAME_EN;


	@Index( IfNotNull.class )
	private Gender GENDER;
	
	@Index( IfNotNull.class )
	private String DATE_OF_BIRTH;
	
	
	@Deprecated
	@Index( IfNotNull.class )
	private String EMAIL;

	@Index
	private Language LANGUAGE;

	@Deprecated
	private Long LANGUAGE_ID;

	private String SUMMARY;
	

	@Index
	private AuthorState STATE;
	
	@Deprecated
	private Boolean CUSTOM_COVER;

	@Index
	private Boolean CUSTOM_IMAGE;

	@Index
	private Date REGISTRATION_DATE;

	@Index
	private Date LAST_UPDATED;

	
	@Index
	private Long FOLLOW_COUNT;

	@Index
	private Integer CONTENT_PUBLISHED;
	
	@Index
	private Long TOTAL_READ_COUNT;

	@Index
	private Long TOTAL_FB_LIKE_SHARE_COUNT;

	
	public AuthorEntity() {}
	
	public AuthorEntity( Long id ) {
		this.AUTHOR_ID = id;
	}

	
	@Override
	public Long getId() {
		return AUTHOR_ID;
	}

	public void setId( Long id ) {
		this.AUTHOR_ID = id;
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.AUTHOR_ID = key.getId();
	}

	
	@Override
	public Long getUserId() {
		return USER_ID;
	}

	@Override
	public void setUserId( Long userId ) {
		this.USER_ID = userId;
	}

	@Override
	public String getFirstName() {
		return FIRST_NAME;
	}

	@Override
	public void setFirstName( String firstName ) {
		this.FIRST_NAME = firstName;
	}

	@Override
	public String getLastName() {
		return LAST_NAME;
	}

	@Override
	public void setLastName( String lastName ) {
		this.LAST_NAME = lastName;
	}

	@Override
	public String getPenName() {
		return PEN_NAME;
	}

	@Override
	public void setPenName( String penName ) {
		this.PEN_NAME = penName;
	}

	@Override
	public String getFirstNameEn() {
		return FIRST_NAME_EN;
	}

	@Override
	public void setFirstNameEn( String firstNameEn ) {
		this.FIRST_NAME_EN = firstNameEn;
	}

	@Override
	public String getLastNameEn() {
		return LAST_NAME_EN;
	}

	@Override
	public void setLastNameEn( String lastNameEn ) {
		this.LAST_NAME_EN = lastNameEn;
	}

	@Override
	public String getPenNameEn() {
		return PEN_NAME_EN;
	}

	@Override
	public void setPenNameEn( String penNameEn ) {
		this.PEN_NAME_EN = penNameEn;
	}

	
	@Override
	public Gender getGender() {
		return GENDER ;
	}
	
	@Override
	public void setGender( Gender gender ) {
		this.GENDER = gender;
	}
	
	@Override
	public String getDateOfBirth() {
		return DATE_OF_BIRTH;
	}
	
	@Override
	public void setDateOfBirth( String dateOfBirth ) {
		this.DATE_OF_BIRTH = dateOfBirth;
	}

	
	@Override
	public Language getLanguage() {
		if( LANGUAGE == null ) {
			if( LANGUAGE_ID == null )
				LANGUAGE = null;
			else if( LANGUAGE_ID == 5130467284090880L || LANGUAGE_ID == 5750790484393984L )
				LANGUAGE = Language.HINDI;
			else if( LANGUAGE_ID == 5965057007550464L || LANGUAGE_ID == 5746055551385600L )
				LANGUAGE = Language.GUJARATI;
			else if( LANGUAGE_ID == 6319546696728576L || LANGUAGE_ID == 5719238044024832L )
				LANGUAGE = Language.TAMIL;
			else if( LANGUAGE_ID == 5173513199550464L )
				LANGUAGE = Language.MARATHI;
			else if( LANGUAGE_ID == 5752669171875840L )
				LANGUAGE = Language.MALAYALAM;
			else if( LANGUAGE_ID == 6235363433512960L )
				LANGUAGE = Language.BENGALI;
			else if( LANGUAGE_ID == 6213615354904576L || LANGUAGE_ID == 5688424874901504L )
				LANGUAGE = Language.ENGLISH;
		}
		LANGUAGE_ID = null;
		return LANGUAGE;
	}

	@Override
	public void setLanguage( Language language ) {
		this.LANGUAGE = language;
	}

	@Override
	public String getSummary() {
		return SUMMARY;
	}

	@Override
	public void setSummary( String summary ) {
		this.SUMMARY = summary;
	}

	
	@Override
	public AuthorState getState() {
		if( STATE == null )
			STATE = AuthorState.ACTIVE;
		return STATE;
	}
	
	@Override
	public void setState( AuthorState state ) {
		this.STATE = state;
	}
	
	@Override
	public Boolean hasCustomImage() {
		if( CUSTOM_IMAGE == null ) {
			CUSTOM_IMAGE = CUSTOM_COVER;
			CUSTOM_COVER = null;
		}
		return CUSTOM_IMAGE == null ? false : CUSTOM_IMAGE;
	}

	@Override
	public void setCustomImage( Boolean customImage ) {
		this.CUSTOM_IMAGE = customImage;
	}
	
	@Override
	public Date getRegistrationDate() {
		return REGISTRATION_DATE;
	}

	@Override
	public void setRegistrationDate( Date registrationDate ) {
		this.REGISTRATION_DATE = registrationDate;
	}

	@Override
	public Date getLastUpdated() {
		return LAST_UPDATED;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}
	
	
	@Override
	public Long getFollowCount() {
		return FOLLOW_COUNT == null ? 0 : FOLLOW_COUNT;
	}
	
	@Override
	public void setFollowCount( Long followCount ) {
		this.FOLLOW_COUNT = followCount;
	}
	
	@Override
	public Integer getContentPublished() {
		return CONTENT_PUBLISHED == null ? 0 : CONTENT_PUBLISHED;
	}
	
	@Override
	public void setContentPublished( Integer contentPublished ) {
		this.CONTENT_PUBLISHED = contentPublished;
	}
	
	@Override
	public Long getTotalReadCount() {
		return TOTAL_READ_COUNT == null ? 0L : TOTAL_READ_COUNT;
	}
	
	@Override
	public void setTotalReadCount( Long totalReadCount ) {
		this.TOTAL_READ_COUNT = totalReadCount;
	}
	
	@Override
	public Long getTotalFbLikeShareCount() {
		return TOTAL_FB_LIKE_SHARE_COUNT == null ? 0L : TOTAL_FB_LIKE_SHARE_COUNT;
	}
	
	@Override
	public void setTotalFbLikeShareCount( Long totalFbLikeShareCount ) {
		this.TOTAL_FB_LIKE_SHARE_COUNT = totalFbLikeShareCount;
	}

}
