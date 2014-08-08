package com.pratilipi.module.pagecontent.managepublishers.client;

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
import com.pratilipi.service.shared.AddPublisherRequest;
import com.pratilipi.service.shared.AddPublisherResponse;
import com.pratilipi.service.shared.GetAuthorListRequest;
import com.pratilipi.service.shared.GetAuthorListResponse;
import com.pratilipi.service.shared.GetPublisherListRequest;
import com.pratilipi.service.shared.GetPublisherListResponse;
import com.pratilipi.service.shared.data.PublisherData;

public class ManagePublishers implements EntryPoint {
	private static final PratilipiServiceAsync pratilipiService =
			GWT.create( PratilipiService.class );
	
	public void onModuleLoad() {
		final ManagePublishersView managePublishers = new ManagePublishersViewImpl();
		
		final VerticalPanel vPanel = new VerticalPanel();
		
		//getting list of authors present in database.
		pratilipiService.getPublisherList(new GetPublisherListRequest(), new AsyncCallback<GetPublisherListResponse>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				
			}

			@Override
			public void onSuccess(GetPublisherListResponse response) {
				for(PublisherData publisherData : response.getPublisherList()){
					vPanel.add(new Label(publisherData.getName()));
				}
				
			}});
		
		Button addPublisher = new Button( "Add Publisher" );
		addPublisher.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ValidatePublisher validator = new ValidatePublisher(managePublishers);
				if(validator.validatePublisher()){
					pratilipiService.addPublisher(new AddPublisherRequest(managePublishers.getPublisher()), new AsyncCallback<AddPublisherResponse>(){
	
						@Override
						public void onFailure(Throwable caught) {
							Window.alert( caught.getMessage() );
						}
	
						@Override
						public void onSuccess(AddPublisherResponse result) {
							Window.alert( "Author added successfully !" );
							Window.Location.reload();
						}});
				}
				else 
					Window.alert("Error in form");
			}});
		
		RootPanel.get("PageContent-ManagePublishers").add(vPanel);
		RootPanel.get("PageContent-ManagePublishers").add(managePublishers);
		RootPanel.get("PageContent-ManagePublishers").add(addPublisher);
		
	}
	
}
