package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Publisher;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PUBLISHER" )
public class PublisherEntity implements Publisher {

	@PrimaryKey
	@Persistent( column = "PUBLISHER_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "NAME" )
	private String name;
	

	@Persistent( column = "EMAIL" )
	private String email;

	@Persistent( column = "REGISTRATION_DATE" )
	private Date registrationDate;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
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
	
}
