<!DOCTYPE html>
<html>
<head>
	<script src="https://code.jquery.com/jquery-3.1.0.min.js" integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s=" crossorigin="anonymous"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<title>Writer Panel</title>
    <style>
        .horizontal-form-input {
            border: none;
            box-shadow: none;
            border-bottom: 1px solid black;
            border-radius: 0px;
            /*color: #a5a0a0;*/
        }
        .horizontal-form-input:hover, .horizontal-form-input:focus {
            box-shadow: none;
        }
        .left-align-content {
            text-align: left;
        }
        form .form-group {
            margin-bottom: 30px;
            margin-top: 15px;
        }
        .pratilipi-red-button {
            background: #FFFFFF;
            border: 1px solid #d0021b;
            box-shadow: 0px 1px 1px 0px rgba(0, 0, 0, 0.50);
            border-radius: 5px;
            font-size: 13px;
            color: #d0021b;
            letter-spacing: 0.3px;
            line-height: 16px;
            text-shadow: 0px 1px 2px #FFFFFF;
            display: inline-block;
            padding: 10px;
            margin-right: 10px;
            magin-top: 5px;
            outline: none;
        } 
        .pratilipi-red-background-button {
            background: #d0021b !important;
            border: 1px solid #d0021b !important;  /* changed*/
            box-shadow: 0px 1px 1px 0px rgba(0, 0, 0, 0.50);
            border-radius: 5px;
            font-size: 13px;
            color: #ffffff;
            letter-spacing: 0.3px;
            line-height: 16px;
            text-shadow: 0px 1px 2px #FFFFFF;
            display: inline-block;
            padding: 10px;
            margin-right: 10px;
            margin-top: 5px;
            outline: none;
        } 
        .go-button {
            margin-top: 20px;
            padding-left: 20px;
            padding-right: 20px;
            font-size: 20px;
            text-shadow: none;
        } 
        .translucent {
            position: absolute;
            top: 0px;
            left: 0px;
            height: 60px;
            background: #999;
            text-align: center;
            opacity: 0.3;
            transition: opacity .13s ease-out;
            width: 100%;
            -webkit-font-smoothing: antialiased;
            padding-top: 5px;
            background: linear-gradient(black, #f9f9f9);
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
        }
        /* base CSS element */
        div.lefttip, div.righttip {
            position: relative;
        }
        div.lefttip div {
            display: block;
            font-size: 16px;
            position: absolute;
            top: 12%;
            left: 50px;
            padding: 5px;
            z-index: 100;
            background: #ddd;
            color: black;
            -moz-border-radius: 5px; /* this works only in camino/firefox */
            -webkit-border-radius: 5px; /* this is just for Safari */
            text-shadow: none;
        }
        div.righttip div {
            display: block;
            font-size: 16px;
            position: absolute;
            top: 12%;
            right: 50px;
            padding: 5px;
            z-index: 100;
            background: #ddd;
            color: black;
            -moz-border-radius: 5px; /* this works only in camino/firefox */
            -webkit-border-radius: 5px; /* this is just for Safari */
            text-shadow: none;
        }        
        div.lefttip div:before{
            content:'';
            display:block;
            width:0;
            height:0;
            position:absolute;

            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
            border-right:8px solid #ddd;
            left:-8px;

            top:20%;
        }
        div.righttip div:before{
            content:'';
            display:block;
            width:0;
            height:0;
            position:absolute;

            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
            border-left:8px solid #ddd;
            right:-8px;

            top:20%;
        }
        .progress-bar:before{
            content:'';
            display:block;
            width:0;
            height:0;
            position:absolute;

            border-top: 8px solid transparent;
            border-bottom: 8px solid transparent;
            border-left:8px solid #ddd;
            right:-8px;

            top:20%;
        }        
        @media only screen and (max-width: 992px) {
            .book-name {
                text-align: left;
            }
        }        

        /* arrows - :before and :after */
/*        .tip:before {
            position: absolute;
            display: inline-block;
            border-top: 7px solid transparent;
            border-right: 7px solid #eee;
            border-bottom: 7px solid transparent;
            border-right-color: rgba(0, 0, 0, 0.2);
            left: -14px;
            top: 20px;
            content: '';
        }

        .tip:after {
            position: absolute;
            display: inline-block;
            border-top: 6px solid transparent;
            border-right: 6px solid #eee;
            border-bottom: 6px solid transparent;
            left: -12px;
            top: 21px;
            content: '';
        } */  
        .right23 {
            right: -23% !important
        }     
        .left25 {
            left: -25% !important;
        }        
    </style>
</head>
<body>
    <div class="translucent">
    </div>
      <div class="pull-right" style="position: absolute;right: 1%;top: 1%;">
          <button class="pratilipi-red-background-button">Publish</button>
          <button class="btn btn-default" style="margin: 0 5px 0 5px;">Save</button>
          <button class="btn btn-default" style="margin: 0 5px 0 5px;">Preview</button>       
          <a data-container="body" data-toggle="popover" data-content="
  <a href='#'>
    <img src='http://0.ptlp.co/resource-all/icon/svg/trash.svg' style='width:16px;height:16px;'>Delete this content
  </a>" data-html="true" data-placement="bottom" href="#"><img style="width: 58px;height: 25px;" src="http://0.ptlp.co/resource-all/icon/svg/dots-three-vertical.svg"></a>       
      </div>    
    <div class="container">
        <!-- panel -->
        <div class="panel panel-default" style="margin-bottom: 5px;"> 
          <div class="panel-body" style="text-align: center;padding-bottom: 5px;">
            <h1 class="book-name" style="margin-top: 5px;">Book Name</h1>
            <form class="col-sm-10 col-sm-offset-1">
              <div class="form-group" style="position: relative;">
                <input  class="form-control horizontal-form-input" id="subtitle" style="padding-bottom: 25px;padding-top: 20px;font-size: 25px;" placeholder="Add Subtitle">
                <!-- editing panel -->
                <div style="position: absolute;right: 1%;top: 5%;"><div style="background-color: #ddd;padding: 7px;">
  
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/bold.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/italic.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/underline.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    |
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/paragraph-left.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/paragraph-center.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/paragraph-right.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    |
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/quotes-left.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/attachment.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                    <a href="#"><img src='http://0.ptlp.co/resource-all/icon/svg/camera2.svg' style='width:20px;height:20px;  margin: 0 2px 0 2px;'></a>
                </div></div>
              </div>
              <textarea style="border: none; margin-bottom:10px;" class="form-control horizontal-form-input" rows="20" placeholder="Tell your story here.."></textarea>               
            </form>
            <div class="clearfix"></div>
            <!-- writer footer -->
            <div>
                <nav aria-label="..." style="padding-top:10px;" class="col-lg-4 col-lg-offset-4 col-xs-6 col-xs-offset-3">
                  <ul class="pager" style="margin: 0px;">
                    <li class="previous"><a class="pratilipi-red-background-button righttip" style="border-radius: 50%; padding:3px; margin-top: 0px;" data-toggle="tooltip" data-placement="left" title="Delete this Chapter" href="#"><img src="http://0.ptlp.co/resource-all/icon/svg/minus-white.svg"></a></li>
                    <a href="#"><img src="http://0.ptlp.co/resource-all/icon/svg/chevron-left.svg"></a>
                    <p style="display: inline;font-size: 20px;padding: 10px;"><input class="horizontal-form-input" type="text" value="12" style="width: 25px;">/25</p>   
                    <a href="#"><img src="http://0.ptlp.co/resource-all/icon/svg/chevron-right.svg"></a>
                    <li class="next"><a class="pratilipi-red-background-button lefttip"  data-toggle="tooltip" data-placement="right" title="New Chapter" style="border-radius: 50%; padding: 3px;margin-top: 0px;" href="#"><img src="http://0.ptlp.co/resource-all/icon/svg/plus-white.svg"></a></li>
                  </ul>
                </nav>
            </div> 
            <!-- progress bar -->
            <div class="progress" style="height:2px;position: absolute;left: 0;right: 0;">
              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                <span class="sr-only">80% Complete (danger)</span>
              </div>
            </div>
            <img src='http://0.ptlp.co/resource-all/icon/svg/circle-red.svg' style='left: 80%;width:15px;height:15px; position: absolute;margin: 0 2px 0 2px;'>  
            <!-- end of progress bar                             -->
        </div>       
    </div>
</body>

<script type="text/javascript">
    $(function () {
        $('[data-toggle="tooltip"]').tooltip('');
    });
    $(function () {
      $('[data-toggle="popover"]').popover();
    });
</script>
</html>