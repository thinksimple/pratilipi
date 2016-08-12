package com.pratilipi.common.util;

import org.jsoup.Jsoup;

public class HtmlUtil {
	
	public static String toPlainText( String html ) {
		return html == null ? null : Jsoup.parse( html.replaceAll( "(?i)<br[^>]*>|\\n", "LINE_BREAK" ) )
				.text()
				.replaceAll( "LINE_BREAK", "\n" )
				.trim();
	}

}
