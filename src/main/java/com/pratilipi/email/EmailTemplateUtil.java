package com.pratilipi.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.EmailType;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.I18n;


public class EmailTemplateUtil {

	private final static String filePath = 
			EmailUtil.class.getName().substring( 0, EmailTemplateUtil.class.getName().lastIndexOf(".") ).replace( ".", "/" ) + "/template/";

	private static final Map<Language, Map<String, String>> i18nMap;

	static {
		List<I18n> i18nList = DataAccessorFactory.getDataAccessor().getI18nList( I18nGroup.EMAIL );
		i18nMap = new HashMap<>( i18nList.size() );
		// TODO: Implementation
		// Map<Language, Map<key,value>> -> Map<TAMIL, Map<"user_followed_you", "${user} has followed you!">>
	}


	public static String getEmailTemplate( EmailType emailType, Language language ) 
			throws UnexpectedServerException {

		return FreeMarkerUtil.processTemplate( i18nMap.get( language ), filePath + emailType.getTemplateName() );

	}

	public static String getCompleteEmail( String body, Language language ) 
			throws UnexpectedServerException {

		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "language", language );
		dataModel.put( "contact_email", language == null || language == Language.ENGLISH ? 
				"contact@pratilipi.com" : language.name().toLowerCase() + "@pratilipi.com" );
		dataModel.put( "emailBody", body );

		return FreeMarkerUtil.processTemplate( dataModel, filePath + "MainEmailTemplate.ftl" );

	}

}
