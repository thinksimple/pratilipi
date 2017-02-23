function( params ) { 
    this.initializeData = function() {
        this.id = getQueryVariable("id");
        this.isGuest = params.isGuest();
        this.navigationList = ko.observableArray([]);
        this.title = ko.observable();
        this.titleEn = ko.observable();
        this.coverImageUrlDesktop = ko.observable();
        this.coverImageUrlMobile = ko.observable();
        this.author_name = ko.observable();
        this.author_id = ko.observable();
        this.language = ko.observable();
        this.summary = ko.observable();
        this.state = ko.observable();
        this.publish_date = ko.observable();
        this.averageRating = ko.observable();
        this.ratingCount = ko.observable();
        this.readCount = ko.observable();
        this.type = ko.observable();
        this.pageUrl = ko.observable();
        
        this.isFollowRequestInProgess = ko.observable( false );
        this.isAddToLibraryRequestInProgess = ko.observable( false );
        
        this.dialog = document.querySelector( '#pratilipi-share-dialog' );
        if ( !this.dialog.showModal ) {
            dialogPolyfill.registerDialog( this.dialog );
        }
    }

    
    this.pushToViewModel = function( data ) {
        this.title(data["title"]).titleEn(data["titleEn"]).author_name(data.author.name).author_id(data.author.authorId).language(data["language"]).summary(data["summary"]).pageUrl(data["pageUrl"]).state(data["state"]).publish_date( convertDate( data["listingDateMillis"] ) ).averageRating( self.roundOffToOneDecimal( data["averageRating"] ) ).ratingCount( data["ratingCount"] ).readCount(data["readCount"]).type(data["type"]);
        this.coverImageUrlDesktop( data["coverImageUrl"] + "&width=200" ).coverImageUrlMobile( data["coverImageUrl"] + "&width=150" );
    }
    var self = this;
    this.getData = function() {
        $.ajax({
            type: 'get',
    //        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ self.id,
            url: '/api/pratilipi?_apiVer=2&pratilipiId=' + self.id,
            data: { 
                // 'language': "${ language }"
            },
            success: function( response ) {
                var res = response;       
                self.pushToViewModel( res );
            },
            error: function( response ) {
                console.log( response );
                console.log( typeof(response) );
            }
        });
    };
    
    this.followAuthor = function() {
        if( self.isGuest() ) {
            goToLoginPage();
        } else {
            sendFollowAuthorAjaxRequest();
        }
    };
    
    this.sendFollowAuthorAjaxRequest = function() {
        self.isFollowRequestInProgess( true );
        $.ajax({
            type: 'post',
    //        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ self.id,
            url: '/api/userauthor/follow',
            data: {
                authorId: self.author_id,
                following: true
            },
            success: function( response ) {
                var res = response;       
                self.pushToViewModel( res );
            },
            error: function( response ) {
                console.log( response );
                console.log( typeof(response) );
            },
            complete: function() {
                this.isFollowRequestInProgess( false );
                /* populate button text acc to the response */
            }
        });              
    };
    
    this.getShareUrl = function( share_platform ) {
        var share_url = encodeURIComponent( window.location.origin + self.pageUrl() );
        if( share_platform == "Whatsapp" ) {          
            var text = "%22" + self.title() + "%22${ _strings.whatsapp_read_story }%20" + share_url +"%0A${ _strings.whatsapp_read_unlimited_stories }";
            return text; /* test if message goes correctly in whatsapp */
        } else {
            return share_url;
        }
    };
    
    this.shareOnSocialMedia = function( item, event ) {
        var element = event.currentTarget;
        var share_platform = element.getAttribute('data-share-platform');
        var shareUrl = self.getShareUrl( share_platform );
        var functionToInvoke = "sharePratilipiOn" + share_platform;
        window[ functionToInvoke ]( shareUrl );        /* check later if its supported in older browsers */
        self.hideShareModal();
    };
    
    this.openShareModal = function() {
        self.dialog.showModal();
    };
    this.hideShareModal = function() {
        self.dialog.close();
    };   
    
    this.roundOffToOneDecimal = function( number ) {
        return Math.round( number * 10 ) / 10;
    };
    
    this.init = function() {
        this.initializeData();
        this.getData();
    }
    
    this.init();
}