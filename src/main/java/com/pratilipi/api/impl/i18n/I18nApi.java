package com.pratilipi.api.impl.i18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.I18n;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings( "serial" )
@Bind( uri = "/i18n" )
public class I18nApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		private I18nGroup group;

		private Language language;

		private Map<String, String> keyValues;

	}

	@Post
	public GenericResponse post( PostRequest request ) 
			throws InsufficientAccessException, UnexpectedServerException {

		if( ! UserAccessUtil.hasUserAccess( AccessTokenFilter.getAccessToken().getUserId(), request.language, AccessType.I18N_UPDATE ) )
			throw new InsufficientAccessException();

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<I18n> i18nList = new ArrayList<>();

		for( Entry<String, String> entry : request.keyValues.entrySet() ) {
			I18n i18n = dataAccessor.getI18n( entry.getKey() );
			if( i18n == null )
				i18n = dataAccessor.newI18n( entry.getKey() );

			// Resetting the group if its already set
			i18n.setGroup( request.group );
			i18n.setI18nString( request.language, entry.getValue() );

			i18nList.add( i18n );

		}

		i18nList = dataAccessor.createOrUpdateI18nList( i18nList );

		return new GenericResponse();

	}

}
