package com.pratilipi.service.shared.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LanguageData implements IsSerializable {

	private Long id;
	
	private String name;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getNamme() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
}
