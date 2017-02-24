function( params ) { 
    var userPratilipihardcoded = {"userId":5124071978172416,"pratilipiId":6046785843757056,"userName":"राधिका","userImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?authorId=6334457649823744&version=1472193677000","userProfilePageUrl":"/radhika-garg-garg","user":{"userId":5124071978172416,"displayName":"राधिका","profilePageUrl":"/radhika-garg-garg","profileImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?authorId=6334457649823744&version=1472193677000"},"addedToLib":false,"hasAccessToReview":true,"isLiked":false};
    var userAuthorhardcoded = {"authorId":5655563241259008,"following":false};
    this.initializePratilipiData = function() {
        this.id = ko.observable();
        this.isGuest = params.isGuest; 
        this.title = ko.observable();
        this.titleEn = ko.observable();
        this.coverImageUrlDesktop = ko.observable();
        this.coverImageUrlMobile = ko.observable();
        this.language = ko.observable();
        this.summary = ko.observable();
        this.state = ko.observable();
        this.publish_date = ko.observable();
        this.averageRating = ko.observable();
        this.ratingCount = ko.observable();
        this.readCount = ko.observable();
        this.type = ko.observable();
        this.pageUrl = ko.observable();
        this.isAddedToLibrary = ko.observable( false );
        
        // this.isFollowRequestInProgress = ko.observable( false );
        // this.isAddToLibraryRequestInProgress = ko.observable( false );

        this.libraryText = ko.computed( function() {
            return this.isAddedToLibrary() ? "- Library" : "+ Library";
        }, this);

        this.isSummaryPresent = ko.computed( function() {
            return this.summary() && this.summary().trim().length;
        }, this);

        
        this.dialog = document.querySelector( '#pratilipi-share-dialog' );
        if ( !this.dialog.showModal ) {
            dialogPolyfill.registerDialog( this.dialog );
        }
    }

    this.initializeAuthorData = function() {
        this.isUserFollowing = ko.observable( false );
        this.author_name = ko.observable();
        this.author_id = ko.observable();
        this.author_page_url = ko.observable();
        this.author_profile_image_url = ko.observable();
        this.author_summary = ko.observable();
        this.followText = ko.computed( function() {
            return this.isUserFollowing() ? "Unfollow" : "Follow";
        }, this); 
        this.followIconText = ko.computed( function() {
            return this.isUserFollowing() ? "done" : "person_add";
        }, this);              
    };

    
    this.pushToPratilipiViewModel = function( pratilipi, userpratilipi ) {
        this.id( pratilipi["pratilipiId"] ).title(pratilipi["title"]).titleEn(pratilipi["titleEn"]).author_name(pratilipi.author.name).author_id(pratilipi.author.authorId).language(pratilipi["language"]).summary(pratilipi["summary"]).pageUrl(pratilipi["pageUrl"]).state(pratilipi["state"]).publish_date( convertDate( pratilipi["listingDateMillis"] ) ).averageRating( self.roundOffToOneDecimal( pratilipi["averageRating"] ) ).ratingCount( pratilipi["ratingCount"] ).readCount(pratilipi["readCount"]).type(pratilipi["type"]);
        this.coverImageUrlDesktop( pratilipi["coverImageUrl"] + "&width=200" ).coverImageUrlMobile( pratilipi["coverImageUrl"] + "&width=150" );
        this.isAddedToLibrary( userpratilipi[ "addedToLib" ] );
    };

    this.pushToAuthorViewModel = function( author, userAuthor ) {
        this.author_id( author["authorId"] ).author_name( author["fullName"] ).author_page_url( author["pageUrl"] ).author_profile_image_url( author["imageUrl"] ).author_summary( author["summary"] );
        this.isUserFollowing( userAuthor["following"] );
    }; 
       
    var self = this;
    // this.getData = function() {
    //     $.ajax({
    //         type: 'get',
    // //        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ self.id,
    //         url: '/api/pratilipi?_apiVer=2&pratilipiId=' + self.id,
    //         data: { 
    //             // 'language': "${ language }"
    //         },
    //         success: function( response ) {
    //             var res = response;       
    //             self.pushToPratilipiViewModel( res );
    //         },
    //         error: function( response ) {
    //             console.log( response );
    //             console.log( typeof(response) );
    //         }
    //     });
    // };

    this.fetchPratilipiAndUserPratilipi = function() {
        var dataAccessor = new DataAccessor();
        dataAccessor.getPratilipiByUri( "/jitesh-donga/vishwamanav", true,
        function( pratilipi, userpratilipi ) {
            self.pushToPratilipiViewModel( pratilipi, userPratilipihardcoded );
            self.fetchAuthorAndUserAuthor();
        });
    };

    this.fetchAuthorAndUserAuthor = function() {
        var dataAccessor = new DataAccessor();
        dataAccessor.getAuthorById( self.author_id() , true, function( author, userAuthor ) {
            self.pushToAuthorViewModel( author, userAuthorhardcoded );
        } );
    };    
    
    this.followAuthor = function() {
        if( self.isGuest() ) {
            goToLoginPage();
        } else {
            self.toggleFollowingState();
            self.sendFollowAuthorAjaxRequest();
        }
    };

    this.addToLibrary = function() {
        if( self.isGuest() ) {
            goToLoginPage();
        } else {
            self.toggleLibraryState();
            self.sendLibraryAjaxRequest();
        }        
    };

    this.toggleLibraryState = function() {
        this.isAddedToLibrary( !this.isAddedToLibrary() );
    };

    this.toggleFollowingState = function() {
        this.isUserFollowing( !this.isUserFollowing() );
    };

    this.sendLibraryAjaxRequest = function() {
        $.ajax({
            type: 'post',
    //        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ self.id,
            url: '/api/userpratilipi/library',
            data: {
                pratilipiId: self.id(),
                addedToLib: self.isAddedToLibrary()
            },
            success: function( response ) {
            },
            error: function( response ) {
                self.toggleLibraryState();
                console.log( response );
                console.log( typeof(response) );
            },
            complete: function() {
                // self.isAddToLibraryRequestInProgress( false );
                /* populate button text acc to the response */
            }
        });              
    };      
    
    this.sendFollowAuthorAjaxRequest = function() {
        $.ajax({
            type: 'post',
    //        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ self.id,
            url: '/api/userauthor/follow',
            data: {
                authorId: self.author_id,
                following: self.isUserFollowing()
            },
            success: function( response ) {
            },
            error: function( response ) {
                self.toggleFollowingState();
            },
            complete: function() {
                // this.isFollowRequestInProgress( false );
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
        this.initializePratilipiData();
        this.initializeAuthorData();
        this.fetchPratilipiAndUserPratilipi();
    }
    
    this.init();
}