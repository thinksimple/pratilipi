package com.pratilipi.data.type.doc;

import com.pratilipi.data.type.InitBannerDoc;

public class InitBannerDocImpl implements InitBannerDoc {

	private String id;
	
	private String title;
	
	private String actionUrl;
	
	
	public InitBannerDocImpl() {}
	
	public InitBannerDocImpl( String bannerId, String title, String actionUrl ) {
		this.id = bannerId;
		this.title = title;
		this.actionUrl = actionUrl;
	}

	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getActionUrl() {
		return actionUrl;
	}
	
}
