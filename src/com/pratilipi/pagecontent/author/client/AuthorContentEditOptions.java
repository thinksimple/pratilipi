package com.pratilipi.pagecontent.author.client;

import com.claymus.commons.client.ui.Dropdown;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.data.AuthorData;

public class AuthorContentEditOptions implements EntryPoint, ClickHandler {
	
	private PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	
	// Author data edit options widgets
	private Anchor editAnchor;

	private AuthorData authorData;


	@Override
	public void onModuleLoad() {
		
		// Decoding AuthorData
		RootPanel rootPanel = RootPanel.get( "PageContent-Author-EncodedData" );
		String authorDataEncodedStr = rootPanel.getElement().getInnerText();
		try {
			SerializationStreamReader streamReader =
					( (SerializationStreamFactory) pratilipiService )
							.createStreamReader( authorDataEncodedStr );
			authorData = (AuthorData) streamReader.readObject();
		} catch( SerializationException e ) {
			Window.alert( e.getMessage() );
		}


		// Author data edit options widgets
		editAnchor = new Anchor( "Edit" );
		editAnchor.addClickHandler( this );

		
		Dropdown dropdown = new Dropdown( authorData.getName() );
		dropdown.add( editAnchor );

		rootPanel = RootPanel.get( "PageContent-Author-Title" );
		rootPanel.getElement().setInnerHTML( "" );
		rootPanel.add( dropdown );

	}
	
	
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
	}

}
