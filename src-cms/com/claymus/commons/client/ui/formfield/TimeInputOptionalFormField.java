package com.claymus.commons.client.ui.formfield;

import java.util.Date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class TimeInputOptionalFormField extends FormField {

	private final Panel panel = new FlowPanel();
	private final CheckBox checkBox = new CheckBox();
	private final TextBox textBox = new TextBox();
	
	
	public TimeInputOptionalFormField() {

		checkBox.addValueChangeHandler( new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event ) {
				validate();
				textBox.setEnabled( event.getValue() );
			}
			
		});
		checkBox.setValue( false );
		textBox.getElement().setAttribute( "type", "time" );
		textBox.getElement().setAttribute( "data-container", "body" );
		textBox.getElement().setAttribute( "data-placement", "top" );
		textBox.addBlurHandler( new BlurHandler() {
			
			@Override
			public void onBlur( BlurEvent event ) {
				validate();
			}
			
		});
		textBox.setEnabled( false );
		
		// Composing the widget
		panel.add( checkBox );
		panel.add( textBox );
		
		// Setting required style classes
		panel.setStyleName( "input-group" );
		checkBox.setStyleName( "input-group-addon" );
		textBox.setStyleName( "form-control" );
		
		initWidget( panel );
		
		setValue( new Date() );
	}

	public String getValue() {
		return checkBox.getValue() ? textBox.getText() : "";
	}

	public void setValue( Date date ) {
		textBox.setValue( DateTimeFormat.getFormat( "HH:mm" ).format( date ) );
	}
	
	@Override
	public boolean validate() {
		if( checkBox.getValue() && textBox.getValue() == "" ) {
			markError( "Input Required !" );
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
