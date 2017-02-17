function() {    
    this.initializeData = function() {
        this.id = getQueryVariable("id");
        this.navigationList = ko.observableArray([]);
        this.title = ko.observable();
        this.titleEn = ko.observable();
        this.coverImageUrl = ko.observable();
        this.author_name = ko.observable();
        this.language = ko.observable();
        this.summary = ko.observable();
        this.state = ko.observable();
        this.publish_date = ko.observable();
        this.averageRating = ko.observable();
        this.ratingCount = ko.observable();
        this.readCount = ko.observable();
        this.type = ko.observable();
    }

    
    this.pushToViewModel = function( data ) {
        this.title(data["title"]).titleEn(data["titleEn"]).coverImageUrl( data["coverImageUrl"] ).author_name(data.author.name).language(data["language"]).summary(data["summary"]).state(data["state"]).publish_date(data["listingDateMillis"]).averageRating(data["averageRating"]).ratingCount(data["ratingCount"]).readCount(data["readCount"]).type(data["type"]);
    }
    var self = this;
    this.getData = function() {
      $.ajax({
        type: 'get',
//        url: '/api/pratilipi?_apiVer=2&pratilipiId='+ self.id,
        url: '<#if stage == "alpha">${ prefix }</#if>/api/pratilipi?_apiVer=2&pratilipiId=' + self.id,
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
    
    this.init = function() {
      this.initializeData();
      this.getData();
    }
    
    this.init();
}