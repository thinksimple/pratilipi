package com.pratilipi.service.shared.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserQueryData implements IsSerializable {

	private String email;
	
	private String name;
	
	private String mailBody;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	
	
	
}
