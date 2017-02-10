function() {
   var self = this;
   this.languageList = [
       { value: "TAMIL", text:  "தமிழ்"},
       { value: "MALAYALAM", text: "മലയാളം"},
       { value: "MARATHI", text: "मराठी"},
       { value: "BENGALI", text:  "বাংলা"},
       { value: "HINDI", text:  "हिंदी"},
       { value: "GUJARATI", text:  "ગુજરાતી"},
       { value: "TELUGU", text:  "తెలుగు"},
       { value: "KANNADA", text: "ಕನ್ನಡ"}
   ];
   this.currentLanguage = ko.observable( "HINDI" );
   this.searchQuery = ko.observable();
   
   this.languageChangeHandler = function() {
     /* change this to current url */
       window.location = ( "http://" + this.currentLanguage() + ".pratilipi.com" );
   };
   
   this.search = function( formElement ) {
       if( self.searchQuery() && self.searchQuery().trim().length ) {
           var search_url = "/search?q=" + self.searchQuery();
           window.location.href = search_url;        
       }
   };
   
   this.openMenuNavigationDrawer = function() {
     if(!(typeof(componentHandler) == 'undefined')){
       componentHandler.upgradeAllRegistered();
     }
     document.querySelector('.mdl-layout').MaterialLayout.toggleDrawer();
   }
}