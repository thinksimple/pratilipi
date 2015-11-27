package com.pratilipi.data.mock;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.gae.AuthorEntity;

public class AuthorMock {

	public static final List<Author> AUTHOR_TABLE = new LinkedList<>();

	public static final Author hiAuthor_1 = new AuthorEntity( 1L );
	public static final Author guAuthor_1 = new AuthorEntity( 2L );
	public static final Author taAuthor_1 = new AuthorEntity( 3L );

	
	static {
		AUTHOR_TABLE.add( hiAuthor_1 );
		AUTHOR_TABLE.add( guAuthor_1 );
		AUTHOR_TABLE.add( taAuthor_1 );

		hiAuthor_1.setFirstName( "हिंदी" );
		hiAuthor_1.setLastName( "ऑथर" );
		hiAuthor_1.setPenName( "पेन नाम" );
		hiAuthor_1.setFirstNameEn( "Hindi" );
		hiAuthor_1.setLastNameEn( "Author" );
		hiAuthor_1.setLanguage( Language.HINDI );
		hiAuthor_1.setSummary( "<b>एक पल का क्रोध आपका भविष्य बिगाड सकता है ।</b> <i>एक पल का क्रोध आपका भविष्य बिगाड सकता है ।</i> एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है ।<br/><br/> एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है ।<br/><br/>" );
		hiAuthor_1.setRegistrationDate( new Date() );
		hiAuthor_1.setContentPublished( 100 );
		hiAuthor_1.setTotalReadCount( 1000L );
		hiAuthor_1.setTotalFbLikeShareCount( 100L );
		
		guAuthor_1.setFirstName( "ગુજરાતી" );
		guAuthor_1.setLastName( "ઐઠોર" );
		guAuthor_1.setFirstNameEn( "Gujarati" );
		guAuthor_1.setLastNameEn( "Author" );
		guAuthor_1.setLanguage( Language.GUJARATI );

		taAuthor_1.setFirstName( "தமிழ்" );
		taAuthor_1.setLastName( "ஆதோர்" );
		taAuthor_1.setFirstNameEn( "Tamil" );
		taAuthor_1.setLastNameEn( "Author" );
		taAuthor_1.setLanguage( Language.TAMIL );
	}
	
}
