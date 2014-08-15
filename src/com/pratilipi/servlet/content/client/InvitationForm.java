package com.pratilipi.servlet.content.client;

import com.claymus.client.UserStatus;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class InvitationForm extends Composite {
	private TextBox emailInput = new TextBox();
	private Button inviteButton = new Button("Invite Friend");
	
	private String refId;
	
	public InvitationForm(String refId){
		this.refId = refId;
		
		emailInput.getElement().setPropertyString("placeholder", "Email");
		
		HorizontalPanel inviteForm = new HorizontalPanel();
		inviteForm.setSpacing(10);
		inviteForm.setStyleName("Pratilipi-InvitationForm");
		inviteForm.add(emailInput);
		inviteForm.add(inviteButton);
		
		initWidget(inviteForm);
	}
	
	public UserData getUser() {
		UserData userData = new UserData();
		userData.setEmail( emailInput.getText().trim() );
		userData.setCampaign( "PreLaunch" );
		userData.setReferer( Window.Location.getParameter( "id" ) );
		userData.setStatus( UserStatus.PRELAUNCH_REFERRAL );
		return userData;
	}
	
	public void addInviteButtonClickHandler(ClickHandler clickHandler){
		inviteButton.addClickHandler(clickHandler);
	}
	
	public void reloadForm(){
		emailInput.setText("");
	}

}
