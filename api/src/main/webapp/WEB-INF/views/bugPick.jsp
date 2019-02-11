<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var pickTag = "${tag}";
	var pickTagText = "${tag}-text"
	var type = "${type}";
    function pickCheck(id) {
        var length = document.getElementsByName('cid').length;
        var obj = $("#"+id);
        var target = $("#" + pickTag);
        if (obj.is(':checked')) {
            // 重复选择，不需要做任何操作
            return;
        }

        obj.prop("checked", true);
        for (var i = 0; i < length; i++) {
            if (document.getElementsByName('cid')[i].checked == true) {
                //  发送http请求
                changeBug(target, $("#" + pickTagText), i);
                break;
            }
        }
        iClose('lookUp');
    }
    // 更新状态changeBug
    function changeBug($pickTag, $pickTagText, index) {
        var value = document.getElementsByName('cid')[index].value;
        var id = $pickTag.attr("crap-id");
        var name = $pickTag.attr("crap-name");

        // 新增:详情页面才有bugVO
        if (!id || id == null || id == 'null' || id == 'NULL'){
            changeShow($pickTag, $pickTagText, index);
            var rootScope = getRootScope();
            rootScope.$apply(function () {
                rootScope.bugVO[name] = value;
            });
            return;
		}

        var params = 'value=' + value + '&type=' + type + '&id=' + id;
        $.ajax({
            type: 'GET',
            url: 'user/bug/changeBug.do',
            async: true,
            data: params,
            complete: function (data) {
                data = data.responseText;
                data = eval("(" + data + ")")
                if (data.success == 0){
                    showTipWithTime(data.error.message, 5)
                } else {
                    changeShow($pickTag, $pickTagText, index);
                }
            }
        });
    }
    function changeShow($pickTag, $pickTagText, index) {
        var def = $pickTag.attr("crap-def");
        var classPre = $pickTag.attr("crap-class-pre");
        var value = document.getElementsByName('cid')[index].value;
        $pickTagText.html($(".cidName")[index].textContent);
        if (classPre) {
            $pickTag.removeClass(classPre + "-" + def);
            $pickTag.addClass(classPre + "-" + value);
        }
        $pickTag.attr("crap-def", value);
        $("#refresh").click();
    }
</script>
<div id="pickContent">
	${pickContent}
</div>
