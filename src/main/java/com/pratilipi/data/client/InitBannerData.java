package com.pratilipi.data.client;

public class InitBannerData {

	private String bannerId;

	private String title;
	
	private String imageUrl;
	
	private String actionUrl;
	
	
	public InitBannerData( String bannerId ) {
		this.bannerId = bannerId;
	}


	public String getId() {
		return bannerId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl( String imageUrl ) {
		this.imageUrl = imageUrl;
	}
	
	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl( String actionUrl ) {
		this.actionUrl = actionUrl;
	}

}