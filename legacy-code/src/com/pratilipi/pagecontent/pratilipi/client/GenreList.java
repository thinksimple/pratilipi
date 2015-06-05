package com.pratilipi.pagecontent.pratilipi.client;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class GenreList extends Composite {
	
	private final Label label = new Label( "" );
	
	
	public GenreList() {
		initWidget( label );
	}
	
	public GenreList( List<String> genreList ) {
		this();
		set( genreList );
	}

	public void set( List<String> genreList ) {
		if( genreList.size() == 0 ) {
			label.setText( "" );

		} else if( genreList.size() == 1 ) {
			label.setText( genreList.get( 0 ) );
	
		} else {
			String genreCsv = genreList.get( 0 );
			for( int i = 1; i < genreList.size(); i++ )
				genreCsv +=  ", " + genreList.get( i );
			label.setText( genreCsv );
		}
	}
	
}
