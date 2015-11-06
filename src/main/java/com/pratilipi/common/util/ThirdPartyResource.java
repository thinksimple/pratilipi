package com.pratilipi.common.util;

public enum ThirdPartyResource {
	
	JQUERY( "<script src='//jquery.__DOMAIN__/jquery-2.1.4/jquery-2.1.4.min.js'></script>" ),

	BOOTSTRAP( "<script src='//bootstrap.__DOMAIN__/bootstrap-3.3.4/js/bootstrap.min.js'></script>"
			 + "<link rel='stylesheet' href='//bootstrap.__DOMAIN__/bootstrap-3.3.4/css/bootstrap.min.css'>" ),

	FONT_AWESOME( "<link rel='stylesheet' href='//fontawesome.__DOMAIN__/font-awesome-4.3.0/css/font-awesome.min.css'>" ),

	POLYMER( "<script src='//polymer.__DOMAIN__/polymer-1.0/webcomponentsjs/webcomponents-lite.js'></script>"
		   + "<link rel='import' href='//polymer.__DOMAIN__/polymer-1.0/polymer/polymer.html'>" ),

	POLYMER_IRON_AJAX( "<link rel='import' href='//polymer.__DOMAIN__/polymer-1.0/iron-ajax/iron-ajax.html'>" ),
	POLYMER_IRON_COLLAPSE( "<link rel='import' href='//polymer.__DOMAIN__/polymer-1.0/iron-collapse/iron-collapse.html'>" ),
	POLYMER_IRON_OVERLAY_BEHAVIOR( "<link rel='import' href='//polymer.__DOMAIN__/polymer-1.0/iron-overlay-behavior/iron-overlay-behavior.html'>" ),
	POLYMER_IRON_RESIZABLE_BEHAVIOR( "<link rel='import' href='//polymer.__DOMAIN__/polymer-1.0/iron-resizable-behavior/iron-resizable-behavior.html'>" ),
	;
	
	
	private String tag;
	
	
	private ThirdPartyResource( String tag ) {
		this.tag = tag.replace( "__DOMAIN__", SystemProperty.get( "cdn" ) + "/third-party" );
	}
	
	public String getTag() {
		return this.tag;
	}
	
}
