package com.pratilipi.pagecontent.authors.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pratilipi.service.shared.data.AuthorData;

public class AuthorsDataInputViewImpl extends AuthorsDataInputView {
	private TextBox firstNameInput = new TextBox();
	private TextBox lastNameInput = new TextBox();
	private TextBox emailInput = new TextBox();
	private TextBox penNameInput = new TextBox();
	
	public AuthorsDataInputViewImpl(){
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(1);
		
		HorizontalPanel name = new HorizontalPanel();
		
		//setting place holders for all text boxes
		firstNameInput.getElement().setPropertyString("placeholder", "First Name");
		firstNameInput.setWidth("100px");
		
		lastNameInput.getElement().setPropertyString("placeholder", "Last Name");
		lastNameInput.setWidth("100px");
		
		penNameInput.getElement().setPropertyString("placeholder", "Pen Name");
		penNameInput.setWidth("204px");
		
		emailInput.getElement().setPropertyString("placeholder", "Email");
		emailInput.setWidth("204px");
		
		//add name text boxes to name horizontal panel
		name.add(firstNameInput);
		name.add(lastNameInput);
		
		//add everything to vertical panel
		vPanel.add(name);
		vPanel.add(penNameInput);
		vPanel.add(emailInput);
		
		initWidget(vPanel);
	}
	
	@Override
	public void setAuthor(AuthorData author) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public AuthorData getAuthor() {
		AuthorData author = new AuthorData();
		author.setFirstName(firstNameInput.getText().trim());
		author.setLastName(lastNameInput.getText().trim());
		author.setPenName(penNameInput.getText().trim());
		author.setEmail(emailInput.getText().trim());
		return author;
	}
	@Override
	public String getFirstName() {
		return this.firstNameInput.getText();
	}
	@Override
	public String getLastName() {
		return this.lastNameInput.getText();
	}
	@Override
	public String getPenName() {
		return this.penNameInput.getText();
	}
	@Override
	public String getEmail() {
		return this.emailInput.getText();
	}

	//Error styling functions
	@Override
	public void setFirstNameErrorStyle() {
		firstNameInput.getElement().setAttribute("style", "border:1px solid #FF0000");
		firstNameInput.setWidth("100px");
	}

	@Override
	public void setLastNameErrorStyle() {
		lastNameInput.getElement().setAttribute("style", "border:1px solid #FF0000");
		lastNameInput.setWidth("100px");
	}

	@Override
	public void setPenNameErrorStyle() {
		penNameInput.getElement().setAttribute("style", "border:1px solid #FF0000");
		penNameInput.setWidth("204px");
	}

	@Override
	public void setEmailErrorStyle() {
		emailInput.getElement().setAttribute("style", "border:1px solid #FF0000");
		emailInput.setWidth("204px");
	}

	//Accept styling functions
	@Override
	public void setFirstNameAcceptStyle() {
		firstNameInput.getElement().setAttribute("style", "border:1px solid #000000");
		firstNameInput.setWidth("100px");
	}

	@Override
	public void setLastNameAcceptStyle() {
		lastNameInput.getElement().setAttribute("style", "border:1px solid #000000");
		lastNameInput.setWidth("100px");
	}

	@Override
	public void setPenNameAcceptStyle() {
		penNameInput.getElement().setAttribute("style", "border:1px solid #000000");
		penNameInput.setWidth("204px");
	}

	@Override
	public void setEmailAcceptStyle() {
		emailInput.getElement().setAttribute("style", "border:1px solid #000000");
		emailInput.setWidth("204px");
	}

}
