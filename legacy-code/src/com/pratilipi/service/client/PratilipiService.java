package com.pratilipi.service.client;

import java.io.IOException;

import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pratilipi.service.shared.AddPratilipiGenreRequest;
import com.pratilipi.service.shared.AddPratilipiGenreResponse;
import com.pratilipi.service.shared.AddUserPratilipiRequest;
import com.pratilipi.service.shared.AddUserPratilipiResponse;
import com.pratilipi.service.shared.ConvertPratilipiWordToHtmlRequest;
import com.pratilipi.service.shared.DeletePratilipiGenreRequest;
import com.pratilipi.service.shared.DeletePratilipiGenreResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetGenreListRequest;
import com.pratilipi.service.shared.GetGenreListResponse;
import com.pratilipi.service.shared.GetLanguageListRequest;
import com.pratilipi.service.shared.GetLanguageListResponse;
import com.pratilipi.service.shared.GetPratilipiListRequest;
import com.pratilipi.service.shared.GetPratilipiListResponse;
import com.pratilipi.service.shared.GetUserPratilipiListRequest;
import com.pratilipi.service.shared.GetUserPratilipiListResponse;
import com.pratilipi.service.shared.GetUserPratilipiRequest;
import com.pratilipi.service.shared.GetUserPratilipiResponse;
import com.pratilipi.service.shared.SaveAuthorRequest;
import com.pratilipi.service.shared.SaveAuthorResponse;
import com.pratilipi.service.shared.SaveGenreRequest;
import com.pratilipi.service.shared.SaveGenreResponse;
import com.pratilipi.service.shared.SavePratilipiRequest;
import com.pratilipi.service.shared.SavePratilipiResponse;
import com.pratilipi.service.shared.SearchRequest;
import com.pratilipi.service.shared.SearchResponse;

@RemoteServiceRelativePath("../service.pratilipi")
public interface PratilipiService extends RemoteService {
	
	// API Version: 4.0; Owner Module: PratilipiContent;
	SavePratilipiResponse savePratilipi(
			SavePratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException;

	// API Version: 4.0; Owner Module: PratilipisContent;
	GetPratilipiListResponse getPratilipiList(
			GetPratilipiListRequest request );
	

	GetLanguageListResponse getLanguageList( GetLanguageListRequest request );


	// API Version: 4.0; Owner Module: AuthorContent;
	SaveAuthorResponse saveAuthor( SaveAuthorRequest request )
			throws InsufficientAccessException, InvalidArgumentException;

	GetAuthorListResponse getAuthorList( GetAuthorListRequest request )
			throws InsufficientAccessException;
	
	
	SaveGenreResponse saveGenre( SaveGenreRequest request )
			throws InvalidArgumentException, InsufficientAccessException;

	GetGenreListResponse getGenreList( GetGenreListRequest request )
			throws InvalidArgumentException, InsufficientAccessException;


	// API Version: 4.0; Owner Module: PratilipiContent;
	AddPratilipiGenreResponse addPratilipiGenre( AddPratilipiGenreRequest request )
			throws InvalidArgumentException, InsufficientAccessException;

	// API Version: 4.0; Owner Module: PratilipiContent;
	DeletePratilipiGenreResponse deletePratilipiGenre( DeletePratilipiGenreRequest request )
			throws InvalidArgumentException, InsufficientAccessException;

	
	AddUserPratilipiResponse addUserPratilipi( AddUserPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException;

	GetUserPratilipiResponse getUserPratilipi( GetUserPratilipiRequest request );
	
	GetUserPratilipiListResponse getUserPratilipiList( GetUserPratilipiListRequest request );
	
	
	// API Version: 4.0; Owner Module: SearchContent;
	SearchResponse search( SearchRequest request );
	
	void ConvertWordToHtml( ConvertPratilipiWordToHtmlRequest request )
			throws IOException;
}
