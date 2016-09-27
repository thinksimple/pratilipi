var Pagination = function(pagination_container, parent_object) {
	this.parent_object = parent_object;
    this.$pagination_container = pagination_container;
    this.$previous_page = this.$pagination_container.find("[data-behaviour=previous-page]");
    this.$next_page = this.$pagination_container.find("[data-behaviour=next-page]");
    this.$page_info_para = this.$pagination_container.find("[data-behaviour=page-info]");
    this.$curr_page_input = this.$pagination_container.find("[data-behaviour=curr_page]");
    this.$progress_line = this.$pagination_container.find(".progress-bar");
    this.$progress_ball = this.$pagination_container.find("[data-behaviour=progress-ball]");
}

Pagination.prototype.init = function () {
    //this.attachPreviousPageListener();
    //this.attachNextPageListener();
}

Pagination.prototype.attachPreviousPageListener = function() {
	var _this = this;
};

Pagination.prototype.attachNextPageListener = function() {
};

Pagination.prototype.setProgressPage = function() {
	if( this.parent_object.index.length == 1 ) {
		this.$pagination_container.hide();
	}
	
	else {
		this.$pagination_container.show();
		this.setCurrentPageNo();
		this.setTotalPageNo();
		this.setProgressBar();
	}
};

Pagination.prototype.setCurrentPageNo = function() {
	this.$curr_page_input.val( this.parent_object.currPage );
};

Pagination.prototype.setTotalPageNo = function() {
	this.$page_info_para.get(0).lastChild.nodeValue = ( "/" + this.parent_object.index.length );
};

Pagination.prototype.setProgressBar = function() {
	var progress = ( this.parent_object.currPage /  this.parent_object.index.length ) ; 
	this.$progress_line.css("width", progress + "%");
	this.$progress_ball.css("left", progress + "%");
};