package com.pratilipi.module.pagecontent.managepublishers.client;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.shared.data.PublisherData;

public class ManagePublishersViewImpl extends ManagePublishersView {
	private TextBox nameInput = new TextBox();
	private TextBox emailInput = new TextBox();
	
	public ManagePublishersViewImpl(){
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(1);
		
		//setting place holders for all text boxes
		nameInput.getElement().setPropertyString("placeholder", "Name");
		nameInput.setWidth("150px");
		
		emailInput.getElement().setPropertyString("placeholder", "Email");
		emailInput.setWidth("150px");
		
		
		//add everything to vertical panel
		vPanel.add(nameInput);
		vPanel.add(emailInput);
		
		initWidget(vPanel);
	}
	
	@Override
	public void setPublisher(PublisherData author) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public PublisherData getPublisher() {
		PublisherData publisher = new PublisherData();
		publisher.setName(nameInput.getText().trim());
		publisher.setEmail(emailInput.getText().trim());
		return publisher;
	}
	@Override
	public String getName() {
		return this.nameInput.getText();
	}
	@Override
	public String getEmail() {
		return this.emailInput.getText();
	}

	//Error styling functions
	@Override
	public void setNameErrorStyle() {
		nameInput.getElement().setAttribute("style", "border:1px solid #FF0000");
		nameInput.setWidth("150px");
	}

	@Override
	public void setEmailErrorStyle() {
		emailInput.getElement().setAttribute("style", "border:1px solid #FF0000");
		emailInput.setWidth("150px");
	}

	//Accept styling functions
	@Override
	public void setNameAcceptStyle() {
		nameInput.getElement().setAttribute("style", "border:1px solid #000000");
		nameInput.setWidth("150px");
	}

	@Override
	public void setEmailAcceptStyle() {
		emailInput.getElement().setAttribute("style", "border:1px solid #000000");
		emailInput.setWidth("150px");
	}

}
