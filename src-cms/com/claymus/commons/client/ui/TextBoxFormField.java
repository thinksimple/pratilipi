package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class TextBoxFormField extends FormField {

	private final Panel panel = new FlowPanel();
	private final Element label = Document.get().createLabelElement();
	private final TextBox textBox = new TextBox();
	private final Element glyphicon = Document.get().createSpanElement();
	
	
	public TextBoxFormField() {

		panel.getElement().appendChild( label );
		panel.add( textBox );
		panel.getElement().appendChild( glyphicon );
		
		panel.setStyleName( "form-group" );
		label.setAttribute( "class", "control-label sr-only" );
		textBox.setStyleName( "form-control" );
		
		textBox.getElement().setAttribute( "data-container", "body" );
		textBox.getElement().setAttribute( "data-placement", "top" );
		
		textBox.addBlurHandler( new BlurHandler() {
			
			@Override
			public void onBlur( BlurEvent event ) {
				validate();
			}
			
		});
		
		initWidget( panel );
	}

	public String getText() {
		return textBox.getText().trim();
	}
	
	public void setEnabled( boolean enabled ) {
		textBox.setEnabled( enabled );
	}
	
	@Override
	public boolean validate() {
		if( isRequired() ) {
			if( textBox.getText().trim() == "" ) {
				markError( "Input Required !" );
				return false;
			} else {
				markSuccess();
				return true;
			}
		}
		
		return true;
	}
	
	private void markSuccess() {
		panel.setStyleName( "form-group has-success has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-ok" );
		hidePopover( textBox.getElement() );
	}
	
	private void markError( String errorMsg ) {
		panel.setStyleName( "form-group has-error has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-remove" );
		showPopover( textBox.getElement(), errorMsg );
	}
	
	public static native void showPopover( Element element, String errorMsg ) /*-{
		$wnd.jQuery( element ).popover( { content : errorMsg } );
		$wnd.jQuery( element ).popover( 'show' );
	}-*/;

	public static native void hidePopover( Element element ) /*-{
		$wnd.jQuery( element ).popover( 'destroy' );
	}-*/;

}
