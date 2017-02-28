package com.pratilipi.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.i18n.I18n;

public class SiteElementUtil {

	public static final String defaultLang = "en";

	private static List<Map<String, Object>> getNavigationList( Language language ) {

		List<String> lines = null;
		try {
			String fileName = "curated/navigation." + language.getCode();
			InputStream inputStream = DataAccessor.class.getResource( fileName ).openStream();
			lines = IOUtils.readLines( inputStream, "UTF-8" );
			inputStream.close();
		} catch( NullPointerException | IOException e ) {
			System.out.println( "Failed to fetch " + language.getNameEn() + " navigation list." );
			lines = new ArrayList<>( 0 );
		}

		List<Map<String, Object>> navigationList = new ArrayList<>();

		String navigationListTitle = null;
		List<Map<String, String>> linkList = new ArrayList<>();

		for( String line : lines ) {
			line = line.trim();
			if( line.isEmpty() )
				continue;

			if( line.contains( "App#" ) ) {
				Map<String, String> navigationEntry = new HashMap<>();
				navigationEntry.put( "url", line.substring( 0, line.indexOf( ' ' ) ).trim() );
				line = line.substring( line.indexOf( ' ' ) ).trim();
				navigationEntry.put( "name", line.substring( 0, line.indexOf( "Analytics#" ) ).trim() );
				linkList.add( navigationEntry );
			}
			else {
				if( navigationListTitle != null ) {
					Map<String, Object> navigation = new LinkedHashMap<>();
					navigation.put( "title", navigationListTitle );
					navigation.put( "linkList", linkList );
					navigationList.add( navigation );
					linkList = new ArrayList<>(); 
				}
				navigationListTitle = line;
			}
		}
		Map<String, Object> navigation = new LinkedHashMap<>();
		navigation.put( "title", navigationListTitle );
		navigation.put( "linkList", linkList );
		navigationList.add( navigation );

		return navigationList;

	}

	public static void main( String args[] ) throws IOException,
			UnexpectedServerException, URISyntaxException,
			ClassNotFoundException {

		String elementFolderName = args[0];
		String i18nElementFolderNamePrefix = args[1];
		String framework = args[2];

		for( Language language : Language.values() ) {

			File elementFolder = new File( elementFolderName );
			for( String elementName : elementFolder.list() ) {
				if( ! elementName.endsWith( ".html" ) && ! elementName.endsWith( ".js" ) )
					continue;

				File element = new File( elementFolder, elementName );
				if( element.isDirectory() )
					continue;

				// Data model required for i18n element generation
				Map<String, Object> dataModel = new HashMap<>();
				dataModel.put( "lang", language.getCode() );
				dataModel.put( "language", language );
				dataModel.put( "domain", language.getHostName() );
				dataModel.put( "fbAppId", "293990794105516" );
				dataModel.put( "googleClientId", "659873510744-kfim969enh181h4gbctffrjg5j47tfuq.apps.googleusercontent.com" );
				dataModel.put( "firebaseLibrary", "http://0.ptlp.co/resource-all/pwa/js/firebase_app_auth_database.js" );
				dataModel.put( "_strings", I18n.getStrings( language ) );

				List<Map<String, Object>> pratilipiTypes = new ArrayList<>();
				for( PratilipiType pratilipiType : PratilipiType.values() ) {
					Map<String, Object> type = new HashMap<>();
					type.put( "name", I18n.getString( pratilipiType.getStringId(), language ) );
					type.put( "namePlural", I18n.getString( pratilipiType.getPluralStringId(), language ) );
					type.put( "value", pratilipiType.name() );
					pratilipiTypes.add( type );
				}
				dataModel.put( "pratilipiTypes", pratilipiTypes );

				// Language lisr
				List<Map<String, Object>> languageList = new ArrayList<>();
				for( Language lang : Language.values() ) {
					if( lang == Language.ENGLISH ) continue;
					Map<String, Object> langMap = new HashMap<>();
					langMap.put( "value", lang );
					langMap.put( "code", lang.getCode() );
					langMap.put( "hostName", "http://" + lang.getHostName() );
					langMap.put( "name", lang.getName() );
					langMap.put( "nameEn", lang.getNameEn() );
					languageList.add(langMap);
				}
				dataModel.put( "languageList", languageList );

				dataModel.put( "navigationList", getNavigationList( language ) );
				// I18n element file output stream
				File i18nElement = null;
				if( framework.equals( "polymer" ) ) {
					File i18nElementFolder = new File( i18nElementFolderNamePrefix + language.getCode() );
					i18nElementFolder.mkdir();
					i18nElement = new File( i18nElementFolder, elementName );
					i18nElement.createNewFile();

				} else if( framework.equals( "knockout" ) ) {
					String elName = null;
					if( elementName.endsWith( ".html" ) ) {
						elName = elementName.substring( 0, elementName.indexOf( ".html" ) ) + "-" + language.getCode() + ".html";
					} else if( elementName.endsWith( ".js" ) ) {
						elName = elementName.substring( 0, elementName.indexOf( ".js" ) ) + "-" + language.getCode() + ".js";
					}
					
					i18nElement = new File( i18nElementFolderNamePrefix, elName );
					i18nElement.createNewFile();
				}

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
	
}
