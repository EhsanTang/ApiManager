/********* http *******/
var ADVERT_ID = "i-advert-id";
function getProjectAdvert(){
    var protocolStr = document.location.protocol;
    var url = "";
    if(protocolStr == "http:"){
        url = "http://api.crap.cn/mock/trueExam.do?id=158667944751412000176&cache=true";
    } else{
        url = "https://api.crap.cn/mock/trueExam.do?id=158667944751412000176&cache=true";
    }

    $.ajax({
        type: "POST",
        url: url,
        async: true,
        data: true,
        timeout: 3000,
        complete: function (responseData, textStatus) {
            if (textStatus == "success") {
                var responseJson = $.parseJSON(responseData.responseText);
                var adverts = responseJson.projectAdverts;

                for(var i=0 ;i<adverts.length; i++){
                    var advert = adverts[i];
                    if (advert.imgUrl){
                        $("#" + ADVERT_ID).append("<div class='p5 BGFFF'><a target='_blank' href='" + advert.httpUrl+ "'><img class='w' src='" + advert.imgUrl + "'/></a></div>");
                    } else {
                        $("#" + ADVERT_ID).append("<div class='p5 BGFFF C000'><a target='_blank' class='no_unl' href='"
                            + advert.httpUrl+ "'><div class='BGEEE f16 p5 pl10 adorn-color' style='color: " + advert.titleColor+ "!important;'> "
                            + advert.title + "<div class='f12 C999 fw200'>" + advert.subTitle+ "</div></div></a></div>");
                    }
                }
            }
        }
    });
}

getProjectAdvert();