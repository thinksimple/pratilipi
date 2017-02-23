package com.pratilipi.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.i18n.I18n;

public class SiteElementUtil {

	public static final String defaultLang = "en";

	
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
				dataModel.put( "_strings", I18n.getStrings( language ) );

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
