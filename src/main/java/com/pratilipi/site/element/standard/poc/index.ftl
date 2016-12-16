<!DOCTYPE html>
<html lang="${ lang }">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
        <title>Knockout Test</title>

        <script src="http://0.ptlp.co/resource-all/jquery.knockout.boostrap.js" type="text/javascript"></script>
        <link rel='stylesheet' href='http://1.ptlp.co/third-party/bootstrap-3.3.4/css/bootstrap.min.css'>

        <style>
            html, body {
                padding: 0;
                margin: 0;
                width: 100%;
            }
            .header {
                height: 60px;
                min-height: 60px;
                width: 100%;
                position: fixed;
                background: #f5f5f5;
                border-bottom: 1px solid #d3d3d3;
                z-index: 1;
            }
            .pratilipi-icon {
                width: 48px;
                height: 48px;
                margin-left: 4px;
                margin-top: 8px;
                float: left;
            }
            .container {
                width: 100%;
                padding-top: 68px;
                margin-bottom: 10px;
                padding-left: 8px;
                padding-right: 8px;
            }
            .navigation-bar {
                margin-right: 12px;
                background: #f5f5f5;
                padding: 20px 32px;
                margin-bottom: 12px;
                border: 1px solid #d3d3d3;
            }
            .navigation-title {
                color: #d0021b;
                font-weight: 700;
                font-size: 18px;
            }
            .navigation-link {
                display: block;
                color: #333;
                line-height: 24px;
                text-decoration: none;
            }
            .main-content {
                overflow:hidden;
                padding: 0 24px;
                background: #f5f5f5;
                border: 1px solid #d3d3d3;
            }
            .section-title {
                border-bottom: 1px solid #333;
            }
            .section-title a {
                color: #d0021b;
                text-decoration: none;
            }
            .footer {
                clear: both;
                width: 100%;
                background: #f5f5f5;
                padding: 20px 0;
				border-top: 1px solid #d3d3d3;
            }

            .pratilipi-card {
                margin-bottom: 20px;
            }
            .pratilipi-card .image-holder {
                float: left;
                width: 100px;
                height: 150px;
            }
            .pratilipi-card .info-holder {
                margin-left: 8px;
                float: left;
                width: calc( 100% - 120px );
                overflow: hidden;
            }
            .pratilipi-card .info-holder .pratilipi-title {
                margin: 0;
                margin-top: 8px;
            }
            .pratilipi-card .info-holder .pratilipi-title a {
                text-decoration: none;
                color: #333;
            }
            .pratilipi-card .info-holder .author-name {
                margin: 0;
                margin-top: 8px;
            }
            .pratilipi-card .info-holder .author-name a {
                text-decoration: none;
                color: #333;
            }
            .pratilipi-card .info-holder .meta-info {
                margin-top: 4px;
                margin-bottom: 16px;
            }
            .pratilipi-card .info-holder .read-button {
                background: #F1F8FB;
                border: 1px solid #0C68BD;
                box-shadow: 0px 1px 1px 0px rgba(0,0,0,0.50);
                border-radius: 2px;
                font-size: 13px;
                color: #0C68BD;
                letter-spacing: 0.3px;
                line-height: 16px;
                text-shadow: 0px 1px 2px #FFFFFF;
                display: inline-block;
                padding: 10px 15px 10px 15px;
                margin-right: 10px;
                outline: none;
                text-decoration: none;
            }
            
            .carousel {
                max-width: 1000px;
                margin: 12px auto;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <a href="/">
                <img class="pratilipi-icon" title="Pratilipi" alt="Pratilipi" src="http://0.ptlp.co/resource-ta/logo/pratilipi_logo.png" />
            </a>
        </div>
        <div class="container">
            <div data-bind="foreach: { data: navigationList, as: 'navigation' }" class="navigation-bar pull-left hidden-xs hidden-sm">
                <div class="navigation-title" data-bind="text: title"></div>
                <div data-bind="foreach: {data: linkList, as: 'link'}">
                    <a class="navigation-link" data-bind="attr: {href: url,title: name}, text: name"></a>
                </div>
                <br/>
            </div>
            

            <div class="main-content">
                <div id="carousel" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carousel" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel" data-slide-to="1"></li>
                        <li data-target="#carousel" data-slide-to="2"></li>
                        <li data-target="#carousel" data-slide-to="3"></li>
                        <li data-target="#carousel" data-slide-to="4"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="item active">
                            <a href="/event/ore-oru-oorla" target="_blank"><img src="http://4.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-19.jpg" /></a>
                        </div>
                        <div class="item">
                            <a on-click="write"><img src="http://0.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-16.jpg" /></a>
                        </div>
                        <div class="item">
                            <a href="/horror" target="_blank"><img src="http://1.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-17.jpg" /></a>
                        </div>
                        <div class="item">
                            <a href="/education" target="_blank"><img src="http://2.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-18.jpg" /></a>
                        </div>
                        <div class="item">
                            <a href="/fiveminstories" target="_blank"><img src="http://3.ptlp.co/resource-ta/home-page-banner/pratilipi-tamil-carousel-13.jpg" /></a>
                        </div>
                    </div> 
                    <a href="#carousel" class="left carousel-control" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </a>
                    <a href="#carousel" class="right carousel-control" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </a>
                </div>
                <div class="loading-spinner" data-bind="visible: sectionList().length == 0">Loading Data...</div>
                <div data-bind="foreach: {data: sectionList, as: 'section'}, visible: sectionList().length > 0">                
                    <h3 class="section-title"><a data-bind="text: title, attr: {href:listPageUrl, target:'_blank'}"></a></h3>
                    <div class="row" data-bind="foreach: {data: pratilipiList, as: 'pratilipi'}">
                        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-4 pratilipi-card">
                            <div class="image-holder">
                                <a data-bind="attr: {href:pageUrl}">
                                    <img width="100" height="150" data-bind="attr: {src: coverImageUrl+'&width=150'}" />
                                </a>
                            </div>
                            <div class="info-holder">
                                <h4 class="pratilipi-title"><a data-bind="attr: {href:pageUrl,title:title}, text: title"></a></h4>
                                <h5 class="author-name"><a data-bind="attr: {href:author.pageUrl,title:author.name,target:'_blank'}, text: author.name"></a></h4>
                                <div class="meta-info">
                                    <div data-bind="visible: averageRating > 0"><span data-bind="text: averageRating.toFixed(1)"></span>&nbsp;Stars</div>
                                </div>
                                <a data-bind="attr: {href:readPageUrl}" class="read-button">Read</a>
                            </div>
                        </div>
                    </div>
                    <div style="min-height: 20px;">&nbsp;</div>
                </div>
            </div>            
        </div>
        <div class="footer">
            Footer
        </div>


     <script type="application/javascript">
        $(function(){
            var ViewModel = {
                user: ${ userJson },
                pratilipiTypes: ${ pratilipiTypesJson },
                navigationList: ${ navigationListJson },
                languageMap: ${ languageMap },
                sectionList: ko.observableArray([]),
                pushToSectionList: function(sectionList){for(var i=0;i<sectionList.length;i++)this.sectionList.push(sectionList[i]);}
            };
            
            ko.applyBindings( ViewModel );
            $( '.carousel' ).carousel();
            $.ajax({
                type: 'get',
                url: '/api/init?_apiVer=2',
                data: { 
                    'language': "${ language }"
                },
                success: function( response ) {
                	var res = jQuery.parseJSON( response );
					ViewModel.pushToSectionList(res["sections"]);
                },
                error: function( response ) {
                    console.log( response );
                	console.log( typeof(response) );
                }
            });
        });
    </script>

  
  </body>
</html>

