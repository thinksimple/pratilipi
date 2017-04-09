/* Google Analytics */
/* 
 * GA has 4 parameters to record:
 * Category, Action, Label, Value
 * 
 * https://drive.google.com/drive/u/1/folders/0B1GuDK5-Y90aVE0zVldHRm95RU0
 * Event Category => The entity being tracked - pratilipi, author, notification, review etc.
 * Event Action => The action the user is taking.
 * Event Label => The page / location from where it is taken.
 * Event Value => The difference in time between last action taken and current action.
 * 
 * 
 * Function Names and Definition:
 * ga_CALV => category, action, location, value
 * ga_CAL => category, action, location
 * ga_CA => category, action
 * 
 */

var ga_value = new Date().getTime();

String.prototype.count = function( s1 ) {
    return ( this.length - this.replace( new RegExp(s1,"g"), '' ).length ) / s1.length;
};


var LOCATION = {

	HOME: { value: "HomePage", urlPattern: "/\/$/s" },
	LIST: { value: "ListPage", urlPattern: "/^\/[^\/]*.*$/s" },
	SEARCH : { value: "SearchPage", urlPattern: "/\/search$/s" },
	LIBRARY: { value: "LibraryPage", urlPattern: "/\/library$/s" },
	EVENT: { value: "EventPage", urlPattern: "/\/event/\[^\/]*.*$/s" },

	AUTHOR: { value: "AuthorPage", urlPattern: "/^\/[^\/]*.*$/s" },
	USER: { value: "UserPage", urlPattern: "/^\/[^\/]*.*$/s" },
	PRATILIPI: { value: "PratilipiPage", urlPattern: "/^\/[^\/]*\/[^\/]*/s" },

	LOGIN: { value: "LoginPage", urlPattern: "/\/login$/s" },
	REGISTER: { value: "RegisterPage", urlPattern: "/\/register$/s" },
	FORGOT_PASSWORD: { value: "ForgotPasswordPage", urlPattern: "/\/forgot-password$/s" },

	NOTIFICATIONS: { value: "NotificationsPage", urlPattern: "/\/notifications$/s" },
	SETTINGS: { value: "UserSettings", urlPattern: "/\/settings$/s" }

};


function ga_CALV( category, action, location, value ) {
	if( category == null || action == null || location == null || value == null ) {
		console.log( category, action, location, value );
		return;
	}
	ga( 'send', 'event', category, action, location, value );
	console.log( category, action, location, value );

	ga_value = new Date().getTime(); /* reset timer */ 
}

function ga_CAL( category, action, location ) {
	if( category == null || action == null || location == null ) {
		console.log( category, action, location );
		return;
	}
	ga_CALV( category, action, location, new Date().getTime() - ga_value );
}

function ga_CA( category, action ) {
	if( category == null || action == null ) {
		console.log( category, action );
		return;
	}

	/* Logic For Location */
	var location = null;

	var currentLocation = window.location.pathname;
	if( currentLocation == "/" )
		location = LOCATION.HOME.value;
	else if( currentLocation == "/search" )
		location = LOCATION.SEARCH.value;
	else if( currentLocation == "/library" )
		location = LOCATION.LIBRARY.value;

	else if( currentLocation == "/login" )
		location = LOCATION.LOGIN.value;
	else if( currentLocation == "/register" )
		location = LOCATION.REGISTER.value;
	else if( currentLocation == "/forgot-password" )
		location = LOCATION.FORGOT_PASSWORD.value;

	else if( currentLocation == "/notifications" )
		location = LOCATION.NOTIFICATIONS.value;
	else if( currentLocation == "/settings" )
		location = LOCATION.SETTINGS.value;

	<#list navigationList as navigation>
		<#list navigation.linkList as link>

	else if( currentLocation == "${ link.url }" && ! "${ link.url }".startsWith( "/search?" ) )
		location = LOCATION.LIST.value;	

		</#list>
	</#list>

	else if( currentLocation.startsWith( "/event/" ) && currentLocation.count( '/' ) == 2 )
		location = LOCATION.EVENT.value;

	if( location == null ) { /* Author or Pratilipi */
		if( currentLocation.count( '/' ) == 1 ) 
			location = appViewModel.isUserPage() ? LOCATION.USER.value : LOCATION.AUTHOR.value;
		else if( currentLocation.count( '/' ) == 2 )
			location = LOCATION.PRATILIPI.value;
	}

	ga_CAL( category, action, location );

}
