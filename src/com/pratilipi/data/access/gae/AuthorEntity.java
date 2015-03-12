package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;
import com.pratilipi.data.transfer.Author;

@SuppressWarnings("serial")
@PersistenceCapable( table = "AUTHOR" )
public class AuthorEntity implements Author {
	
	@PrimaryKey
	@Persistent( column = "AUTHOR_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;

	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

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
	
	@Persistent( column = "SUMMARY" )
	private Text summary;
	
	@Persistent( column = "EMAIL" )
	private String email;

	@Persistent( column = "REGISTRATION_DATE" )
	private Date registrationDate;

	@Persistent( column = "CONTENT_PUBLISHED" )
	private Long contentPublished;

	@Persistent( column = "TOTAL_READ_COUNT" )
	private Long totalReadCount;
	
	@Persistent( column = "LAST_PROCESS_DATE" )
	private Date lastProcessDate;

	@Persistent( column = "NEXT_PROCESS_DATE" )
	private Date nextProcessDate;
	
	
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
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
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
	public String getSummary() {
		return summary == null ? null : summary.getValue();
	}

	@Override
	public void setSummary( String summary ) {
		this.summary = summary == null ? null : new Text( summary );
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
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Override
	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate;
	}

	@Override
	public Long getContentPublished() {
		return contentPublished == null ? 0L : contentPublished;
	}
	
	@Override
	public void setContentPublished( Long contentPublished ) {
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
