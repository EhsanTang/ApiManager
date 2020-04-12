/********* http *******/
var ADVERT_ID = "i-advert-id";
function getProjectAdvert(){
    $.ajax({
        type: "POST",
        url: "http://api.crap.cn/mock/trueExam.do?id=158667944751412000176&cache=true",
        async: true,
        data: true,
        timeout: 3000,
        complete: function (responseData, textStatus) {
            if (textStatus == "success") {
                var responseJson = $.parseJSON(responseData.responseText);
                var adverts = responseJson.projectAdverts;

                for(var i=0 ;i<adverts.length; i++){
                    var advert = adverts[i];
                    $("#" + ADVERT_ID).append("<div><a target='_blank' href='" + advert.httpUrl+ "'><img class='w' src='" + advert.imgUrl + "'/></a></div>");
                }
            }
        }
    });
}

getProjectAdvert();