package com.pratilipi.common.util;

public enum ThirdPartyResource {
	
	JQUERY( "<script src='/third-party/jquery-2.1.4/jquery-2.1.4.min.js'></script>" ),

	BOOTSTRAP( "<script src='/third-party/bootstrap-3.3.4/js/bootstrap.min.js'></script>"
			 + "<link rel='stylesheet' href='/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>" ),

	FONT_AWESOME( "<link rel='stylesheet' href='/third-party/font-awesome-4.3.0/css/font-awesome.min.css'>" ),

	POLYMER( "<script src='/third-party/polymer-1.0/webcomponentsjs/webcomponents-lite.js'></script>"
		   + "<link rel='import' href='/third-party/polymer-1.0/polymer/polymer.html'>" ),

	POLYMER_IRON_AJAX				( "<link rel='import' href='/third-party/polymer-1.0/iron-ajax/iron-ajax.html'>" ),
	POLYMER_IRON_COLLAPSE			( "<link rel='import' href='/third-party/polymer-1.0/iron-collapse/iron-collapse.html'>" ),
	POLYMER_IRON_OVERLAY_BEHAVIOR	( "<link rel='import' href='/third-party/polymer-1.0/iron-overlay-behavior/iron-overlay-behavior.html'>" ),
	POLYMER_IRON_RESIZABLE_BEHAVIOR	( "<link rel='import' href='/third-party/polymer-1.0/iron-resizable-behavior/iron-resizable-behavior.html'>" ),
	;
	
	
	private String tag;
	
	private ThirdPartyResource( String tag ) {
		
		if( SystemProperty.CDN != null ) {

			int indexSrc = tag.indexOf( "src='" );
			if( indexSrc != -1 ) {
				String domain = SystemProperty.CDN.replace( '*', tag.charAt( indexSrc + 18 ) );
				tag = tag.replace( "src='", "src='" + domain );
			}
			
			int indexHref = tag.indexOf( "href='" );
			if( indexHref != -1 ) {
				String domain = SystemProperty.CDN.replace( '*', tag.charAt( indexHref + 19 ) );
				tag = tag.replace( "href='", "href='" + domain );
			}
			
		}
		
		this.tag = tag;
		
	}
	
	public String getTag() {
		return this.tag;
	}
	
}
