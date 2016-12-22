package com.pratilipi.common.util;

public enum ThirdPartyResource {

	JQUERY							( "<script src='/third-party/compress/jquery_3_1_1.js'></script>" ),

	JQUERY_BOOTSTRAP				( "<script src='/third-party/compress/jquery_3_1_1.bootstrap_3_3_7.js'></script>" ),

	JQUERY_BOOTSTRAP_POLYMER_JS		( "<script src='/third-party/compress/jquery_3_1_1.bootstrap_3_3_7.webcomponents_lite.js'></script>" ),

	BOOTSTRAP_JS					( "<script defer src='/third-party/bootstrap-3.3.4/js/bootstrap.min.js'></script>" ),

	BOOTSTRAP_CSS					( "<link rel='stylesheet' href='/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>" ),

	CKEDITOR						( "<script src='/third-party/ckeditor-4.5.10-full/ckeditor.js'></script>" ),

	POLYMER_ELEMENTS				( "<link rel='import' href='/third-party/compress/pratilipi.polymer.elements.2.html'>" ),

	FIREBASE						( "<script src='/third-party/compress/firebase_3_6_1.js'></script>" ),

	GOOGLE_TRANSLITERATION			( "<script src='/third-party/compress/jsapi.js'></script>" )

	;
	
	
	private String tag;
	
	private ThirdPartyResource( String tag ) {
		
		if( SystemProperty.CDN != null ) {

			String domain = SystemProperty.CDN.replace( '*', '0' );

			if( tag.indexOf( "src='" ) != -1 )
				tag = tag.replace( "src='", "src='" + domain );
			if( tag.indexOf( "href='" ) != -1 )
				tag = tag.replace( "href='", "href='" + domain );

		}

		this.tag = tag;

	}
	
	public String getTag() {
		return this.tag;
	}
	
}
