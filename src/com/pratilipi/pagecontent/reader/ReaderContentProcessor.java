package com.pratilipi.pagecontent.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.ClaymusResource;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.pagecontent.PageContentProcessor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentUtil;
import com.pratilipi.service.shared.data.PratilipiData;

public class ReaderContentProcessor extends PageContentProcessor<ReaderContent> {
	
	private static final Logger logger =
			Logger.getLogger( ReaderContentProcessor.class.getName() );

	private static final Gson gson = new GsonBuilder().create();

	private static final String COOKIE_PAGE_NUMBER = "reader_page_number_";
	private static final String COOKIE_CONTENT_SIZE_PRATILIPI = "reader_size_pratilipi";
	private static final String COOKIE_CONTENT_SIZE_IMAGE = "reader_size_image";
	
	
	@Override
	public Resource[] getDependencies( ReaderContent readerContent, HttpServletRequest request ) {
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		
		if( pratilipiHelper.isModeBasic() ) {
			return new Resource[]{
					ClaymusResource.JQUERY_1,
			};
		
		} else {
			return new Resource[] {
					ClaymusResource.JQUERY_2,
					ClaymusResource.POLYMER,
					ClaymusResource.POLYMER_CORE_AJAX,
					ClaymusResource.POLYMER_CORE_COLLAPSE,
					ClaymusResource.POLYMER_CORE_DRAWER_PANEL,
					ClaymusResource.POLYMER_CORE_ICON_BUTTON,
					ClaymusResource.POLYMER_CORE_TOOLBAR,
					ClaymusResource.POLYMER_PAPER_DIALOG,
					ClaymusResource.POLYMER_PAPER_FAB,
					ClaymusResource.POLYMER_PAPER_ICON_BUTTON,
					ClaymusResource.POLYMER_PAPER_SLIDER,
					new Resource() {
						
						@Override
						public String getTag() {
							return "<link rel='import' href='/polymer/pagecontent-reader-menu.html'>";
						}
						
					},
					new Resource() {
						
						@Override
						public String getTag() {
							return "<link rel='import' href='/polymer/pagecontent-reader-navigation.html'>";
						}
						
					},
			};
		}
	}

	@Override
	public String generateTitle( ReaderContent readerContent, HttpServletRequest request ) {
		String pratilipiIdStr = request.getParameter( "id" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );

		Pratilipi pratilipi = DataAccessorFactory
				.getDataAccessor( request )
				.getPratilipi( pratilipiId );
		
		return "Read | " + pratilipi.getTitle()
				+ ( pratilipi.getTitleEn() == null ? "" : " (" + pratilipi.getTitleEn() + ")" );
	}
	
	@Override
	public String generateHtml( ReaderContent readerContent, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		String pratilipiIdStr = request.getParameter( "id" );
		Long pratilipiId = Long.parseLong( pratilipiIdStr );
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		
		// AccessToReadPratilipiContent ?
		if( !PratilipiContentHelper.hasRequestAccessToReadPratilipiContent( request, pratilipi ) )
			throw new InsufficientAccessException();
		
		// AccessToUpdatePratilipiData ?
		boolean showEditOption = PratilipiContentHelper
				.hasRequestAccessToUpdatePratilipiContent( request, pratilipi );

		// Creating PratilipiData
		PratilipiData pratilipiData =
				pratilipiHelper.createPratilipiData( pratilipi, null, null, null );

		// Page # to display
		String pageNoStr = request.getParameter( "page" ) == null
				? pratilipiHelper.getCookieValue( COOKIE_PAGE_NUMBER + pratilipiId )
				: request.getParameter( "page" );

		int pageNo = pageNoStr == null || pageNoStr.isEmpty() ? 1 : Integer.parseInt( pageNoStr );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pratilipiData", pratilipiData );
		dataModel.put( "pageNo", pageNo );
		dataModel.put( "pageNoCookieName", COOKIE_PAGE_NUMBER + pratilipiId );

		
		if( pratilipiData.getContentType() == PratilipiContentType.PRATILIPI ) {

			BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
			BlobEntry blobEntry = null;
			try {
				blobEntry = blobAccessor.getBlob( PratilipiHelper.getContent( pratilipiId ) );
			} catch( IOException e ) {
				logger.log( Level.SEVERE, "Failed to fetch pratilipi content.", e );
				throw new UnexpectedServerException();
			}
			
			String content = blobEntry == null
					? "&nbsp;"
					: new String( blobEntry.getData(), Charset.forName( "UTF-8" ) );
			PratilipiContentUtil pratilipiContentUtil = new PratilipiContentUtil( content );
			String pageContent = pratilipiContentUtil.getContent( pageNo );
			
			dataModel.put( "pageCount", pratilipiContentUtil.getPageCount() );
			dataModel.put( "pageContent", pratilipiHelper.isModeBasic() ? pageContent : gson.toJson( pageContent ) );
			dataModel.put( "contentSize", pratilipiHelper.getCookieValue( COOKIE_CONTENT_SIZE_PRATILIPI ) );
			dataModel.put( "contentSizeCookieName", COOKIE_CONTENT_SIZE_PRATILIPI );

		} else if( pratilipiData.getContentType() == PratilipiContentType.IMAGE ) {
			
			dataModel.put( "pageCount", (int) (long) pratilipiData.getPageCount() );
			dataModel.put( "contentSize", pratilipiHelper.getCookieValue( COOKIE_CONTENT_SIZE_IMAGE ) );
			dataModel.put( "contentSizeCookieName", COOKIE_CONTENT_SIZE_IMAGE );
			
		} else {
			
			throw new InvalidArgumentException( pratilipiData.getContentType() + " content type is not yet supported." );
			
		}
		
		
		if( request.getParameter( "ret" ) != null && !request.getParameter( "ret" ).trim().isEmpty()  )
			dataModel.put( "exitUrl", request.getParameter( "ret" ) );

		dataModel.put( "showEditOption", showEditOption && pratilipiData.getContentType() == PratilipiContentType.PRATILIPI );

		
		String templateName = pratilipiHelper.isModeBasic()
				? getTemplateName().replace( ".ftl", "Basic.ftl" )
				: getTemplateName();
		
		return FreeMarkerUtil.processTemplate( dataModel, templateName );
	}
	
}
