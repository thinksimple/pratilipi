var TableOfContents = function(toc_container, parent_object) {
	this.parent_object = parent_object;
    this.toc_container = toc_container;
    this.$dropdown_menu_list = toc_container.find(".dropdown-menu");
    this.$book_name_container = toc_container.find("#book_name_toc");
    this.$chapters_separator_second = this.$dropdown_menu_list.find("[data-behaviour=chapters_separator_second]");
    this.chapters = [];
    console.log(this.chapters);
    var pratilipi_data = ${ pratilipiJson };
    this.book_name = pratilipi_data.title ? pratilipi_data.title : pratilipi_data.titleEn;
    this.$new_chapter_button = this.$dropdown_menu_list.find("[data-behaviour=new_chapter]");
    //console.log( this.book_name );

}

TableOfContents.prototype.init = function () {
	this.changeName( this.book_name );
    this.setListWidth();
    //this.populateChapters();
    this.attachNewChapterListener();
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
    this.chaptersList = index;
    console.log( index );
    $.each(this.chaptersList, function(index, chapter) {
        var ch = new Chapter( chapter, _this.parent_object );
        _this.chapters.push(ch);
        ch.getListDomElement().insertBefore( _this.$chapters_separator_second );
    });
};

TableOfContents.prototype.emptyChaptersList = function() {
	this.chapters = [];
	this.$dropdown_menu_list.find(".toc_chapter_item").remove();
};

TableOfContents.prototype.attachNewChapterListener = function() {
	var _this = this;
	this.$new_chapter_button.on('click', function(e) {
		e.preventDefault();
		_this.parent_object.addNewChapter( _this.parent_object.currChapter );
	});
};

TableOfContents.prototype.changeCurrentChapterName = function( currChapter, title ) {
	this.chapters[ currChapter - 1 ].changeName( title );
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