package com.claymus.commons.client.ui.formfield;

import com.google.gwt.user.client.ui.Composite;

public abstract class FormField extends Composite {

	private boolean isRequired = false;
	
	
	public boolean isRequired() {
		return isRequired;
	}
	
	public void setRequired( boolean required ) {
		this.isRequired = required;
	}
	
	public abstract boolean validate();
	
}
