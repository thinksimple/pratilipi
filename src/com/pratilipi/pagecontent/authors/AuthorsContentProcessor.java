package com.pratilipi.pagecontent.authors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.ClaymusResource;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.pagecontent.author.AuthorContentHelper;

public class AuthorsContentProcessor extends PageContentProcessor<AuthorsContent> {

	public static final String ACCESS_ID_AUTHOR_LIST = "author_list";
	public static final String ACCESS_ID_AUTHOR_READ_META_DATA = "author_read_meta_data";
	public static final String ACCESS_ID_AUTHOR_ADD = "author_add";
	
	
	@Override
	public Resource[] getDependencies( AuthorsContent authorsContent, HttpServletRequest request ) {
		
		ClaymusHelper claymusHelper = ClaymusHelper.get( request );

		if( claymusHelper.isModeBasic() ) {
			return new Resource[] {};
		
		} else {
			return new Resource[] {
					ClaymusResource.JQUERY_2,
					ClaymusResource.POLYMER,
					ClaymusResource.POLYMER_CORE_AJAX,
					ClaymusResource.POLYMER_CORE_COLLAPSE,
					ClaymusResource.POLYMER_PAPER_SPINNER,
					ClaymusResource.POLYMER_PAPER_BUTTON,
					ClaymusResource.POLYMER_PAPER_FAB,
					ClaymusResource.POLYMER_CORE_ICONS,
					ClaymusResource.POLYMER_PAPER_ACTION_DIALOG,
					ClaymusResource.POLYMER_PAPER_INPUT_DECORATOR,
					ClaymusResource.POLYMER_PAPER_INPUT,
					ClaymusResource.POLYMER_PAPER_TOAST,
					ClaymusResource.POLYMER_CORE_MENU,
					ClaymusResource.POLYMER_PAPER_DROPDOWN_MENU,
					new Resource() {
						
						@Override
						public String getTag() {
							return "<link rel='import' href='/polymer/pagecontent-author-form.html'>";
						}
						
					},
					new Resource() {
											
						@Override
						public String getTag() {
							return "<link rel='import' href='/polymer/pagecontent-authors.html'>";
						}
						
					}
			};
		}
		
	}
	
	@Override
	public String generateHtml( AuthorsContent authorsContent, HttpServletRequest request )
			throws UnexpectedServerException, InsufficientAccessException {
		
		if( !AuthorContentHelper.hasRequestAccessToListAuthorData( request ) )
			throw new InsufficientAccessException();
		
		PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
		boolean showMetaData =
				pratilipiHelper.hasUserAccess( ACCESS_ID_AUTHOR_READ_META_DATA, false );
		boolean showAddOption =
				pratilipiHelper.hasUserAccess( ACCESS_ID_AUTHOR_ADD, false );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "showMetaData", showMetaData );
		dataModel.put( "showAddOption", showAddOption );
		dataModel.put( "timeZone", pratilipiHelper.getCurrentUserTimeZone() );
		

		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}
	
	@Override
	protected String getTemplateName() {
		return "com/pratilipi/pagecontent/authors/AuthorsContent.ftl";
	}

}
