package com.claymus.pagecontent.blogpost;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentFactory;
import com.claymus.pagecontent.blogpost.gae.BlogPostContentEntity;
import com.claymus.pagecontent.blogpost.shared.BlogPostContentData;
import com.pratilipi.commons.server.PratilipiHelper;

public class BlogPostContentHelper extends PageContentFactory<
		BlogPostContent,
		BlogPostContentData,
		BlogPostContentProcessor> {
	
	private static final Access ACCESS_TO_ADD =
			new Access( "blogpost_add", false, "Add" );
	private static final Access ACCESS_TO_UPDATE =
			new Access( "blogpost_update", false, "Update" );

	
	@Override
	public String getModuleName() {
		return "Blog Post";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_ADD,
				ACCESS_TO_UPDATE };
	}
	

	public static BlogPostContent newBlogPostContent() {
		return new BlogPostContentEntity();
	}

	@Override
	public BlogPostContent createOrUpdateFromData(
			BlogPostContentData blogPostContentData,
			BlogPostContent blogPostContent ) {
		
		if( blogPostContent == null )
			blogPostContent = newBlogPostContent();
		blogPostContent.setTitle( blogPostContentData.getTitle() );
		blogPostContent.setContent( blogPostContentData.getContent() );
		return blogPostContent;
	}


	@Override
	public boolean hasRequestAccessToAddContent( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_ADD );
	}
	
	@Override
	public boolean hasRequestAccessToUpdateContent( HttpServletRequest request ) {
		return PratilipiHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE );
	}

}
