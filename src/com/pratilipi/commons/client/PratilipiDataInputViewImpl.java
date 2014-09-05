package com.pratilipi.commons.client;

import com.claymus.commons.client.ui.formfield.ListBoxFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
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
	private Panel isbnPanel = new FlowPanel();
	private Panel titlePanel = new FlowPanel();
	private Panel authorPanel = new FlowPanel();
	//private Panel publisherPanel = new FlowPanel();
	private Panel languagePanel = new FlowPanel();
	
	private Label formHeader = new Label( "Enter Book Details" );
	private Label pratilipiTypeLabel = new Label( "Type" );
	private Label isbnLabel = new Label("ISBN ");
	private Label titleLabel = new Label("Title ");
	private Label authorLabel = new Label("Author ");
	//private Label publisherLabel = new Label("Publisher ");
	private Label languageLabel = new Label("Language ");
	
	private ListBoxFormField pratilipiTypeInput = new ListBoxFormField();
	private TextInputFormField isbnInput = new TextInputFormField(); 
	private TextInputFormField titleInput = new TextInputFormField();
	private ListBoxFormField authorInput = new ListBoxFormField();
	//private ListBoxFormField publisherInput = new ListBoxFormField();;
	private ListBoxFormField languageInput = new ListBoxFormField();;
	
	private Button add = new Button( "Add" );
	
	public PratilipiDataInputViewImpl() {
		
	    pratilipiTypeInput.setRequired( true );
	    pratilipiTypeInput.addItem( PratilipiType.ARTICLE.getName() );
	    pratilipiTypeInput.addItem( PratilipiType.BOOK.getName());
	    pratilipiTypeInput.addItem( PratilipiType.POEM.getName());
	    pratilipiTypeInput.addItem( PratilipiType.STORY.getName());
	    
	    //adding label and input boxes to horizontal panels.
	    pratilipiTypeLabel.addStyleName( "col-sm-2" );
	    pratilipiTypeInput.addStyleName( "col-sm-4" );
	    pratilipiTypePanel.addStyleName( "row" );
	    pratilipiTypePanel.add( pratilipiTypeLabel );
	    pratilipiTypePanel.add( pratilipiTypeInput );
	    
	    
	    isbnLabel.addStyleName( "col-sm-2" );
	    isbnInput.addStyleName( "col-sm-4" );
	    isbnPanel.addStyleName( "row" );
	    isbnPanel.add( isbnLabel );
	    isbnPanel.add( isbnInput );
	    
	    titleInput.setRequired( true );
	    titleLabel.addStyleName( "col-sm-2" );
	    titleInput.addStyleName( "col-sm-4" );
	    titlePanel.addStyleName( "row" );
	    titlePanel.add( titleLabel );
	    titlePanel.add( titleInput );
	    
	    authorInput.setRequired( true );
	    authorLabel.addStyleName( "col-sm-2" );
	    authorInput.addStyleName( "col-sm-4" );
	    authorPanel.addStyleName( "row" );
	    authorPanel.add(authorLabel);
	    authorPanel.add(authorInput);
	    
	    /* To be used in future implementations
	    publisherInput.setRequired( true );
	    publisherLabelCol.add( publisherLabel );
	    publisherInputCol.add( publisherInput );
	    publisherPanel.add(publisherLabelCol);
	    publisherPanel.add(publisherInputCol);
	    */
	    
	    languageInput.setRequired( true );
	    languageLabel.addStyleName( "col-sm-2" );
	    languageInput.addStyleName( "col-sm-4" );
	    languagePanel.addStyleName( "row" );
	    languagePanel.add(languageLabel);
	    languagePanel.add(languageInput);
	    
	    add.addStyleName( "btn btn-default" );
	    
	    //adding horizontal panels to vertical panel
	    panel.add(formHeader);
	    panel.add( pratilipiTypePanel );
		panel.add( isbnPanel );
		panel.add( titlePanel );
		panel.add( authorPanel );
		//panel.add( publisherPanel );
		panel.add( languagePanel );
		panel.add( add );
		
		//form.add(panel);
		
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
		validated = isbnInput.validate() && validated;
		validated = titleInput.validate() && validated;
		validated = authorInput.validate() && validated;
		validated = languageInput.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		pratilipiTypeInput.setEnabled( enabled);
		isbnInput.setEnabled( enabled );
		titleInput.setEnabled( enabled );
		authorInput.setEnabled( enabled );
		//publisherInput.setEnabled( enabled );
		languageInput.setEnabled( enabled );		
	}

	@Override
	public T getPratilipiData() {
		PratilipiData pratilipiData;
		if( pratilipiTypeInput.getItemText().equals( "BOOK" )){
			pratilipiData = new BookData();
		}
		else if( pratilipiTypeInput.getItemText().equals( "POEM" ) )
			pratilipiData = new PoemData();
		else if( pratilipiTypeInput.getItemText().equals( "STORY" ) )
			pratilipiData = new StoryData();
		else
			pratilipiData = new ArticleData();

		pratilipiData.setPratilipiType( pratilipiTypeInput.getItemText() );
		pratilipiData.setIsbn( isbnInput.getText() );
		//book.setId(bookId);
		pratilipiData.setTitle( titleInput.getText() );
		pratilipiData.setAuthorName( authorInput.getItemText());  //need to find a way to get AUTHOR_ID of required author from database
		pratilipiData.setAuthorId( Long.valueOf(authorInput.getValue()) );
		//book.setPublisherName(publisherInput.getItemText());
		//book.setPublisherId( Long.valueOf(publisherInput.getValue()) );
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
