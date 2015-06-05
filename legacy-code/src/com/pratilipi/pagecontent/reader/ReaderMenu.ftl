<#macro menu indexList bookmarkList>
	
	<div id="Pratilipi-Reader-Basic-ContentTable-Button" class="PratilipiContent-ReaderBasic-button" onclick="showIndexDiv( event )">
		<img src="/theme.pratilipi/images/buttons/menu-white.png" title="Table of Content">
	</div>
	
	<div id="Pratilipi-Reader-Basic-ContentTable" class="tabs">
	    <ul class="tab-links">
	        <li class="active"><a href="#content">Content</a></li>
	        <li><a href="#bookmarks">Bookmark</a></li>
	    </ul>
	    <div class="tab-content">
	        <div id="content" class="tab active"></div>
	        <div id="bookmarks" class="tab"></div>
	    </div>
	</div>
	
	<style type="text/css">
	 	
	 	#Pratilipi-Reader-Basic-ContentTable-Button {
	 		display: none;
	 	}
	 	
		#Pratilipi-Reader-Basic-ContentTable{
			display: none;
			width:300px;
			height: 90%;
		    z-index:2;
		    float: right;
		    position:absolute;
		    right:-250px;
		    opacity: 1;
		    background-color: #D8D8D8;
		    overflow-y: scroll;
		    padding: 5px;
		    box-shadow:-1px 1px 1px grey;
		}
		
		.tabs {
		    width:100%;
		    display:inline-block;
		}
		
		/*----- Tab Links -----*/
	    /* Clearfix */
	    .tab-links {
	    	padding : 0px;
	    	float: left;
	    	position: relative;
	    }
	    
	    .tab-links:after {
	        display:block;
	        clear:both;
	        content:'';
	    }
	 
	    .tab-links li {
	        margin:0px;
	        float:left;
	        list-style:none;
	    }
	 
        .tab-links a {
            padding:9px 15px;
            display:inline-block;
            border-radius:3px 3px 0px 0px;
            background:#888888;
            color: #fff;
            font-size:16px;
            transition:all linear 0.15s;
        }
 
        .tab-links a:hover {
            background:#B0B0B0;
            text-decoration:none;
        }
	 
	    li.active a, li.active a:hover {
	        background:#fff;
	        color:#4c4c4c;
	        cursor: default;
	        text-decoration: none;
	    }
	    
	    /*----- Content of Tabs -----*/
	    .tab-content {
	    	top: 45px;
	        padding:5px;
	        box-shadow:-1px 1px 1px rgba(0,0,0,0.15);
	        background:#fff;
	        color: #000;
	        width: 98%;
	        position: absolute;
	    }
	 
        .tab {
            display:none;
        }
 
        .tab.active {
            display:block;
            cursor: default;
        }
		
		.menuItem {
			padding-left: 10px;
			cursor: pointer;
		}
		
		.menuItem:hover {
			background-color: #CCC;
		}
		
		.indexItem {
			padding: 10px;
			padding-left: 20px;
		}
		
		.indexSubItem {
			padding-left: 30px;
		}

		.selectedIndex {
			background-color: #DDD;
			color: green;
		}
		
		
	</style>
	
	<script language="javascript">
		/* CONTENT DIV FUNCTIONS */
		function showIndexDiv( event ){
			var event = event || window.event;
			event.stopPropagation();
		    var hidden = $('#Pratilipi-Reader-Basic-ContentTable');
		    if (hidden.hasClass('visible')){
		        hidden.animate({"right":"-250px"}, "slow", function(){ hidden.css( "display", "none" ); }).removeClass( 'visible' );
		    } else {
		    	hidden.css( "display", "block" );
		        hidden.animate({"right":"0px"}, "slow").addClass( 'visible' );
		    }
		}
		
		function setContentDiv( index ){
			var contentTableDiv = jQuery( "#content" );
			for( var i = 0; i < index.length; i++ ){
				var div = document.createElement( "div" );
				if( index[i].title != '' && index[i].pageNo != '' && index[i].level == 1 ){
					var item = "<div class='menuItem indexItem indexSubItem' data-pageNo=" + index[i].pageNo + " onclick='indexClicked(" + index[i].pageNo + ");'>" + 
									index[i].title + 
									"</div>";
					contentTableDiv.append( item ); 
				}
				
				if( index[i].title != '' && index[i].pageNo != '' ){
					var item = "<div class='menuItem indexItem' data-pageNo=" + index[i].pageNo + " onclick='indexClicked(" + index[i].pageNo + ");'>" + 
									index[i].title + 
									"</div>";
					contentTableDiv.append( item ); 
				}
			}
			
			markSelectedIndex();
		}
		
		function setBookmarkDiv( bookmarkList ){
			var bookmarksDiv = jQuery( "#bookmarks" );
			bookmarksDiv.empty();
			for( var i = 0; i < bookmarkList.length; i++ ){
				var div = document.createElement( "div" );
								
				if( bookmarkList[i].title != '' && bookmarkList[i].pageNo != '' ){
					var item = "<div class='menuItem indexItem' data-pageNo=" + bookmarkList[i].pageNo + " onclick='indexClicked(" + bookmarkList[i].pageNo + ");'>" + 
									bookmarkList[i].title + 
									"</div>";
					bookmarksDiv.append( item ); 
				}
			}
		}
		
		function markSelectedIndex(){
			jQuery( "#Pratilipi-Reader-Basic-ContentTable .menuItem" ).each( function(){
				if( jQuery( this ).attr( "data-pageNo" ) == pageNo ){
					jQuery( this ).addClass( "selectedIndex" );
				} else
					jQuery( this ).removeClass( "selectedIndex" );
			});
		}
		
		function indexClicked( pageNumber ){
			if( pageNumber != pageNo && pageNumber <= pageCount ){
				recordPageTime();
				pageNo = parseInt( pageNumber );
				pageNoDisplayed = 0;
				updateDisplay();
			}
		}

		
		jQuery(document).ready(function() {
		    jQuery('.tabs .tab-links a').on('click', function(e)  {
		        var currentAttrValue = jQuery(this).attr('href');
		 
		        // Show/Hide Tabs
		        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
		 
		        // Change/remove current tab to active
		        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
		 		e.stopPropagation();
		        e.preventDefault();
		    });
		});
		
		function showMenuButton( indexList, bookmarkList ){
			var menuButton = document.getElementById( 'Pratilipi-Reader-Basic-ContentTable-Button' );
			
			if( bookmarkList.length > 0 || indexList.length > 0 )
				menuButton.style.display = "inline";
			else
				menuButton.style.display = "none";
		}
		
		if( window.attachEvent) {//for IE8 and below
			window.attachEvent( 'onload', function( event ){
				showMenuButton( ${ indexList }, ${ bookmarkList } );
				setContentDiv( ${ indexList } );
				setBookmarkDiv( ${ bookmarkList } );
			});
		}
		else {
			window.addEventListener( 'load', function( event ){
				showMenuButton( ${ indexList }, ${ bookmarkList } );
				setContentDiv( ${ indexList } );
				setBookmarkDiv( ${ bookmarkList } );
			});
		}
	</script>
</#macro>