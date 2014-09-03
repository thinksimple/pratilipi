package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GenreData implements IsSerializable {

	private Long id;
	
	private String name;
	
	private Date creationDate;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}
	
}
