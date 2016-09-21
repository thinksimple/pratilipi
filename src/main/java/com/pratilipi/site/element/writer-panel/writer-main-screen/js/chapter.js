var Chapter = function (ch_object) {
    this.name = ch_object.name;
    this.$ListDomElement = null;
};

Chapter.prototype.getListDomElement = function () {
    if ( this.$ListDomElement ) {
        return this.$ListDomElement;
    }

    this.$ListDomElement = $("<li>");
    var $name = $("<a>", {
        href: "#"
    }).text(this.name);

    var $delete = $("<img>", {
        "class": "pull-right",
         src: "http://0.ptlp.co/resource-all/icon/svg/trash.svg"
    }).data("relatedObject", this).css({width: "20px", height: "20px"});

    $name.append( $delete );
    this.$ListDomElement.append($name)
    // console.log(this.$ListDomElement);
    return this.$ListDomElement;        

};