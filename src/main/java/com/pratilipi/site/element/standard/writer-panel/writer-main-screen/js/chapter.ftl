var Chapter = function (ch_object, writer_panel_object) {
    this.chapterNo = ch_object.chapterNo;
    this.name = ch_object.title ? ch_object.title : "${ _strings.writer_chapter } " + this.chapterNo;
    this.$ListDomElement = null;
    this.writer_panel_object = writer_panel_object;
};

Chapter.prototype.getListDomElement = function () {
	var _this = this;
    if ( this.$ListDomElement ) {
        return this.$ListDomElement;
    }

    this.$ListDomElement = $("<li>").addClass("toc_chapter_item");
    this.$name = $("<a>", {
        href: "#"
    }).text(this.name);

    var $delete = $("<img>", {
        "class": "pull-right",
        "data-behaviour": "delete-chapter",
         src: "http://0.ptlp.co/resource-all/icon/svg/trash.svg"
    }).data("relatedObject", this).css({width: "20px", height: "20px"});
    
    //$delete.on("click", function(e) {
    	//e.stopPropagation();
    	//_this.writer_panel_object.removeChapter( _this.chapterNo );
    //});

    this.$name.append( $delete );
    this.$ListDomElement.append(this.$name);
    this.$ListDomElement.on('click', function(e) {
    	//make sure it doesnt fire on delete
    	if( $(e.target).data("behaviour") != "delete-chapter" ) {
    		_this.writer_panel_object.setCurrentPage( _this.chapterNo );
    	}
    });

    return this.$ListDomElement;        

};

Chapter.prototype.changeName = function( title ) {
	this.name = title ? title : "${ _strings.writer_chapter } " + this.chapterNo;
	this.$name.get(0).firstChild.nodeValue = this.name;
};

Chapter.prototype.setActive = function( ) {
	this.$name.addClass('current-chapter');
};