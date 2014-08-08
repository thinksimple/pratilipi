package com.pratilipi.module.pagecontent.manageauthors.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.client.PratilipiService;
import com.pratilipi.service.client.PratilipiServiceAsync;
import com.pratilipi.service.shared.AddAuthorRequest;
import com.pratilipi.service.shared.AddAuthorResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.data.AuthorData;

public class ManageAuthors implements EntryPoint {
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {
		final ManageAuthorsView manageAuthors = new ManageAuthorsViewImpl();
		
		final VerticalPanel vPanel = new VerticalPanel();
		
		//getting list of authors present in database.
		pratilipiService.getAuthorList(new GetAuthorListRequest(), new AsyncCallback<GetAuthorListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}

			@Override
			public void onSuccess(GetAuthorListResponse response) {
				for(AuthorData authorData : response.getAuthorList()){
					vPanel.add(new Label(authorData.getFirstName()+authorData.getLastName()));
				}
				
			}});
		
		Button addAuthor = new Button( "Add Author" );
		addAuthor.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ValidateAuthor validator = new ValidateAuthor(manageAuthors);
				if(validator.validateAuthor()){
					pratilipiService.addAuthor(new AddAuthorRequest(manageAuthors.getAuthor()), new AsyncCallback<AddAuthorResponse>(){
	
						@Override
						public void onFailure(Throwable caught) {
							Window.alert( caught.getMessage() );
						}
	
						@Override
						public void onSuccess(AddAuthorResponse result) {
							Window.alert( "Author added successfully !" );
							Window.Location.reload();
						}});
				}
				else 
					Window.alert("Error in form");
			}});
		
		RootPanel.get("PageContent-ManageAuthors").add(vPanel);
		RootPanel.get("PageContent-ManageAuthors").add(manageAuthors);
		RootPanel.get("PageContent-ManageAuthors").add(addAuthor);
		
	}
	
}
