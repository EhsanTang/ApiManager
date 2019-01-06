function loadBugPick($this, $event, iwidth, iheight,code) {
    /***********加载选择对话框********************/
    var obj = $($this);
    $("#pickContent").html(loadText);
    callAjaxByName("iUrl=user/bug/pick.do|isHowMethod=updateDiv|iPost=POST|iParams=&code="+code+"&tag="+ obj.attr("crap-data-id") +
        "&def=" + obj.attr("crap-data-def"));
    lookUp('lookUp', $event, iheight, iwidth, 5, obj.attr("crap-data-id"));
    showMessage('lookUp','false',false,-1);
}