package com.pratilipi.data;

import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.util.PratilipiDataUtil;

public class MockData {

	public static final List<Page> PAGE_TABLE = new LinkedList<>();
	public static final List<Author> AUTHOR_TABLE = new LinkedList<>();
	public static final List<Pratilipi> PRATILIPI_TABLE = new LinkedList<>();

	public static final List<Object> GLOBAL_INDEX = new LinkedList<>();
	
	
	static {
		
		// AUTHOR Table
		
		Author hiAuthor_1 = new AuthorEntity( 1L );
		hiAuthor_1.setFirstName( "हिंदी" );
		hiAuthor_1.setLastName( "ऑथर" );
		hiAuthor_1.setFirstNameEn( "Hindi" );
		hiAuthor_1.setLastNameEn( "Author" );
		hiAuthor_1.setLanguage( Language.HINDI );

		Author guAuthor_1 = new AuthorEntity( 2L );
		guAuthor_1.setFirstName( "ગુજરાતી" );
		guAuthor_1.setLastName( "ઐઠોર" );
		guAuthor_1.setFirstNameEn( "Gujarati" );
		guAuthor_1.setLastNameEn( "Author" );
		guAuthor_1.setLanguage( Language.GUJARATI );

		Author taAuthor_1 = new AuthorEntity( 3L );
		taAuthor_1.setFirstName( "தமிழ்" );
		taAuthor_1.setLastName( "ஆதோர்" );
		taAuthor_1.setFirstNameEn( "Tamil" );
		taAuthor_1.setLastNameEn( "Author" );
		taAuthor_1.setLanguage( Language.TAMIL );
		
		AUTHOR_TABLE.add( hiAuthor_1 );
		AUTHOR_TABLE.add( guAuthor_1 );
		AUTHOR_TABLE.add( taAuthor_1 );

		
		// PRATILIPI Table
		
		Pratilipi hiPratilipi_1 = new PratilipiEntity( 1L );
		hiPratilipi_1.setTitle( "बुक टाइटल १" );
		hiPratilipi_1.setTitleEn( "Book Title 1" );
		hiPratilipi_1.setLanguage( Language.HINDI );
		hiPratilipi_1.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_1.setSummary( "<b>एक पल का क्रोध आपका भविष्य बिगाड सकता है ।</b> <i>एक पल का क्रोध आपका भविष्य बिगाड सकता है ।</i> एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है ।" );
		hiPratilipi_1.setPublicationYear( 2009 );
		hiPratilipi_1.setType( PratilipiType.BOOK );
		hiPratilipi_1.setReviewCount( 10L );
		hiPratilipi_1.setRatingCount( 10L );
		hiPratilipi_1.setTotalRating( 35L );
		
		Pratilipi hiPratilipi_2 = new PratilipiEntity( 2L );
		hiPratilipi_2.setTitle( "बुक टाइटल २" );
		hiPratilipi_2.setTitleEn( "Book Title 2" );
		hiPratilipi_2.setLanguage( Language.HINDI );
		hiPratilipi_2.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_2.setSummary( "एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है ।" );
		hiPratilipi_2.setPublicationYear( 2010 );
		hiPratilipi_2.setType( PratilipiType.STORY );
		hiPratilipi_2.setReviewCount( 10L );
		hiPratilipi_2.setRatingCount( 10L );
		hiPratilipi_2.setTotalRating( 35L );
		
		Pratilipi hiPratilipi_3 = new PratilipiEntity( 3L );
		hiPratilipi_3.setTitle( "बुक टाइटल ३" );
		hiPratilipi_3.setTitleEn( "Book Title 3" );
		hiPratilipi_3.setLanguage( Language.HINDI );
		hiPratilipi_3.setAuthorId( hiAuthor_1.getId() );
		hiPratilipi_3.setSummary( "एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है । एक पल का क्रोध आपका भविष्य बिगाड सकता है ।" );
		hiPratilipi_3.setPublicationYear( 2011 );
		hiPratilipi_3.setType( PratilipiType.POEM );
		hiPratilipi_3.setReviewCount( 10L );
		hiPratilipi_3.setRatingCount( 10L );
		hiPratilipi_3.setTotalRating( 35L );
		
		Pratilipi guPratilipi_1 = new PratilipiEntity( 4L );
		guPratilipi_1.setTitle( "બૂક તીટલે 1" );
		guPratilipi_1.setTitleEn( "Book Title 1" );
		guPratilipi_1.setLanguage( Language.GUJARATI );
		guPratilipi_1.setAuthorId( guAuthor_1.getId() );
		guPratilipi_1.setSummary( "એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો." );
		guPratilipi_1.setPublicationYear( 2012 );
		guPratilipi_1.setType( PratilipiType.BOOK );
		guPratilipi_1.setReviewCount( 10L );
		guPratilipi_1.setRatingCount( 10L );
		guPratilipi_1.setTotalRating( 35L );
		
		Pratilipi guPratilipi_2 = new PratilipiEntity( 5L );
		guPratilipi_2.setTitle( "બૂક તીટલે 2" );
		guPratilipi_2.setTitleEn( "Book Title 2" );
		guPratilipi_2.setLanguage( Language.GUJARATI );
		guPratilipi_2.setAuthorId( guAuthor_1.getId() );
		guPratilipi_2.setSummary( "એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો." );
		guPratilipi_2.setPublicationYear( 2013 );
		guPratilipi_2.setType( PratilipiType.STORY );
		guPratilipi_2.setReviewCount( 10L );
		guPratilipi_2.setRatingCount( 10L );
		guPratilipi_2.setTotalRating( 35L );
		
		Pratilipi guPratilipi_3 = new PratilipiEntity( 6L );
		guPratilipi_3.setTitle( "બૂક તીટલે 3" );
		guPratilipi_3.setTitleEn( "Book Title 3" );
		guPratilipi_3.setLanguage( Language.GUJARATI );
		guPratilipi_3.setAuthorId( guAuthor_1.getId() );
		guPratilipi_3.setSummary( "એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો. એક ક્ષણ રેજ તમારા ભવિષ્ય વિશે કરી શકો છો." );
		guPratilipi_3.setPublicationYear( 2014 );
		guPratilipi_3.setType( PratilipiType.POEM );
		guPratilipi_3.setReviewCount( 10L );
		guPratilipi_3.setRatingCount( 10L );
		guPratilipi_3.setTotalRating( 35L );
		
		Pratilipi taPratilipi_1 = new PratilipiEntity( 7L );
		taPratilipi_1.setTitle( "புக் டைட்டில் 1" );
		taPratilipi_1.setTitleEn( "Book Title 1" );
		taPratilipi_1.setLanguage( Language.TAMIL );
		taPratilipi_1.setAuthorId( taAuthor_1.getId() );
		taPratilipi_1.setSummary( "ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும் ." );
		taPratilipi_1.setPublicationYear( 2015 );
		taPratilipi_1.setType( PratilipiType.BOOK );
		taPratilipi_1.setReviewCount( 10L );
		taPratilipi_1.setRatingCount( 10L );
		taPratilipi_1.setTotalRating( 35L );
		
		Pratilipi taPratilipi_2 = new PratilipiEntity( 8L );
		taPratilipi_2.setTitle( "புக் டைட்டில் 2" );
		taPratilipi_2.setTitleEn( "Book Title 2" );
		taPratilipi_2.setLanguage( Language.TAMIL );
		taPratilipi_2.setAuthorId( taAuthor_1.getId() );
		taPratilipi_2.setSummary( "ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும் ." );
		taPratilipi_2.setPublicationYear( 2009 );
		taPratilipi_2.setType( PratilipiType.STORY );
		taPratilipi_2.setReviewCount( 10L );
		taPratilipi_2.setRatingCount( 10L );
		taPratilipi_2.setTotalRating( 35L );
		
		Pratilipi taPratilipi_3 = new PratilipiEntity( 9L );
		taPratilipi_3.setTitle( "புக் டைட்டில் 3" );
		taPratilipi_3.setTitleEn( "Book Title 3" );
		taPratilipi_3.setLanguage( Language.TAMIL );
		taPratilipi_3.setAuthorId( taAuthor_1.getId() );
		taPratilipi_3.setSummary( "ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும். ஒரு கணம் ஆத்திரம் உங்கள் எதிர்காலத்தை பற்றி என்ன செய்ய முடியும் ." );
		taPratilipi_3.setPublicationYear( 2010 );
		taPratilipi_3.setType( PratilipiType.POEM );
		taPratilipi_3.setReviewCount( 10L );
		taPratilipi_3.setRatingCount( 10L );
		taPratilipi_3.setTotalRating( 35L );
		
		PRATILIPI_TABLE.add( hiPratilipi_1 );
		PRATILIPI_TABLE.add( hiPratilipi_2 );
		PRATILIPI_TABLE.add( hiPratilipi_3 );
		
		PRATILIPI_TABLE.add( guPratilipi_1 );
		PRATILIPI_TABLE.add( guPratilipi_2 );
		PRATILIPI_TABLE.add( guPratilipi_3 );
		
		PRATILIPI_TABLE.add( taPratilipi_1 );
		PRATILIPI_TABLE.add( taPratilipi_2 );
		PRATILIPI_TABLE.add( taPratilipi_3 );


		// PAGE Table
		
		Page home_Page = new PageEntity( 1L );
		home_Page.setType( PageType.GENERIC );
		home_Page.setUri( null );
		home_Page.setUriAlias( "/" );
		home_Page.setPrimaryContentId( null );

		PAGE_TABLE.add( home_Page );
		
		Page hiAuthor_1_Page = new PageEntity( 101L );
		hiAuthor_1_Page.setType( PageType.AUTHOR );
		hiAuthor_1_Page.setUri( PageType.AUTHOR.getUrlPrefix() + hiAuthor_1.getId() );
		hiAuthor_1_Page.setUriAlias( "/hindi-author-1" );
		hiAuthor_1_Page.setPrimaryContentId( hiAuthor_1.getId() );

		Page guAuthor_1_Page = new PageEntity( 102L );
		guAuthor_1_Page.setType( PageType.AUTHOR );
		guAuthor_1_Page.setUri( PageType.AUTHOR.getUrlPrefix() + guAuthor_1.getId() );
		guAuthor_1_Page.setUriAlias( "/gujarati-author-1" );
		guAuthor_1_Page.setPrimaryContentId( guAuthor_1.getId() );

		Page taAuthor_1_Page = new PageEntity( 103L );
		taAuthor_1_Page.setType( PageType.AUTHOR );
		taAuthor_1_Page.setUri( PageType.AUTHOR.getUrlPrefix() + taAuthor_1.getId() );
		taAuthor_1_Page.setUriAlias( "/tamil-author-1" );
		taAuthor_1_Page.setPrimaryContentId( taAuthor_1.getId() );

		PAGE_TABLE.add( hiAuthor_1_Page );
		PAGE_TABLE.add( guAuthor_1_Page );
		PAGE_TABLE.add( taAuthor_1_Page );

		Page hiPratilipi_1_Page = new PageEntity( 10101L );
		hiPratilipi_1_Page.setType( PageType.PRATILIPI );
		hiPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_1.getId() );
		hiPratilipi_1_Page.setUriAlias( "/hindi-author-1/book-title-1" );
		hiPratilipi_1_Page.setPrimaryContentId( hiPratilipi_1.getId() );

		Page hiPratilipi_2_Page = new PageEntity( 10102L );
		hiPratilipi_2_Page.setType( PageType.PRATILIPI );
		hiPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_2.getId() );
		hiPratilipi_2_Page.setUriAlias( "/hindi-author-1/book-title-2" );
		hiPratilipi_2_Page.setPrimaryContentId( hiPratilipi_2.getId() );

		Page hiPratilipi_3_Page = new PageEntity( 10103L );
		hiPratilipi_3_Page.setType( PageType.PRATILIPI );
		hiPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + hiPratilipi_3.getId() );
		hiPratilipi_3_Page.setUriAlias( "/hindi-author-1/book-title-3" );
		hiPratilipi_3_Page.setPrimaryContentId( hiPratilipi_3.getId() );

		Page guPratilipi_1_Page = new PageEntity( 10201L );
		guPratilipi_1_Page.setType( PageType.PRATILIPI );
		guPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_1.getId() );
		guPratilipi_1_Page.setUriAlias( "/gujarati-author-1/book-title-1" );
		guPratilipi_1_Page.setPrimaryContentId( guPratilipi_1.getId() );

		Page guPratilipi_2_Page = new PageEntity( 10202L );
		guPratilipi_2_Page.setType( PageType.PRATILIPI );
		guPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_2.getId() );
		guPratilipi_2_Page.setUriAlias( "/gujarati-author-1/book-title-2" );
		guPratilipi_2_Page.setPrimaryContentId( guPratilipi_2.getId() );

		Page guPratilipi_3_Page = new PageEntity( 10203L );
		guPratilipi_3_Page.setType( PageType.PRATILIPI );
		guPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + guPratilipi_3.getId() );
		guPratilipi_3_Page.setUriAlias( "/gujarati-author-1/book-title-3" );
		guPratilipi_3_Page.setPrimaryContentId( guPratilipi_3.getId() );

		Page taPratilipi_1_Page = new PageEntity( 10301L );
		taPratilipi_1_Page.setType( PageType.PRATILIPI );
		taPratilipi_1_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_1.getId() );
		taPratilipi_1_Page.setUriAlias( "/tamil-author-1/book-title-1" );
		taPratilipi_1_Page.setPrimaryContentId( taPratilipi_1.getId() );
	
		Page taPratilipi_2_Page = new PageEntity( 10302L );
		taPratilipi_2_Page.setType( PageType.PRATILIPI );
		taPratilipi_2_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_2.getId() );
		taPratilipi_2_Page.setUriAlias( "/tamil-author-1/book-title-2" );
		taPratilipi_2_Page.setPrimaryContentId( taPratilipi_2.getId() );

		Page taPratilipi_3_Page = new PageEntity( 10303L );
		taPratilipi_3_Page.setType( PageType.PRATILIPI );
		taPratilipi_3_Page.setUri( PageType.PRATILIPI.getUrlPrefix() + taPratilipi_3.getId() );
		taPratilipi_3_Page.setUriAlias( "/tamil-author-1/book-title-3" );
		taPratilipi_3_Page.setPrimaryContentId( taPratilipi_3.getId() );
		
		PAGE_TABLE.add( hiPratilipi_1_Page );
		PAGE_TABLE.add( hiPratilipi_2_Page );
		PAGE_TABLE.add( hiPratilipi_3_Page );

		PAGE_TABLE.add( guPratilipi_1_Page );
		PAGE_TABLE.add( guPratilipi_2_Page );
		PAGE_TABLE.add( guPratilipi_3_Page );

		PAGE_TABLE.add( taPratilipi_1_Page );
		PAGE_TABLE.add( taPratilipi_2_Page );
		PAGE_TABLE.add( taPratilipi_3_Page );
		
		
		// GLOBAL_INDEX

		GLOBAL_INDEX.add( PratilipiDataUtil.createData( hiPratilipi_1, hiAuthor_1, hiPratilipi_1_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( hiPratilipi_2, hiAuthor_1, hiPratilipi_2_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( hiPratilipi_3, hiAuthor_1, hiPratilipi_3_Page ) );
		
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( guPratilipi_1, guAuthor_1, guPratilipi_1_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( guPratilipi_2, guAuthor_1, guPratilipi_2_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( guPratilipi_3, guAuthor_1, guPratilipi_3_Page ) );
		
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( taPratilipi_1, taAuthor_1, taPratilipi_1_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( taPratilipi_2, taAuthor_1, taPratilipi_2_Page ) );
		GLOBAL_INDEX.add( PratilipiDataUtil.createData( taPratilipi_3, taAuthor_1, taPratilipi_3_Page ) );

	}
	
}
