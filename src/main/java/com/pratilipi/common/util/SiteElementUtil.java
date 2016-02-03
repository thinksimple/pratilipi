package com.pratilipi.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.gson.Gson;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.i18n.I18n;

public class SiteElementUtil {

	public static final String defaultLang = "en";

	
	public static void main( String args[] ) throws IOException,
			UnexpectedServerException, URISyntaxException,
			ClassNotFoundException {

		String elementFolderName = args[0];
		String elementDataFolder = args[1];
		String elementDataModelPackage = args[2];
		String i18nElementFolderNamePrefix = args[3];
		
		
		for( Language language : Language.values() ) {

			File elementFolder = new File( elementFolderName );
			for( String elementName : elementFolder.list() ) {
				if( ! elementName.endsWith( ".html" ) )
					continue;
				
				File element = new File( elementFolder, elementName );
				if( element.isDirectory() )
					continue;

				// Data model required for i18n element generation
				Map<String, Object> dataModel = new HashMap<>();
				dataModel.put( "language", language );
				dataModel.put( "lang", language.getCode() );
				dataModel.put( "domain", language.getHostName() );
				dataModel.put( "fbAppId", "293990794105516" );
				dataModel.put( "_strings", I18n.getStrings( language ) );
				dataModel.put( "data", getElementData( elementName, language.getCode(), elementDataFolder, elementDataModelPackage ) );

				// I18n element file output stream
				File i18nElementFolder = new File( i18nElementFolderNamePrefix + language.getCode() );
				i18nElementFolder.mkdir();
				File i18nElement = new File( i18nElementFolder, elementName );
				i18nElement.createNewFile();
				OutputStreamWriter i18nElementOS = new OutputStreamWriter( new FileOutputStream( i18nElement ), "UTF-8" );
				
				// The magic
				FreeMarkerUtil.processTemplate(
						dataModel,
						elementFolderName + "/" + elementName,
						i18nElementOS );

				// closing the output stream
				i18nElementOS.close();
			}
			
		}
	}
	
	private static Object getElementData( String elementName, String lang,
			String elementDataFolder, String elementDataModelPackage )
			throws IOException, ClassNotFoundException {

		File file = new File( elementDataFolder + "/" + elementName.replaceFirst( "\\.html$", "." + lang + ".json" ) );
		if( ! file.exists() )
			file = new File( elementDataFolder + "/" + elementName.replaceFirst( "\\.html$", "." + Language.ENGLISH.getCode() + ".json" ) );
		if( ! file.exists() )
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
