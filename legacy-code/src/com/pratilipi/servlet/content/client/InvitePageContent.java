package com.pratilipi.servlet.content.client;

import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.InviteUserRequest;
import com.claymus.service.shared.InviteUserResponse;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class InvitePageContent extends Composite implements EntryPoint {
	
	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	private InvitationForm invitationForm;
	
	private Image facebook = new Image("theme.pratilipi/images/facebook.png");
	private Image twitter = new Image("theme.pratilipi/images/twitter.png");
	private Image googlePlus = new Image("theme.pratilipi/images/google.png");
	
	private String userId;
	private String url;

	@Override
	public void onModuleLoad() {
		
		userId = Window.Location.getParameter("id");
		url = "www.pratilipi.com?id=" + userId;
		
		invitationForm = new InvitationForm(userId);
		
		ClickHandler inviteButtonClickHandler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				UserData userData = invitationForm.getUser();
				if( isValidEmail( userData )){
					claymusService.inviteUser( new InviteUserRequest( userData ), new AsyncCallback<InviteUserResponse>() {
						
						@Override
						public void onSuccess( InviteUserResponse response ) {
							Window.alert("Invitation Sent!");
							invitationForm.reloadForm();
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							Window.alert( caught.getMessage() );
						}
						
					});
				}
		}};
			
		invitationForm.addInviteButtonClickHandler(inviteButtonClickHandler);
		
		//Social Media share
		facebook.setStyleName("SharePanel-Image");
		facebook.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://www.facebook.com/sharer.php?u="+url, "_blank", "enabled");
			}});
		
		twitter.setStyleName("SharePanel-Image");
		twitter.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://twitter.com/home?status="+url, "_blank", "enabled");
			}});
		
		googlePlus.setStyleName("SharePanel-Image");
		googlePlus.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Window.open("https://plus.google.com/share?url="+url, "_blank", "enabled");
			}});
		
		
			
		HorizontalPanel sharePanel = new HorizontalPanel();
		sharePanel.setSpacing(10);
		sharePanel.add(facebook);
		sharePanel.add(twitter);
		sharePanel.add(googlePlus);
		
		//Panels added to div
		RootPanel.get("Pratilipi-InvitationForm").add(invitationForm);
		RootPanel.get("Pratilipi-SharePanel").add(sharePanel);
		
	}
	
	private boolean isValidEmail( UserData user ){
		
		Boolean validated = true;
 
		final String emailPattern = new String("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
		final RegExp emailExp = RegExp.compile(emailPattern);
		
		MatchResult matcher = emailExp.exec( user.getEmail() );
		Boolean matchFound = (matcher!=null);
		
		if( user.getEmail().isEmpty() ){
			this.invitationForm.setErrorStyle();
			Window.alert( "Email cannot be empty" );
			validated = false;
		}
		else if(!matchFound){
			this.invitationForm.setErrorStyle();
			Window.alert( "Not a valid email address" );
			validated = false;
		}
		else{
			this.invitationForm.removeErrorStyle();
			validated = true;
		}

		return validated;
	}

}
