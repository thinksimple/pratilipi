package com.pratilipi.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.pratilipi.common.exception.UnexpectedServerException;


public class SiteElementUtil {

	public static final String defaultLang = "en";

	
	public static void main( String args[] ) throws IOException,
			UnexpectedServerException, URISyntaxException,
			ClassNotFoundException {

		String langCodes[] = args[0].split( "," );
		String languageFilePrefix = args[1];
		String elementFolderName = args[2];
		String elementDataFolder = args[3];
		String elementDataModelPackage = args[4];
		String i18nElementFolderNamePrefix = args[5];
		
		
		for( String lang : langCodes ) {

			File elementFolder = new File( elementFolderName );
			for( String elementName : elementFolder.list() ) {
				File element = new File( elementFolder, elementName );
				if( element.isDirectory() )
					continue;

				File i18nElementFolder = new File( i18nElementFolderNamePrefix + lang );
				i18nElementFolder.mkdir();
				File i18nElement = new File( i18nElementFolder, elementName );
				i18nElement.createNewFile();
				FileWriter i18nElementFW = new FileWriter( i18nElement );
				
				Map<String, Object> dataModel = new HashMap<>();
				dataModel.put( "_strings", getStrMap( languageFilePrefix + lang, languageFilePrefix + defaultLang ) );
				dataModel.put( "data", getElementData( elementName, lang, elementDataFolder, elementDataModelPackage ) );
				
				FreeMarkerUtil.processTemplate(
						dataModel,
						elementFolderName + "/" + elementName,
						i18nElementFW );

				i18nElementFW.close();
			}
			
		}
	}

	
	private static Map<String, String> getStrMap( String langFileName,
			String fallbackLangFileName ) throws IOException {

		Properties strings = new Properties();
		
		// Loading strings for fallback langauge
		if( fallbackLangFileName != null && !fallbackLangFileName.equals( langFileName ) ) {
			File fallbackLangFile = new File( fallbackLangFileName );
			if( fallbackLangFile.exists() ) {
				FileInputStream in = new FileInputStream( fallbackLangFile );
				strings.load( in );
				in.close();
			}
		}
		
		// Loading strings for the langauge
		File langFile = new File( langFileName );
		if( langFile.exists() ) {
			strings = new Properties( strings );
			FileInputStream in = new FileInputStream( langFile );
			strings.load( in );
			in.close();
		}
		
		// Putting strings in a Map
		Map<String, String> strMap = new HashMap<>();
		for( Entry<Object, Object> entry : strings.entrySet() )
			strMap.put( (String)entry.getKey(), (String)entry.getValue() );

		// return
		return strMap;
	}
	
	private static Object getElementData( String elementName, String lang,
			String elementDataFolder, String elementDataModelPackage )
			throws IOException, ClassNotFoundException {

		File file = new File( elementDataFolder + "/" + elementName.replaceFirst( "\\.html$", "." + lang + ".json" ) );
		if( !file.exists() )
			return null;

		// Fetching content (json) from data file
		String jsonStr = "";
		LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
		try {
			while( it.hasNext() )
				jsonStr += it.nextLine() + '\n';
		} finally {
			LineIterator.closeQuietly( it );
		}

		// Creating class object of data model class
		String className = "";
		for( String str : elementName.split( "-" ) )
			className += Character.toUpperCase( str.charAt( 0 ) ) + str.substring( 1 );
		className = className.substring( 0, className.indexOf( '.' ) );
		Class<?> clazz = Class.forName( elementDataModelPackage + "." + className );

		// The magic
		return new Gson().fromJson( jsonStr, clazz );
	}
	
}
