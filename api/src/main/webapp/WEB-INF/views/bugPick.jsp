<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var pickTag = "${tag}";
    function pickCheck(id) {
        var length = document.getElementsByName('cid').length;
        var obj = $("#"+id);
        var target = $("#" + pickTag);
        if (obj.is(':checked')) {
            obj.prop("checked", false);
        } else {
            obj.prop("checked", true);
            for (var i = 0; i < length; i++) {
                if (document.getElementsByName('cid')[i].checked == true) {
                    var def = target.attr("crap-data-def");
                    var iclass = target.attr("crap-data-class");
                    var value = document.getElementsByName('cid')[i].value;

                    target.html($(".cidName")[i].textContent);
                    target.removeClass(iclass + "-" + def);
                    target.addClass(iclass + "-" + value);
                    target.attr("crap-data-def", value);
					//  发送http请求
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
