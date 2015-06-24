package com.pratilipi.common.util;

public enum ThirdPartyResource {
	
	JQUERY( "<script src='//jquery.__DOMAIN__/jquery-2.1.4/jquery-2.1.4.min.js'></script>" ),

	BOOTSTRAP( "<script src='//bootstrap.__DOMAIN__/bootstrap-3.3.4/js/bootstrap.min.js'></script>"
			 + "<link rel='stylesheet' href='//bootstrap.__DOMAIN__/bootstrap-3.3.4/css/bootstrap.min.css'>" ),

	POLYMER( "<script src='//polymer.__DOMAIN__/polymer-1.0/webcomponentsjs/webcomponents-lite.js'></script>"
		   + "<link rel='import' href='//polymer.__DOMAIN__/polymer-1.0/polymer/polymer.html'>" ),

	;
	
	
	private String tag;
	
	
	private ThirdPartyResource( String tag ) {
		this.tag = tag.replace( "__DOMAIN__", SystemProperty.get( "cdn.3p" ) );
	}
	
	public String getTag() {
		return this.tag;
	}
	
}
