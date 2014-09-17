package com.claymus.module.pagecontent.html.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable
public class HtmlContentEntity extends PageContentEntity implements HtmlContent {

	@Persistent( column = "X_COL_0" )
	private String title;
	
	@Persistent( column = "X_COL_1" )
	private Text html;

	
	@Override
	public String getHtml() {
		return html == null ? null : html.getValue();
	}

	@Override
	public void setHtml( String html ) {
		this.html = new Text( html );
	}

}
