package com.pratilipi.common.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class LanguageUtil {
	
	public static Map<String, String> getStrings( String langFileName,
			String fallbackLangFileName ) throws IOException {

		Map<String, String> strMap = new HashMap<>();

		// Loading strings for fallback langauge
		if( fallbackLangFileName != null && !fallbackLangFileName.equals( langFileName ) ) {
			File fallbackLangFile = new File( fallbackLangFileName );
			if( fallbackLangFile.exists() ) {
				LineIterator it = FileUtils.lineIterator( fallbackLangFile, "UTF-8" );
				try {
					while( it.hasNext() ) {
						String line = it.nextLine();
						if( line.indexOf( '=' ) != -1 )
							strMap.put(
									line.substring( 0, line.indexOf( '=' ) ).trim(),
									line.substring( line.indexOf( '=' ) + 1 ).trim() );
					}
				} finally {
					LineIterator.closeQuietly( it );
				}
			}
		}
		
		// Loading strings for the langauge
		File langFile = new File( langFileName );
		if( langFile.exists() ) {
			LineIterator it = FileUtils.lineIterator( langFile, "UTF-8" );
			try {
				while( it.hasNext() ) {
					String line = it.nextLine();
					if( line.indexOf( '=' ) != -1 )
						strMap.put(
								line.substring( 0, line.indexOf( '=' ) ).trim(),
								line.substring( line.indexOf( '=' ) + 1 ).trim() );
				}
			} finally {
				LineIterator.closeQuietly( it );
			}
		}

		// return
		return strMap;
	}

}
