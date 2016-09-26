var Chapter = function (ch_object, writer_panel_object) {
    this.chapterNo = ch_object.chapterNo;
    this.name = ch_object.chapterTitle ? ch_object.chapterTitle : "Chapter " + this.chapterNo;
    this.$ListDomElement = null;
    this.writer_panel_object = writer_panel_object;
};

Chapter.prototype.getListDomElement = function () {
	var _this = this;
    if ( this.$ListDomElement ) {
        return this.$ListDomElement;
    }

    this.$ListDomElement = $("<li>").addClass("toc_chapter_item");
    var $name = $("<a>", {
        href: "#"
    }).text(this.name);

    var $delete = $("<img>", {
        "class": "pull-right",
         src: "http://0.ptlp.co/resource-all/icon/svg/trash.svg"
    }).data("relatedObject", this).css({width: "20px", height: "20px"});
    
    $delete.on("click", function() {
    	_this.writer_panel_object.removeChapter( _this.chapterNo );
    });

    $name.append( $delete );
    this.$ListDomElement.append($name)
    // console.log(this.$ListDomElement);
    return this.$ListDomElement;        

};