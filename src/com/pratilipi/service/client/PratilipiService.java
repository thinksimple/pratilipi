package com.pratilipi.service.client;

import com.claymus.commons.client.IllegalArgumentException;
import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.client.UnexpectedServerException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.AddGenreRequest;
import com.pratilipi.service.shared.AddGenreResponse;
import com.pratilipi.service.shared.AddLanguageRequest;
import com.pratilipi.service.shared.AddLanguageResponse;
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.GetPublisherListRequest;
import com.pratilipi.service.shared.GetPublisherListResponse;
import com.pratilipi.service.shared.GetUserPratilipiListRequest;
import com.pratilipi.service.shared.GetUserPratilipiListResponse;
import com.pratilipi.service.shared.GetUserPratilipiRequest;
import com.pratilipi.service.shared.GetUserPratilipiResponse;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.SavePratilipiContentRequest;
import com.pratilipi.service.shared.SavePratilipiContentResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.UserQueryRequest;
import com.pratilipi.service.shared.UserQueryResponse;

@RemoteServiceRelativePath("../service.pratilipi")
public interface PratilipiService extends RemoteService {
	
	SavePratilipiResponse savePratilipi(
			SavePratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetPratilipiListResponse getPratilipiList(
			GetPratilipiListRequest request );
	

	SavePratilipiContentResponse savePratilipiContent(
			SavePratilipiContentRequest request )
			throws IllegalArgumentException, InsufficientAccessException,
					UnexpectedServerException;


	AddLanguageResponse addLanguage( AddLanguageRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetLanguageListResponse getLanguageList( GetLanguageListRequest request )
			throws InsufficientAccessException;


	AddAuthorResponse addAuthor( AddAuthorRequest request )
			throws IllegalArgumentException, InsufficientAccessException;
	
	SaveAuthorResponse saveAuthor( SaveAuthorRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
			throws InsufficientAccessException;
	
	
	AddPublisherResponse addPublisher( AddPublisherRequest request )
			throws InsufficientAccessException;

	GetPublisherListResponse getPublisherList( GetPublisherListRequest request );
	

	AddGenreResponse addGenre( AddGenreRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	
	AddUserPratilipiResponse addUserPratilipi( AddUserPratilipiRequest request )
			throws IllegalArgumentException, InsufficientAccessException;

	GetUserPratilipiResponse getUserPratilipi( GetUserPratilipiRequest request );
	
	GetUserPratilipiListResponse getUserPratilipiList( GetUserPratilipiListRequest request );
	
	UserQueryResponse userQueryMail( UserQueryRequest request );

}
