package com.pratilipi.api.pratilipi.data;

public enum PratilipiCategory {
	
	    TOP_25 ( "Top25" ),
	    PREMCHAND ( "Premchand" ),
	    ;
	    
	    private final String name;       

	    private PratilipiCategory( String name ) {
	        this.name = name;
	    }

	    public boolean equalsName( String otherName ) {
	        return ( otherName == null ) ? false : name.equals( otherName );
	    }

	    public String toString() {
	       return this.name;
	    }
	    
}
