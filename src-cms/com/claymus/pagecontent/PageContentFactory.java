package com.claymus.pagecontent;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.data.transfer.PageContent;
import com.claymus.service.shared.data.PageContentData;

public abstract class PageContentFactory<
		P extends PageContent,
		Q extends PageContentData,
		R extends PageContentProcessor<P> > {

	public abstract String getModuleName();
	
	public abstract Double getModuleVersion();

	public abstract Access[] getAccessList();
	
	public boolean hasRequestAccessToAddContent( HttpServletRequest request ) {
		return false;
	};
	
	public boolean hasRequestAccessToUpdateContent( HttpServletRequest request ) {
		return false;
	};
	
	public Q toDataObject( P pageContent ) {
		return null;
	};
	
	public P fromDataObject( Q pageContentData ) {
		return null;
	};

	public P createOrUpdateFromData( Q pageContentData, P pageContent ) {
		return pageContent;
	};

}
