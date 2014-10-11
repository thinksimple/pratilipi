package com.claymus.pagecontent.html;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentFactory;
import com.claymus.pagecontent.html.gae.HtmlContentEntity;
import com.claymus.pagecontent.html.shared.HtmlContentData;

public class HtmlContentFactory
		extends PageContentFactory<HtmlContent, HtmlContentData, HtmlContentProcessor> {
	
	@Override
	public String getModuleName() {
		return "Html Content";
	}

	@Override
	public Double getModuleVersion() {
		return 2.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	@Override
	public HtmlContentData toDataObject(HtmlContent pageContent) {
		return null;
	}

	@Override
	public HtmlContent fromDataObject(HtmlContentData pageContentData) {
		return null;
	}

	
	public static HtmlContent newHtmlContent() {
		return new HtmlContentEntity();
	}

}
