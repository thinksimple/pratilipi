package com.claymus.commons.client.ui.formfield;
import java.util.Date;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class CurrencyInputFormField extends FormField {

	private final Panel panel = new FlowPanel();
	private final Element symbol = Document.get().createSpanElement();
	private final TextBox textBox = new TextBox();
	
	private static final RegExp regExp = RegExp.compile( "^-?\\d+\\.?\\d*$" );
	
	
	public CurrencyInputFormField() {

		symbol.setInnerText( "Rs." );
		textBox.getElement().setAttribute( "placeholder", "Amount" );
		textBox.getElement().setAttribute( "data-container", "body" );
		textBox.getElement().setAttribute( "data-placement", "top" );
		textBox.addBlurHandler( new BlurHandler() {
			
			@Override
			public void onBlur( BlurEvent event ) {
				validate();
			}
			
		});
		
		
		// Composing the widget
		panel.getElement().appendChild( symbol );
		panel.add( textBox );
		
		
		// Setting required style classes
		panel.setStyleName( "input-group" );
		symbol.setAttribute( "class", "input-group-addon" );
		textBox.setStyleName( "form-control" );
		
		
		initWidget( panel );
	}

	public String getAmount() {
		return textBox.getValue() == "" ? textBox.getText() : "";
	}

	public void setAmount( Date date ) {
		textBox.setValue( DateTimeFormat.getFormat( "HH:mm" ).format( date ) );
	}
	
	@Override
	public boolean validate() {
		if( isRequired() && textBox.getText() == "" ) {
			markError( "Input Required !" );
			return false;
		} else if( textBox.getText() != "" && ! regExp.test( textBox.getText() ) ) {
			markError( "Provide a valid number !" );
			return false;
		} else {
			markSuccess();
			return true;
		}
	}
	
	private void markSuccess() {
		panel.setStyleName( "input-group has-success" );
		hidePopover( textBox.getElement() );
	}
	
	private void markError( String errorMsg ) {
		panel.setStyleName( "input-group has-error" );
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
