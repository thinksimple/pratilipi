package com.pratilipi.data.type.gae;

import com.pratilipi.data.type.InitBanner;

@SuppressWarnings("serial")
public class InitBannerEntity implements InitBanner {

	private String BANNER_ID;
	
	private String TITLE;
	
	private String ACTION_URL;
	
	
	public InitBannerEntity() {}
	
	public InitBannerEntity( String bannerId ) {
		this.BANNER_ID = bannerId;
	}

	
	public String getId() {
		return BANNER_ID;
	}
	
	public String getTitle() {
		return TITLE;
	}
	
	public String getActionUrl() {
		return ACTION_URL;
	}
	
}
