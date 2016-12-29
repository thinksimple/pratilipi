package com.pratilipi.api.impl.i18n;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.I18nGroup;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.I18n;

@SuppressWarnings( "serial" )
@Bind( uri = "/i18n" )
public class i18nApi extends GenericApi {

	public static class PostRequest extends GenericRequest {

		private I18nGroup group;

		private Language language;

		private String keyValues;

	}

	@Post
	public GenericResponse post( PostRequest request ) 
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException, UnsupportedEncodingException {

		JsonObject jsonObject = new Gson().fromJson( URLDecoder.decode( request.keyValues, "UTF-8" ), JsonObject.class );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		List<I18n> i18nList = dataAccessor.getI18nList( request.group );
		for( I18n i18n : i18nList )
			if( jsonObject.has( i18n.getId() ) )
				i18n.setI18nString( request.language, jsonObject.get( i18n.getId() ).getAsString() );

		System.out.println( new Gson().toJson( i18nList ) );
//		dataAccessor.createOrUpdateI18nList( i18nList );

		return new GenericResponse();

	}

}
