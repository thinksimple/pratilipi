package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.util.PratilipiDocUtil;
import com.pratilipi.filter.UxModeFilter;


@SuppressWarnings( "serial" )
@Bind( uri = "/pratilipi/content", ver = "3" )
public class PratilipiContentV3Api extends PratilipiContentV2Api {

	@Get
	public GetResponse get( GetRequest request )
			throws InvalidArgumentException, InsufficientAccessException,
			UnexpectedServerException {

		Pratilipi pratilipi = DataAccessorFactory.getDataAccessor().getPratilipi( request.pratilipiId );
		
		if( UxModeFilter.isAndroidApp() || pratilipi.getContentType() == PratilipiContentType.IMAGE ) {
			
			Object content = PratilipiDocUtil.getContent_v3(
					request.pratilipiId,
					request.chapterNo,
					request.pageNo );
			
			return new GetResponse(
					request.pratilipiId,
					request.chapterNo,
					null,
					request.pageNo,
					content );
		
		} else {
		
			if( request.chapterNo == null )
				request.chapterNo = 1;
		
			Object content = PratilipiDocUtil.getContent_v3(
					request.pratilipiId,
					request.chapterNo,
					request.pageNo );

			
			if( content == null ) {
				
				return new GetResponse(
						request.pratilipiId,
						request.chapterNo,
						null,
						request.pageNo,
						"" );
				
			} else if( request.pageNo != null ) {
				
				PratilipiContentDoc.Page page = (PratilipiContentDoc.Page) content;
				return new GetResponse(
						request.pratilipiId,
						request.chapterNo,
						null,
						request.pageNo,
						page.getHtml() );

			} else {
				
				PratilipiContentDoc.Chapter chapter = (PratilipiContentDoc.Chapter) content;
				
				String contentHtml = "";
				for( PratilipiContentDoc.Page page : chapter.getPageList() )
					contentHtml += page.getHtml();
				
				return new GetResponse(
						request.pratilipiId,
						request.chapterNo,
						chapter.getTitle(),
						request.pageNo,
						contentHtml );
				
			}

		}

	}
	
}
