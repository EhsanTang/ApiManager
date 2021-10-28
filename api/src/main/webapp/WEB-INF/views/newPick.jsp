<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
    var pickCallBack = ${iCallBack};
    var pickCallBackParam = '${iCallBackParam}';
    var pickRadio ="${radio}";
    function pickCheck(id,isRadio) {
        if ($("#"+id).is(':checked')) {
            $("#"+id).prop("checked", false);
            $("#d_"+id).removeClass("pickActive");
            $("#d_"+id).removeClass("main-color");
        } else {
            $("#"+id).prop("checked", true);
            $("#d_"+id).addClass("pickActive");
            $("#d_"+id).addClass("main-color");
            if(isRadio=='true'){
                $("#pickContent div").removeClass("pickActive");
                $("#pickContent div").removeClass("main-color")
                setPick(id);
            }
        }
    }


    // pick 确认
    function setPick(id) {
        var result ={};
        var length = document.getElementsByName('cid').length;
        for (var i = 0; i < length; i++) {
            if (pickRadio == 'false') {
                alert("暂时不支持多选");
            } else {
                if (document.getElementsByName('cid')[i].checked == true) {
                    result.name = $(".cidName")[i].textContent;
                    result.value = document.getElementsByName('cid')[i].value;
                    result.id = id;
                    result.pickTag = "${tag}";
                }
            }
        }

        // 回调函数
        if (pickCallBack) {
            if (pickCallBackParam) {
                pickCallBack(result, pickCallBackParam);
            } else {
                pickCallBack(result);
            }
        }
        // 关闭对话框
        iClose('lookUp');
    }
</script>

<div id="pickContent">
	${pickContent}
</div>

