function( params ) { 
    console.log( params );
    var self = this;
    this.pratilipiId = ko.observable( null );
//    this.abc = "abc";
    this.isUserFollowing = ko.observable( false );

    this.followText = ko.computed( function() {
        return this.isUserFollowing() ? "${ _strings.author_unfollow }" : "${ _strings.author_follow }";          
    }, this);

    // this.userPratilipihardcoded = {"userId":5124071978172416,"pratilipiId":6046785843757056,"userName":"राधिका","userImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?authorId=6334457649823744&version=1472193677000","userProfilePageUrl":"/radhika-garg-garg","user":{"userId":5124071978172416,"displayName":"राधिका","profilePageUrl":"/radhika-garg-garg","profileImageUrl":"https://d3cwrmdwk8nw1j.cloudfront.net/author/image?authorId=6334457649823744&version=1472193677000"},"addedToLib":false,"hasAccessToReview":true,"isLiked":false};
    // var userAuthorhardcoded = {"authorId":5655563241259008,"following":false};
    this.pratilipiObj = params.pratilipiObj;
    this.userPratilipiObj = params.userPratilipiObj;
    this.authorObj = ko.mapping.fromJS( {"profileImageUrl": "","pageUrl": "", "name": "", "summary": ""} , {}, this.authorObj );
    this.userAuthorObj = ko.mapping.fromJS( {"following": false}, {}, this.userAuthorObj ); 

    this.getShareUrl = function( share_platform ) {
        var share_url = encodeURIComponent( window.location.origin + this.pratilipiObj.pageUrl() );
        if( share_platform == "Whatsapp" ) {          
            var text = "%22" + this.pratilipiObj.title() + "%22${ _strings.whatsapp_read_story }%20" + share_url +"%0A${ _strings.whatsapp_read_unlimited_stories }";
            return text; /* test if message goes correctly in whatsapp */
        } else {
            return share_url;
        }
    };

    this.shareOnSocialMedia = function( item, event ) {
        var element = event.currentTarget;
        var share_platform = element.getAttribute('data-share-platform');
        var shareUrl = this.getShareUrl( share_platform );
        var functionToInvoke = "sharePratilipiOn" + share_platform;
        window[ functionToInvoke ]( shareUrl );        /* check later if its supported in older browsers */
        this.hideShareModal();
    };

    this.coverUploaded = ko.observable();
    this.chooseImageFile = function() {
    	document.getElementById( "uploadPratilipiImageInput" ).click();
	};

	this.uploadPratilipiImage = function( vm, evt ) {
		var files = evt.target.files;
		var file = files[0];
		if( file != null && ( file.name.match( ".*\.jpg" ) || file.name.match( ".*\.png" ) ) ) {
			self.coverUploaded( true );
			document.getElementById( "uploadPratilipiImageForm" ).submit();
		}
	};

	this.iframeLoaded = function( vm, evt ) {
		if( self.coverUploaded() ) {
			self.coverUploaded( false );
			var response = JSON.parse( evt.currentTarget.contentDocument.body.innerText );
			if( response[ "message" ] != null ) {
				ToastUtil.toast( response[ "message" ], 3000 );
			} else {
				// Success Callback
				ToastUtil.toast( "${ _strings.success_generic_message }", 3000 );
				setTimeout( function () { window.location.reload(); }, 3000 );
			}
		}
	};

    if( !isEmpty( this.pratilipiObj ) ) {
        
        
        this.openShareModal = function() {
            this.dialog.showModal();
        };
        this.hideShareModal = function() {
            this.dialog.close();
        }; 
        this.toggleLibraryState = function() {
            this.isAddedToLibrary( !this.isAddedToLibrary() );
        };

        this.sendLibraryAjaxRequest = function() {
            this.dataAccessor.addOrRemoveFromLibrary( this.pratilipiId(), this.isAddedToLibrary(), null, this.toggleLibraryState.bind( this ) );            
        };

        this.togglePratilipiState = function() {
        	var state = this.pratilipiObj.pratilipiId();
        	var pratilipi = {
        		"pratilipiId": this.pratilipiId(),
        		"state": state != "PUBLISHED" ? "PUBLISHED" : "DRAFTED"
        	};
        	this.dataAccessor.createOrUpdatePratilipi( pratilipi, function( pratilipi ) {
        		window.location.reload();
        	}, function( error ) {
        		ToastUtil.toast( error.message, 3000 );
        		setTimeout( function () { window.location.reload(); }, 3000 );
        	}); 
        };

        this.addToLibrary = function() {
            if( this.isGuest() ) {
                goToLoginPage();
            } else {
                this.toggleLibraryState();
                this.sendLibraryAjaxRequest();
            }        
        };

        this.getDesktopCoverImageUrl = function( url ) {
            var seperator = getUrlSeperator( url );
            return url + seperator + "width=200";
        };

        this.getMobileCoverImageUrl = function( url ) {
            var seperator = getUrlSeperator( url );
            return url + seperator + "width=150";
        };

        this.formatDate = function( date ) {
            return convertDate( date );
        }; 
        
        this.formatReadCount = function( num ) {
            if( isMobile() ) {
                return num > 999 ? (num/1000).toFixed(1) + 'k' : num;
            } else {
              return num.toLocaleString('en-US');
            }
        };
        
        this.roundOffToOneDecimal = function( number ) {
            return Math.round( number * 10 ) / 10;
        }; 

        this.getInitialLibState = function() {
            if ( this.isGuest() ) 
                return false;
            else
                // return this.userPratilipihardcoded.addedToLib; /* change before pushing */
                return this.userPratilipiObj.addedToLib();
        }        

        this.initializePratilipiData = function() {
            this.isGuest = ko.observable( appViewModel.user.isGuest() );
            this.pratilipiId( this.pratilipiObj.pratilipiId() );
            this.author_id = ko.observable( this.pratilipiObj.author.authorId() );
            this.isAddedToLibrary = ko.observable( this.getInitialLibState() ); 

            this.libraryText = ko.computed( function() {
                return this.isAddedToLibrary() ? "- ${ _strings.library }" : "+ ${ _strings.library }";
            }, this); 
            
            this.isSummaryPresent = ko.computed( function() {
                return this.pratilipiObj.summary && this.pratilipiObj.summary().trim().length;
            }, this); 

            this.dialog = document.querySelector( '#pratilipi-share-dialog' );
            if ( !this.dialog.showModal ) {
                dialogPolyfill.registerDialog( this.dialog );
            }                                            
        };

        this.pushToAuthorViewModel = function( author, userAuthor ) {
            // this.author_id( author["authorId"] ).author_name( author["fullName"] ).author_page_url( author["pageUrl"] ).author_profile_image_url( author["imageUrl"] ).author_summary( author["summary"] );
            // this.isUserFollowing( userAuthor["following"] );
            ko.mapping.fromJS( author, {}, this.authorObj );
            ko.mapping.fromJS( userAuthor, {}, this.userAuthorObj );
            console.log( "authors data", this.authorObj, this.userAuthorObj );
            this.initializeAuthorData();
        };      

        this.fetchAuthorAndUserAuthor = function() {
            this.dataAccessor.getAuthorById( this.author_id() , true, function( author, userAuthor ) {
                self.pushToAuthorViewModel( author, userAuthor );
            } );
        };

        this.getInitialFollowingState = function() {
            if ( this.isGuest() ) 
                return false;
            else
                // return userAuthorhardcoded.addedToLib; /* change before pushing */
                return this.userAuthorObj.following();
        };

        this.toggleFollowingState = function() {
            this.isUserFollowing( !this.isUserFollowing() );
        };

        this.sendFollowAuthorAjaxRequest = function() {
            this.dataAccessor.followOrUnfollowAuthor( this.author_id(), this.isUserFollowing(), null, this.toggleFollowingState.bind( this ) );            
        };       

        this.followAuthor = function() {
            if( this.isGuest() ) {
                goToLoginPage();
            } else {
                this.toggleFollowingState();
                this.sendFollowAuthorAjaxRequest();
            }
        }; 

        this.getImageThumbnail = function( url ) {
            var seperator = getUrlSeperator( url );
            return url + seperator + "width=40";
        }; 

        this.initializeAuthorData = function() {
            this.isUserFollowing( this.getInitialFollowingState() );

 
            // this.followIconText = ko.computed( function() {
            //     return this.isUserFollowing() ? "done" : "person_add";
            // }, this);              
        };        
        


        this.init = function() {
            this.dataAccessor = new DataAccessor();
            this.initializePratilipiData();
            this.fetchAuthorAndUserAuthor();
            // this.fetchAuthorAndUserAuthor();
            // this.fetchPratilipiAndUserPratilipi();
        }

        this.init();
    }
    // this.initializePratilipiData = function() {
    //     this.id = ko.observable();
    //     this.isGuest = ko.observable( appViewModel.user.isGuest() ); 
    //     this.title = ko.observable();
    //     this.titleEn = ko.observable();
    //     this.coverImageUrlDesktop = ko.observable();
    //     this.coverImageUrlMobile = ko.observable();
    //     this.language = ko.observable();
    //     this.summary = ko.observable();
    //     this.state = ko.observable();
    //     this.publish_date = ko.observable();
    //     this.averageRating = ko.observable();
    //     this.ratingCount = ko.observable();
    //     this.readCount = ko.observable();
    //     this.type = ko.observable();
    //     this.pageUrl = ko.observable();
    //     this.isAddedToLibrary = ko.observable( false );
        
    //     // this.isFollowRequestInProgress = ko.observable( false );
    //     // this.isAddToLibraryRequestInProgress = ko.observable( false );

    //     this.libraryText = ko.computed( function() {
    //         return this.isAddedToLibrary() ? "- Library" : "+ Library";
    //     }, this);

    //     this.isSummaryPresent = ko.computed( function() {
    //         return this.summary() && this.summary().trim().length;
    //     }, this);

        
    //     this.dialog = document.querySelector( '#pratilipi-share-dialog' );
    //     if ( !this.dialog.showModal ) {
    //         dialogPolyfill.registerDialog( this.dialog );
    //     }
    // }
    
    // this.formatDate = function( date ) {
    //     return convertDate( date );
    // };

    // this.initializeAuthorData = function() {
    //     this.isUserFollowing = ko.observable( false );
    //     this.author_name = ko.observable();
    //     this.author_id = ko.observable( this.pratilipiObj.author.authorId() );
    //     this.author_page_url = ko.observable();
    //     this.author_profile_image_url = ko.observable();
    //     this.author_summary = ko.observable();
    //     this.followText = ko.computed( function() {
    //         return this.isUserFollowing() ? "Unfollow" : "Follow";
    //     }, this); 
    //     this.followIconText = ko.computed( function() {
    //         return this.isUserFollowing() ? "done" : "person_add";
    //     }, this);              
    // };

    
    // this.pushToPratilipiViewModel = function( pratilipi, userpratilipi ) {
    //     this.id( pratilipi["pratilipiId"] ).title(pratilipi["title"]).titleEn(pratilipi["titleEn"]).author_name(pratilipi.author.name).author_id(pratilipi.author.authorId).language(pratilipi["language"]).summary(pratilipi["summary"]).pageUrl(pratilipi["pageUrl"]).state(pratilipi["state"]).publish_date( convertDate( pratilipi["listingDateMillis"] ) ).averageRating( this.roundOffToOneDecimal( pratilipi["averageRating"] ) ).ratingCount( pratilipi["ratingCount"] ).readCount(pratilipi["readCount"]).type(pratilipi["type"]);
    //     this.coverImageUrlDesktop( pratilipi["coverImageUrl"] + "&width=200" ).coverImageUrlMobile( pratilipi["coverImageUrl"] + "&width=150" );
    //     this.isAddedToLibrary( userpratilipi[ "addedToLib" ] );
    // };

    // this.pushToAuthorViewModel = function( author, userAuthor ) {
    //     this.author_id( author["authorId"] ).author_name( author["fullName"] ).author_page_url( author["pageUrl"] ).author_profile_image_url( author["imageUrl"] ).author_summary( author["summary"] );
    //     this.isUserFollowing( userAuthor["following"] );
    // }; 
       
    // var this = this;

    // this.fetchPratilipiAndUserPratilipi = function() {
    //     var dataAccessor = new DataAccessor();
    //     dataAccessor.getPratilipiByUri( "/jitesh-donga/vishwamanav", true,
    //     function( pratilipi, userpratilipi ) {
    //         this.pushToPratilipiViewModel( pratilipi, userPratilipihardcoded );
    //         this.fetchAuthorAndUserAuthor();
    //     });
    // };

    // this.fetchAuthorAndUserAuthor = function() {
    //     var dataAccessor = new DataAccessor();
    //     dataAccessor.getAuthorById( this.author_id() , true, function( author, userAuthor ) {
    //         this.pushToAuthorViewModel( author, userAuthorhardcoded );
    //     } );
    // };    
    
    // this.followAuthor = function() {
    //     if( this.isGuest() ) {
    //         goToLoginPage();
    //     } else {
    //         this.toggleFollowingState();
    //         this.sendFollowAuthorAjaxRequest();
    //     }
    // };

    // this.addToLibrary = function() {
    //     if( this.isGuest() ) {
    //         goToLoginPage();
    //     } else {
    //         this.toggleLibraryState();
    //         this.sendLibraryAjaxRequest();
    //     }        
    // };

    // this.toggleLibraryState = function() {
    //     this.isAddedToLibrary( !this.isAddedToLibrary() );
    // };

    // this.toggleFollowingState = function() {
    //     this.isUserFollowing( !this.isUserFollowing() );
    // };

    // this.sendLibraryAjaxRequest = function() {
    //     $.ajax({
    //         type: 'post',
    // //        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ this.id,
    //         url: '/api/userpratilipi/library',
    //         data: {
    //             pratilipiId: this.id(),
    //             addedToLib: this.isAddedToLibrary()
    //         },
    //         success: function( response ) {
    //         },
    //         error: function( response ) {
    //             this.toggleLibraryState();
    //             console.log( response );
    //             console.log( typeof(response) );
    //         },
    //         complete: function() {
    //             // this.isAddToLibraryRequestInProgress( false );
    //             /* populate button text acc to the response */
    //         }
    //     });              
    // };      
    
    // this.sendFollowAuthorAjaxRequest = function() {
    //     $.ajax({
    //         type: 'post',
    // //        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ this.id,
    //         url: '/api/userauthor/follow',
    //         data: {
    //             authorId: this.author_id,
    //             following: this.isUserFollowing()
    //         },
    //         success: function( response ) {
    //         },
    //         error: function( response ) {
    //             this.toggleFollowingState();
    //         },
    //         complete: function() {
    //             // this.isFollowRequestInProgress( false );
    //             /* populate button text acc to the response */
    //         }
    //     });              
    // };
    
    // this.getShareUrl = function( share_platform ) {
    //     var share_url = encodeURIComponent( window.location.origin + this.pageUrl() );
    //     if( share_platform == "Whatsapp" ) {          
    //         var text = "%22" + this.title() + "%22${ _strings.whatsapp_read_story }%20" + share_url +"%0A${ _strings.whatsapp_read_unlimited_stories }";
    //         return text; /* test if message goes correctly in whatsapp */
    //     } else {
    //         return share_url;
    //     }
    // };
    
    // this.shareOnSocialMedia = function( item, event ) {
    //     var element = event.currentTarget;
    //     var share_platform = element.getAttribute('data-share-platform');
    //     var shareUrl = this.getShareUrl( share_platform );
    //     var functionToInvoke = "sharePratilipiOn" + share_platform;
    //     window[ functionToInvoke ]( shareUrl );        /* check later if its supported in older browsers */
    //     this.hideShareModal();
    // };
    
    // this.openShareModal = function() {
    //     this.dialog.showModal();
    // };
    // this.hideShareModal = function() {
    //     this.dialog.close();
    // };   
    
    // this.roundOffToOneDecimal = function( number ) {
    //     return Math.round( number * 10 ) / 10;
    // };
    
    // this.init = function() {
    //     // this.initializePratilipiData();
    //     this.initializeAuthorData();
    //     this.fetchAuthorAndUserAuthor();
    //     // this.fetchPratilipiAndUserPratilipi();
    // }
    
        // this.init();

    
    
}
