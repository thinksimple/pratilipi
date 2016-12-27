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

	private static final Map<String, I18n> i18ns;

	static {
		List<I18n> i18nList = DataAccessorFactory.getDataAccessor().getI18nList( I18nGroup.EMAIL );
		i18ns = new HashMap<>( i18nList.size() );
		for( I18n i18n : i18nList )
			i18ns.put( i18n.getId(), i18n );
	}


	public static String getEmailTemplate( EmailType emailType, Language language ) 
			throws UnexpectedServerException {

		return FreeMarkerUtil.processTemplate( i18ns, filePath + emailType.getTemplateName() );

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
