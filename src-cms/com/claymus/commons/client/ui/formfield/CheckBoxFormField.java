package com.claymus.commons.client.ui.formfield;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class CheckBoxFormField extends FormField {

	private final Panel formGroup = new FlowPanel();
	private final Panel checkBoxPanel = new SimplePanel();
	private final Element labelElement = Document.get().createLabelElement();
	private final InputElement checkBox = Document.get().createCheckInputElement();
	private final Element label = Document.get().createSpanElement();

	
	public CheckBoxFormField() {
		
		// Composing the widget
		formGroup.getElement().appendChild( checkBoxPanel.getElement() );
		checkBoxPanel.getElement().appendChild( labelElement );
		labelElement.appendChild( checkBox );
		labelElement.appendChild( label );
		
		
		// Setting required style classes
		formGroup.setStyleName( "form-group" );
		checkBoxPanel.setStyleName( "checkbox" );
		
		
		initWidget( formGroup );
	}

	public boolean isChecked() {
		return checkBox.isChecked();
	}
	
	public void setText( String text ) {
		label.setInnerText( text );
	}
	
	@Override
	public boolean validate() {
		return true;
	}
	
}
