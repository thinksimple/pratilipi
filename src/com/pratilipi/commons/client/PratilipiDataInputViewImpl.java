package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.service.shared.data.ArticleData;
import com.pratilipi.service.shared.data.AuthorData;
import com.pratilipi.service.shared.data.BookData;
import com.pratilipi.service.shared.data.LanguageData;
import com.pratilipi.service.shared.data.PoemData;
import com.pratilipi.service.shared.data.PratilipiData;
import com.pratilipi.service.shared.data.PublisherData;
import com.pratilipi.service.shared.data.StoryData;

public class PratilipiDataInputViewImpl<T extends PratilipiData> extends PratilipiDataInputView<T> {
	
	private Panel panel = new FlowPanel();
	private Panel pratilipiTypePanel = new FlowPanel();
	private Panel pratilipiTypeInputCol = new SimplePanel();
	private Panel titlePanel = new FlowPanel();
	private Panel titleInputCol = new SimplePanel();
	private Panel authorPanel = new FlowPanel();
	private Panel authorInputCol = new SimplePanel();
	//private Panel publisherPanel = new FlowPanel();
	private Panel languagePanel = new FlowPanel();
	private Panel languageInputCol = new SimplePanel();
	
	private Label formHeader = new Label( "Enter Book Details" );
	
	private ListBoxFormField pratilipiTypeInput = new ListBoxFormField();
	private TextInputFormField titleInput = new TextInputFormField();
	private ListBoxFormField authorInput = new ListBoxFormField();
	//private ListBoxFormField publisherInput = new ListBoxFormField();;
	private ListBoxFormField languageInput = new ListBoxFormField();;
	
	private Button add = new Button( "Add" );
	
	
	private final PratilipiType pratilipiType;
	
	public PratilipiDataInputViewImpl( PratilipiType pratilipiType ) {
		
		this.pratilipiType = pratilipiType;
		
		pratilipiTypeInput.addItem("Select Type", "");
	    pratilipiTypeInput.setRequired( true );
	    pratilipiTypeInput.addItem( PratilipiType.ARTICLE.getName() );
	    pratilipiTypeInput.addItem( PratilipiType.BOOK.getName());
	    pratilipiTypeInput.addItem( PratilipiType.POEM.getName());
	    pratilipiTypeInput.addItem( PratilipiType.STORY.getName());
	    
	    //adding label and input boxes to horizontal panels.
	    pratilipiTypeInputCol.addStyleName( "col-sm-4" );
	    pratilipiTypeInputCol.add( pratilipiTypeInput );
	    pratilipiTypePanel.addStyleName( "row" );
	    pratilipiTypePanel.add( pratilipiTypeInputCol );
	    
	    titleInput.setPlaceholder( "Title" );
	    titleInput.setRequired( true );
	    titleInputCol.addStyleName( "col-sm-4" );
	    titleInputCol.add( titleInput );
	    titlePanel.addStyleName( "row" );
	    titlePanel.add( titleInputCol );
	    
	    authorInput.addItem("Select Author", "");
	    authorInput.setRequired( true );
	    authorInputCol.addStyleName( "col-sm-4" );
	    authorInputCol.add( authorInput );
	    authorPanel.addStyleName( "row" );
	    authorPanel.add( authorInputCol );
	    
	    /* To be used in future implementations
	    publisherInput.setRequired( true );
	    publisherLabelCol.add( publisherLabel );
	    publisherInputCol.add( publisherInput );
	    publisherPanel.add(publisherLabelCol);
	    publisherPanel.add(publisherInputCol);
	    */
	    languageInput.addItem("Select Language", "" );
	    languageInput.setRequired( true );
	    languageInputCol.addStyleName( "col-sm-4" );
	    languageInputCol.add( languageInput );
	    languagePanel.addStyleName( "row" );
	    languagePanel.add(languageInputCol);
	    
	    add.addStyleName( "btn btn-default" );
	    
	    //adding horizontal panels to vertical panel
	    panel.add(formHeader);
	    panel.add( pratilipiTypePanel );
		panel.add( titlePanel );
		panel.add( authorPanel );
		panel.add( languagePanel );
		panel.add( add );
		
		initWidget( panel );
	}
	
	@Override
	public HandlerRegistration addAddButtonClickHandler(ClickHandler clickHandler){
		return add.addClickHandler( clickHandler );
	}

	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = pratilipiTypeInput.validate() && validated;
		validated = titleInput.validate() && validated;
		validated = authorInput.validate() && validated;
		validated = languageInput.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		pratilipiTypeInput.setEnabled( enabled);
		titleInput.setEnabled( enabled );
		authorInput.setEnabled( enabled );
		languageInput.setEnabled( enabled );		
	}

	@Override
	public T getPratilipiData() {
		PratilipiData pratilipiData;
		if( pratilipiTypeInput.getItemText().equals( "Book" )){
			pratilipiData = new BookData();
		}
		else if( pratilipiTypeInput.getItemText().equals( "Poem" ) )
			pratilipiData = new PoemData();
		else if( pratilipiTypeInput.getItemText().equals( "Story" ) )
			pratilipiData = new StoryData();
		else
			pratilipiData = new ArticleData();

		pratilipiData.setPratilipiType( pratilipiType );
		pratilipiData.setTitle( titleInput.getText() );
		pratilipiData.setAuthorName( authorInput.getItemText());
		pratilipiData.setAuthorId( Long.valueOf(authorInput.getValue()) );
		pratilipiData.setLanguageName(languageInput.getItemText());
		pratilipiData.setLanguageId(Long.valueOf(languageInput.getValue()));
		
		return (T) pratilipiData;
	}

	@Override
	public void setPratilipiData( T t ) {
		//TODO
	}

	@Override
	public void setAuthorList(AuthorData authorData) {
		// TODO Auto-generated method stub
		this.authorInput.addItem(authorData.getFirstName() + 
									" " + 
									authorData.getLastName(), 
								 authorData.getId().toString());
		
	}

	@Override
	public void setLanguageList(LanguageData languageData) {
		this.languageInput.addItem(languageData.getName(), 
									languageData.getId().toString());
		
	}

	@Override
	public void setPublisherList(PublisherData publisherData) {
		// TODO Auto-generated method stub
		
	}
		
}
