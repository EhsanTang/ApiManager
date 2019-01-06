<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var pickTag = "${tag}";
	var pickTagName = "${tagName}";
	var listDataName = "${listDataName}"
    var listDataIndex = "${listDataIndex}"

    function pickCheck(id, radio) {
        var length = document.getElementsByName('cid').length;
        if ($("#"+id).is(':checked')) {
            $("#"+id).prop("checked", false);
        } else {
            $("#"+id).prop("checked", true);
            for (var i = 0; i < length; i++) {
                if (document.getElementsByName('cid')[i].checked == true) {
					if(pickTagName){
						$("#" + pickTagName).html($(".cidName")[i].textContent);
					}
					$("#" + pickTag).css
					// class 、 发送http请求
                    mr10 mt-3 f12 btn btn-xs btn-bug-status-21
                    $(".cidName")[i].value;
                    break;
                }

            }
        }
        iClose('lookUp');
    }
</script>
<div id="pickContent">
	${pickContent}
</div>
