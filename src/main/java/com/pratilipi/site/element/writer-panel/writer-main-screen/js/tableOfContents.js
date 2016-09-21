var TableOfContents = function(toc_container) {
    this.toc_container = toc_container;
    this.$dropdown_menu_list = toc_container.find(".dropdown-menu");
    this.chapters = [];
    console.log(this.chapters);

}

TableOfContents.prototype.init = function () {
    this.setListWidth();
    this.populateChapters();
    this.addNewChapterButton();
}

TableOfContents.prototype.setListWidth = function() {
    // setting height of dropdown ul equal to height of the trigger div
    this.$dropdown_menu_list.width( this.toc_container.find("#table_of_contents_dropdown").width() - 2);
};

TableOfContents.prototype.populateChapters = function() {
    var _this = this;
    console.log(this);
    this.ajaxChaptersList = [{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"},{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"},{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"},{ name: "Untitled 1"},{ name: "Untitled 2"},{ name: "Untitled 3"}    ];
    $.each(this.ajaxChaptersList, function(index, chapter) {
        var ch = new Chapter( chapter );
        _this.chapters.push(ch);
        _this.$dropdown_menu_list.append( ch.getListDomElement() );
    });
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