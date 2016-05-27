package com.pratilipi.common.util;

public enum ThirdPartyResource {
	
	JQUERY( "<script defer src='/third-party/jquery-2.1.4/jquery-2.1.4.min.js'></script>" ),
	
	TINYMCE( "<script defer src='/third-party/tinymce/tinymce.min.js'></script>" ),

	ANGULARJS( "<script defer src='/third-party/angular-1.4.8/angular.min.js'></script>"),

	BOOTSTRAP( "<script defer src='/third-party/bootstrap-3.3.4/js/bootstrap.min.js'></script>"
			 + "<link rel='stylesheet' href='/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>" ),

	FONT_AWESOME( "<link rel='stylesheet' href='/third-party/font-awesome-4.3.0/css/font-awesome.min.css'>" ),

	POLYMER( "<script async src='/third-party/polymer-1.0/webcomponentsjs/webcomponents-lite.js'></script>"
		   + "<link rel='import' href='/third-party/polymer-1.0/polymer/polymer.html'>" ),

	POLYMER_IRON_A11Y_KEYS			( "<link rel='import' href='/third-party/polymer-1.0/iron-a11y-keys/iron-a11y-keys.html'>" ),
	POLYMER_IRON_AJAX				( "<link rel='import' href='/third-party/polymer-1.0/iron-ajax/iron-ajax.html'>" ),
	POLYMER_IRON_COLLAPSE			( "<link rel='import' href='/third-party/polymer-1.0/iron-collapse/iron-collapse.html'>" ),
	POLYMER_IRON_FLEX_LAYOUT		( "<link rel='import' href='/third-party/polymer-1.0/iron-flex-layout/iron-flex-layout.html'>" ),
	POLYMER_IRON_ICONS				( "<link rel='import' href='/third-party/polymer-1.0/iron-icons/iron-icons.html'>" ),
	POLYMER_IRON_ICONS_SOCIAL		( "<link rel='import' href='/third-party/polymer-1.0/iron-icons/social-icons.html'>" ),
	POLYMER_IRON_ICONS_AV			( "<link rel='import' href='/third-party/polymer-1.0/iron-icons/av-icons.html'>" ),
	POLYMER_IRON_ICONS_EDITOR		( "<link rel='import' href='/third-party/polymer-1.0/iron-icons/editor-icons.html'>" ),
	POLYMER_IRON_OVERLAY_BEHAVIOR	( "<link rel='import' href='/third-party/polymer-1.0/iron-overlay-behavior/iron-overlay-behavior.html'>" ),
	POLYMER_IRON_RESIZABLE_BEHAVIOR	( "<link rel='import' href='/third-party/polymer-1.0/iron-resizable-behavior/iron-resizable-behavior.html'>" ),
	POLYMER_PAPER_CARD				( "<link rel='import' href='/third-party/polymer-1.0/paper-card/paper-card.html'>" ),
	POLYMER_PAPER_DROPDOWN_MENU		( "<link rel='import' href='/third-party/polymer-1.0/paper-dropdown-menu/paper-dropdown-menu.html'>" ),
	POLYMER_PAPER_FAB				( "<link rel='import' href='/third-party/polymer-1.0/paper-fab/paper-fab.html'>" ),
	POLYMER_PAPER_ICON_BUTTON		( "<link rel='import' href='/third-party/polymer-1.0/paper-icon-button/paper-icon-button.html'>" ),
	POLYMER_PAPER_INPUT				( "<link rel='import' href='/third-party/polymer-1.0/paper-input/paper-input.html'>"
									+ "<link rel='import' href='/third-party/polymer-1.0/paper-input/paper-textarea.html'>" ),
	POLYMER_PAPER_ITEM				( "<link rel='import' href='/third-party/polymer-1.0/paper-item/paper-item.html'>" ),
	POLYMER_PAPER_MENU				( "<link rel='import' href='/third-party/polymer-1.0/paper-menu/paper-menu.html'>" ),
	POLYMER_PAPER_MENU_BUTTON		( "<link rel='import' href='/third-party/polymer-1.0/paper-menu-button/paper-menu-button.html'>" ),
	POLYMER_PAPER_SLIDER			( "<link rel='import' href='/third-party/polymer-1.0/paper-slider/paper-slider.html'>" ),
	POLYMER_PAPER_SPINNER			( "<link rel='import' href='/third-party/polymer-1.0/paper-spinner/paper-spinner.html'>" ),
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
