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
        </style>
    </head>
    <body>
        <div class="header">
            <a href="/">
				<img class="pratilipi-icon" title="${ _strings.pratilipi }" alt="${ _strings.pratilipi }" src="http://0.ptlp.co/resource-${ lang }/logo/pratilipi_logo.png" />
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
            <div data-bind="foreach: {data: sectionList, as: 'section'}" class="main-content">                
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
        <div class="footer">
            Footer
        </div>


        <script type="application/javascript">
        $(function(){
            var ViewModel = {
                user: ${ userJson },
                pratilipiTypes: ${ pratilipiTypesJson },
                navigationList: ${ navigationList },
                languageMap: ${ languageMap },
                sectionList: [{"title":"\u0027இந்த வார எழுத்தாளர்\u0027 - ஈரோடு கார்த்திக்","listPageUrl":"/authoroftheweek","pratilipiList":[{"pratilipiId":5657872050421760,"title":"உப்பு காபியும் ஒரு காதலும்","author":{"authorId":4859243224104960,"name":"ஈரோடு கார்த்திக்","pageUrl":"/erode-karthik"},"pageUrl":"/erode-karthik/uppu-coffeyum-oru-kadhalum","coverImageUrl":"http://0.ptlp.co/pratilipi/cover?pratilipiId\u003d5657872050421760\u0026version\u003d1451627729694","readPageUrl":"/read?id\u003d5657872050421760","writePageUrl":"/write?id\u003d5657872050421760","ratingCount":7,"averageRating":4.571429,"readCount":11176,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5185279753191424,"title":"சகுனி வில்லனா ?","author":{"authorId":4859243224104960,"name":"ஈரோடு கார்த்திக்","pageUrl":"/erode-karthik"},"pageUrl":"/erode-karthik/saguni-villana","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d5185279753191424\u0026version\u003d1451284788362","readPageUrl":"/read?id\u003d5185279753191424","writePageUrl":"/write?id\u003d5185279753191424","ratingCount":2,"averageRating":4.0,"readCount":9845,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6218971603795968,"title":"உலகின் கடைசி ஆண்கள்","author":{"authorId":4859243224104960,"name":"ஈரோடு கார்த்திக்","pageUrl":"/erode-karthik"},"pageUrl":"/erode-karthik/ulagin-kadaisi-aangal","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d6218971603795968\u0026version\u003d1477571502824","readPageUrl":"/read?id\u003d6218971603795968","writePageUrl":"/pratilipi-write?id\u003d6218971603795968","ratingCount":1,"averageRating":5.0,"readCount":3172,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5741924795285504,"title":"எதிர்பூமி","author":{"authorId":4859243224104960,"name":"ஈரோடு கார்த்திக்","pageUrl":"/erode-karthik"},"pageUrl":"/erode-karthik/edhirboomi","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d5741924795285504\u0026version\u003d1451988100464","readPageUrl":"/read?id\u003d5741924795285504","writePageUrl":"/write?id\u003d5741924795285504","ratingCount":4,"averageRating":4.0,"readCount":4501,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5603878461505536,"title":"பேய்ப் படமும் ஒரு வியாதியும்","author":{"authorId":4859243224104960,"name":"ஈரோடு கார்த்திக்","pageUrl":"/erode-karthik"},"pageUrl":"/erode-karthik/pei-padamum-oru-vyathiyum","coverImageUrl":"http://1.ptlp.co/pratilipi/cover?pratilipiId\u003d5603878461505536\u0026version\u003d1468502717229","readPageUrl":"/read?id\u003d5603878461505536","writePageUrl":"/write?id\u003d5603878461505536","ratingCount":1,"averageRating":5.0,"readCount":2753,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5645202668650496,"title":"தந்திரம்","author":{"authorId":4859243224104960,"name":"ஈரோடு கார்த்திக்","pageUrl":"/erode-karthik"},"pageUrl":"/erode-karthik/thandhiram","coverImageUrl":"http://1.ptlp.co/pratilipi/cover?pratilipiId\u003d5645202668650496\u0026version\u003d1452065157879","readPageUrl":"/read?id\u003d5645202668650496","writePageUrl":"/write?id\u003d5645202668650496","ratingCount":3,"averageRating":4.0,"readCount":2760,"addedToLib":false,"hasAccessToUpdate":false}]},{"title":"அவளும் நானும்","listPageUrl":"/avalumnanum","pratilipiList":[{"pratilipiId":6596734566268928,"title":"அப்பா","author":{"authorId":5631749434376192,"name":"ஹரிஷ் கணபதி","pageUrl":"/harish-ganapathi"},"pageUrl":"/harish-ganapathi/appa","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d6596734566268928\u0026version\u003d1435232399624","readPageUrl":"/read?id\u003d6596734566268928","writePageUrl":"/write?id\u003d6596734566268928","ratingCount":3,"averageRating":4.6666665,"readCount":6982,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6142931610107904,"title":"சகியோடு சிறுபொழுது","author":{"authorId":5075760188489728,"name":"சிவராமன் கண்ணன்","pageUrl":"/sivaraman-kannan"},"pageUrl":"/sivaraman-kannan/sakiyodu-sirupozhudhu","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d6142931610107904\u0026version\u003d1434992969532","readPageUrl":"/read?id\u003d6142931610107904","writePageUrl":"/write?id\u003d6142931610107904","ratingCount":0,"averageRating":0.0,"readCount":804,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5740534714859520,"title":"மறந்த ஒரு காதல்","author":{"authorId":6236231721549824,"name":"லதா","pageUrl":"/latha"},"pageUrl":"/latha/marandha-oru-kaadhal","coverImageUrl":"http://0.ptlp.co/pratilipi/cover?pratilipiId\u003d5740534714859520\u0026version\u003d1434881783900","readPageUrl":"/read?id\u003d5740534714859520","writePageUrl":"/write?id\u003d5740534714859520","ratingCount":3,"averageRating":3.6666667,"readCount":4042,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":4673321551527936,"title":"கடந்து சென்ற காதல் !!","author":{"authorId":4684175068102656,"name":"குகன்","pageUrl":"/guhan"},"pageUrl":"/guhan/kadandhu-sendra-kaadhal","coverImageUrl":"http://1.ptlp.co/pratilipi/cover?pratilipiId\u003d4673321551527936\u0026version\u003d1433408355429","readPageUrl":"/read?id\u003d4673321551527936","writePageUrl":"/write?id\u003d4673321551527936","ratingCount":19,"averageRating":4.5263157,"readCount":48861,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6502198754148352,"title":"காதலா? கடமையா? – குறுநாவல்","author":{"authorId":5110832824320000,"name":"சித்தி ஜுனைதா பேகம்","pageUrl":"/sithi-junaidha-begam"},"pageUrl":"/sithi-junaidha-begam/kadhala-kadamaiya","coverImageUrl":"http://2.ptlp.co/pratilipi/cover?pratilipiId\u003d6502198754148352\u0026version\u003d1432904772944","readPageUrl":"/read?id\u003d6502198754148352","writePageUrl":"/write?id\u003d6502198754148352","ratingCount":1,"averageRating":4.0,"readCount":678,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5899422105862144,"title":"தேவதை சரணாலயம்","author":{"authorId":5966312329183232,"name":"கவிஞர் தவம்","pageUrl":"/kavignar-thavam"},"pageUrl":"/kavignar-thavam/thevadhai-saranalayam","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d5899422105862144\u0026version\u003d1432821101268","readPageUrl":"/read?id\u003d5899422105862144","writePageUrl":"/write?id\u003d5899422105862144","ratingCount":1,"averageRating":4.0,"readCount":451,"addedToLib":false,"hasAccessToUpdate":false}]},{"title":"கவிதை சாலை","listPageUrl":"/kavidhaisaalai","pratilipiList":[{"pratilipiId":6583874420211712,"title":"இவை இரண்டும் காமத்தில் வரா....","author":{"authorId":6516155416051712,"name":"பூங்கோதை ரமேஷ்","pageUrl":"/poongothai-ramesh"},"pageUrl":"/pratilipi/6583874420211712","coverImageUrl":"http://2.ptlp.co/pratilipi/cover?pratilipiId\u003d6583874420211712\u0026version\u003d1468072268418","readPageUrl":"/read?id\u003d6583874420211712","writePageUrl":"/write?id\u003d6583874420211712","ratingCount":2,"averageRating":4.5,"readCount":556,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5657633287569408,"title":"பெண்ணிற்கான புதிய விடியல்","author":{"authorId":6290625768980480,"name":"பிரதீபா கண்ணன் ","pageUrl":"/pratheepa-kannan"},"pageUrl":"/pratheepa-kannan/pennirkaana-pudhiya-vidiyal","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d5657633287569408\u0026version\u003d1459428516763","readPageUrl":"/read?id\u003d5657633287569408","writePageUrl":"/write?id\u003d5657633287569408","ratingCount":20,"averageRating":4.85,"readCount":1015,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5206978487910400,"title":"கொண்டாடப்படாத காதல்","author":{"authorId":5953888456802304,"name":"ஜெர்ரி பாஸ்டின்","pageUrl":"/jerry-bastin"},"pageUrl":"/jerry-bastin/kondadapadadha-kadhal","coverImageUrl":"http://0.ptlp.co/pratilipi/cover?pratilipiId\u003d5206978487910400\u0026version\u003d1454912498699","readPageUrl":"/read?id\u003d5206978487910400","writePageUrl":"/write?id\u003d5206978487910400","ratingCount":3,"averageRating":3.3333333,"readCount":779,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5702841278660608,"title":"கொண்டாடப்படாத காதல்","author":{"authorId":5684849123786752,"name":"ர. இந்து","pageUrl":"/r-indhu"},"pageUrl":"/r-indhu/kondadapadadha-kaadhal","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d5702841278660608\u0026version\u003d1455715762074","readPageUrl":"/read?id\u003d5702841278660608","writePageUrl":"/write?id\u003d5702841278660608","ratingCount":22,"averageRating":4.818182,"readCount":1300,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6242980970102784,"title":"காதல் கொண்டேன்","author":{"authorId":5403962167525376,"name":"சூர்யா","pageUrl":"/surya"},"pageUrl":"/surya/kadhal-konden","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d6242980970102784\u0026version\u003d1471021680564","readPageUrl":"/read?id\u003d6242980970102784","writePageUrl":"/write?id\u003d6242980970102784","ratingCount":0,"averageRating":0.0,"readCount":512,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5662814144299008,"title":"இங்கி பிங்கி பாங்கி","author":{"authorId":4816826919813120,"name":"மு. அறவொளி","pageUrl":"/mu-aravoli"},"pageUrl":"/mu-aravoli/inky-pinky-ponky","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d5662814144299008\u0026version\u003d1439197210547","readPageUrl":"/read?id\u003d5662814144299008","writePageUrl":"/write?id\u003d5662814144299008","ratingCount":0,"averageRating":0.0,"readCount":1161,"addedToLib":false,"hasAccessToUpdate":false}]},{"title":"தமிழக அரசியல்","listPageUrl":"/tnpolitics","pratilipiList":[{"pratilipiId":6284602383532032,"title":"நீங்கள் ம.ந.கூவையும் ஆதரிக்கக் கூடாது.. ஏன்?","author":{"authorId":6265154570289152,"name":"வில்லவன்","pageUrl":"/villavan"},"pageUrl":"/villavan/nengal-ma-na-koovaiyum-adharikka-kudadhu-yen","coverImageUrl":"http://2.ptlp.co/pratilipi/cover?pratilipiId\u003d6284602383532032\u0026version\u003d1455604040835","readPageUrl":"/read?id\u003d6284602383532032","writePageUrl":"/write?id\u003d6284602383532032","ratingCount":0,"averageRating":0.0,"readCount":347,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5733541054775296,"title":"தமிழகச் சட்டசபை தேர்தலும் முடிவுகளும் 2016","author":{"authorId":5142965274017792,"name":"ராஜா ராஜேந்திரன்","pageUrl":"/raja-rajendran"},"pageUrl":"/raja-rajendran/tamizhaga-sattasabai-therdhalum-mudivugalum-2016","coverImageUrl":"http://1.ptlp.co/pratilipi/cover?pratilipiId\u003d5733541054775296\u0026version\u003d1464601719467","readPageUrl":"/read?id\u003d5733541054775296","writePageUrl":"/write?id\u003d5733541054775296","ratingCount":0,"averageRating":0.0,"readCount":14,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6304534372548608,"title":"நாஞ்சில் - ஒரு சகாப்தம்","author":{"authorId":5733403722776576,"name":"சிவகுமார் வெங்கடாசலம்","pageUrl":"/sivakumar-venkatachalam"},"pageUrl":"/sivakumar-venkatachalam/naanjil-oru-sagaptham","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d6304534372548608\u0026version\u003d1451903040940","readPageUrl":"/read?id\u003d6304534372548608","writePageUrl":"/write?id\u003d6304534372548608","ratingCount":1,"averageRating":2.0,"readCount":3903,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5142434516303872,"title":"நூத்தி பத்து அம்மா அம்மா!","author":{"authorId":5945781940912128,"name":"யுவகிருஷ்ணா","pageUrl":"/yuvakrishna"},"pageUrl":"/yuvakrishna/noothi-pathu-amma-amma","coverImageUrl":"http://2.ptlp.co/pratilipi/cover?pratilipiId\u003d5142434516303872\u0026version\u003d1456490023512","readPageUrl":"/read?id\u003d5142434516303872","writePageUrl":"/write?id\u003d5142434516303872","ratingCount":0,"averageRating":0.0,"readCount":18,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6017566419976192,"title":"வாக்காளர் அலப்பறை...7","author":{"authorId":5705651795787776,"name":"\u0027பரிவை\u0027 சே.குமார்","pageUrl":"/parivai-s-kumar"},"pageUrl":"/parivai-s-kumar/vaakalar-alaparai-7","coverImageUrl":"http://2.ptlp.co/pratilipi/cover?pratilipiId\u003d6017566419976192\u0026version\u003d1462891380281","readPageUrl":"/read?id\u003d6017566419976192","writePageUrl":"/write?id\u003d6017566419976192","ratingCount":0,"averageRating":0.0,"readCount":197,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6457397323235328,"title":"மிஸ்டர் ராங்!","author":{"authorId":5945781940912128,"name":"யுவகிருஷ்ணா","pageUrl":"/yuvakrishna"},"pageUrl":"/yuvakrishna/mister-wrong","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d6457397323235328\u0026version\u003d1457180481502","readPageUrl":"/read?id\u003d6457397323235328","writePageUrl":"/write?id\u003d6457397323235328","ratingCount":0,"averageRating":0.0,"readCount":5196,"addedToLib":false,"hasAccessToUpdate":false}]},{"title":"புதியவை","listPageUrl":"/new","pratilipiList":[{"pratilipiId":4862077475749888,"title":"அன்புதான் இன்ப ஊற்று !","author":{"authorId":5119620790878208,"name":"சுப்ரமணியன் .பூ","pageUrl":"/subramanian-poovalingam"},"pageUrl":"/subramanian-poovalingam/anbuthaan-inba-ootru","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d4862077475749888\u0026version\u003d1479213612159","readPageUrl":"/read?id\u003d4862077475749888","writePageUrl":"/pratilipi-write?id\u003d4862077475749888","ratingCount":0,"averageRating":0.0,"readCount":5,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5933941568569344,"title":"இலவசம் என்னும் வசியம்","author":{"authorId":5119620790878208,"name":"சுப்ரமணியன் .பூ","pageUrl":"/subramanian-poovalingam"},"pageUrl":"/subramanian-poovalingam/ilavasam-ennum-vasiyam","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d5933941568569344\u0026version\u003d1479213488083","readPageUrl":"/read?id\u003d5933941568569344","writePageUrl":"/pratilipi-write?id\u003d5933941568569344","ratingCount":0,"averageRating":0.0,"readCount":2,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":5739271907442688,"title":"புதுச் செல்வந்தர்கள்","author":{"authorId":5674212849090560,"name":"உதயகுமார் கணபதி","pageUrl":"/udhayakumar-ganapathy"},"pageUrl":"/udhayakumar-ganapathy/new-rich-people","coverImageUrl":"http://3.ptlp.co/pratilipi/cover?pratilipiId\u003d5739271907442688\u0026version\u003d1479141799711","readPageUrl":"/read?id\u003d5739271907442688","writePageUrl":"/pratilipi-write?id\u003d5739271907442688","ratingCount":0,"averageRating":0.0,"readCount":0,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":6413535736233984,"title":"இரா. மோகன் ! நூல் ஆசிரியர் ச. கவிதா. நூல் விமர்சனம் : கவிஞர் இரா. இரவி.","author":{"authorId":5674543087616000,"name":"கவிஞர் இரா. இரவி","pageUrl":"/kavignar-eraravi"},"pageUrl":"/kavignar-eraravi/ira-mogan","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d6413535736233984\u0026version\u003d1479281583849","readPageUrl":"/read?id\u003d6413535736233984","writePageUrl":"/pratilipi-write?id\u003d6413535736233984","ratingCount":0,"averageRating":0.0,"readCount":0,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":4708112268263424,"title":"தன்னம்பிக்கை","author":{"authorId":5096078908063744,"name":"மனோஜ் குமார்","pageUrl":"/manoj-kumar-8"},"pageUrl":"/manoj-kumar-8/thannambikkai","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d4708112268263424\u0026version\u003d1479101314564","readPageUrl":"/read?id\u003d4708112268263424","writePageUrl":"/pratilipi-write?id\u003d4708112268263424","ratingCount":0,"averageRating":0.0,"readCount":5,"addedToLib":false,"hasAccessToUpdate":false},{"pratilipiId":4864343045832704,"title":"உணர்வு","author":{"authorId":5975434744299520,"name":"சண்முக வேல்","pageUrl":"/shanmuga-vel-shanpalani"},"pageUrl":"/pratilipi/4864343045832704","coverImageUrl":"http://4.ptlp.co/pratilipi/cover?pratilipiId\u003d4864343045832704\u0026version\u003d1479091556471","readPageUrl":"/read?id\u003d4864343045832704","writePageUrl":"/pratilipi-write?id\u003d4864343045832704","ratingCount":0,"averageRating":0.0,"readCount":0,"addedToLib":false,"hasAccessToUpdate":false}]}],
            };
            
            ko.applyBindings( ViewModel );
        });
    </script>
  </body>
</html>

