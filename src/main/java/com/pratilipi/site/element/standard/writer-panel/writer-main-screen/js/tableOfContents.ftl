var TableOfContents = function(toc_container, parent_object) {
	this.parent_object = parent_object;
    this.toc_container = toc_container;
    this.$dropdown_menu_list = toc_container.find(".dropdown-menu");
    this.$book_name_container = toc_container.find("#book_name_toc");
    this.chapters = [];
    console.log(this.chapters);
    var pratilipi_data = ${ pratilipiJson };
    this.book_name = pratilipi_data.title ? pratilipi_data.title : pratilipi_data.titleEn;
    //console.log( this.book_name );

}

TableOfContents.prototype.init = function () {
	this.changeName( this.book_name );
    this.setListWidth();
    //this.populateChapters();
    //this.addNewChapterButton();
}

TableOfContents.prototype.changeName = function( name ) {
	this.book_name = name;
	this.$book_name_container.text( name );
};

TableOfContents.prototype.setListWidth = function() {
    // setting height of dropdown ul equal to height of the trigger div
    this.$dropdown_menu_list.width( this.toc_container.find("#table_of_contents_dropdown").width() - 2);
};

TableOfContents.prototype.populateIndex = function( index ) {
    var _this = this;
    this.emptyChaptersList();
    this.chaptersList = index["index"];
    $.each(this.chaptersList, function(index, chapter) {
        var ch = new Chapter( chapter );
        _this.chapters.push(ch);
        _this.$dropdown_menu_list.append( ch.getListDomElement() );
    });
};

TableOfContents.prototype.emptyChaptersList = function() {
	    this.chapters = [];
	    this.$dropdown_menu_list.find(".toc_chapter_item").remove();
};

TableOfContents.prototype.addNewChapterButton = function() {
    var $divider = $("<li>", {
        role: "separator",
        "class": "divider"
    });

    var $boldNewChapter = $("<strong>", {
        "class": "pratilipi-red"
    })
    .text(" + New Chapter");
    var $aNewChapter = $("<a>", {
        href: "#", 
    }).append( $boldNewChapter );
    var $liNewChapter = $("<li>", {
        "class": "text-center"
    }).append( $aNewChapter );

    this.$dropdown_menu_list.append( $divider ).append( $liNewChapter );
};