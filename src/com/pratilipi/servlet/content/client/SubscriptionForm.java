package com.pratilipi.servlet.content.client;

import com.claymus.commons.shared.UserStatus;
import com.claymus.service.shared.data.UserData;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class SubscriptionForm extends Composite {

	private final TextBox firstNameInput = new TextBox();
	private final TextBox lastNameInput = new TextBox();
	private final TextBox emailInput = new TextBox();
	private final Button cancelButton = new Button( "Cancel" );
	private final Button subscribeButton = new Button( "Subscribe" );
	
	public SubscriptionForm() {

		firstNameInput.getElement().setAttribute( "placeholder", "First Name" );
		lastNameInput.getElement().setAttribute( "placeholder", "Last Name" );
		emailInput.getElement().setAttribute( "placeholder", "Email Address" );
		
		
		Panel namePanel = new FlowPanel();
		namePanel.add( firstNameInput );
		namePanel.add( lastNameInput );
		
		Panel emailPanel = new FlowPanel();
		emailPanel.add( emailInput );
		
		Panel buttonPanel = new FlowPanel();
		buttonPanel.add( subscribeButton );
		buttonPanel.add( cancelButton );
		
		
		Panel panel = new FlowPanel();
		panel.add( namePanel );
		panel.add( emailPanel );
		panel.add( buttonPanel );
		
		namePanel.addStyleName( "namePanel" );
		emailPanel.addStyleName( "emailPanel" );
		buttonPanel.addStyleName( "buttonPanel" );
		panel.addStyleName( "pratilipi-SubscriptionForm" );

		initWidget( panel );

	}
	
	public UserData getUser() {
		UserData userData = new UserData();
		if( firstNameInput.getText().trim().length() != 0 )
			userData.setFirstName( firstNameInput.getText().trim() );
		if( lastNameInput.getText().trim().length() != 0 )
			userData.setLastName( lastNameInput.getText().trim() );
		userData.setEmail( emailInput.getText().trim() );
		userData.setCampaign( "SoftLaunch" );
		userData.setReferer( Window.Location.getParameter( "ref" ) );
		userData.setStatus( UserStatus.PRELAUNCH_SIGNUP );
		return userData;
	}
	
	public void addCancelButtonClickHandler( ClickHandler clickHandler ) {
		cancelButton.addClickHandler( clickHandler );
	}

	public void addSubscribeButtonClickHandler( ClickHandler clickHandler ) {
		subscribeButton.addClickHandler( clickHandler );
	}

	public void setEnabled( boolean enabled ) {
		firstNameInput.setEnabled( enabled );
		lastNameInput.setEnabled( enabled );
		emailInput.setEnabled( enabled );
		subscribeButton.setEnabled( enabled );
		cancelButton.setEnabled( enabled );
	}
	
}
