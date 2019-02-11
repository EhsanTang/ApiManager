/**
 * 编辑器：富文本wang编辑器，markdown editorMe编辑器
 * 数据字典，接口参数编辑方法
 */
    // 标识是否初始化过，display = none时，初始化样式有问题
var markdownEditor;
function createEditorMe(id, markdownContent) {
    if (markdownEditor != null){
        return;
    }
    markdownEditor = editormd(id, {
        path: "./resources/framework/editormd-1.5.0/lib/", // Autoload modules mode, codemirror, marked... dependents libs path
        width: "100%",
        height: 500,
        theme: "default", // "default | dark"
        previewTheme: "default",
        // editorTheme : "pastel-on-dark",
        codeFold: true,
        toolbarIcons: function () {
            // return editormd.toolbarModes["mini"]; // full, simple, mini
            // Or return editormd.toolbarModes[name]; // full, simple, mini
            // Using "||" set icons align right.
            // "emoji", "html-entities" json数据被拦截
            return ["undo", "redo", "|", "bold", "del", "italic", "quote", "|"
                , "h1", "h2", "h3", "h4", "h5", "h6", "|",
                "list-ul", "list-ol", "hr", "|",
                "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "pagebreak", "|",
                "goto-line", "watch", "preview", "fullscreen", "clear", "search", "|", "help"];
        },
        //syncScrolling : false,
        saveHTMLToTextarea: true,    // 保存 HTML 到 Textarea
        searchReplace: true,
        //watch : false,                // 关闭实时预览
        htmlDecode: "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        //toolbar  : false,             //关闭工具栏
        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
        emoji: true,
        taskList: true,
        tocm: true,         // Using [TOCM]
        tex: true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart: true,             // 开启流程图支持，默认关闭
        sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
        //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: "user/markdown/upload.do",
        onload: function () {
            console.log('onload', this);
            //this.fullscreen();
            this.unwatch();
            //this.watch().fullscreen();

            this.setMarkdown(markdownContent);
            //this.width("100%");
            //this.height(480);
            //this.resize("100%", 640);
        }, onfullscreen: function () {
            $("#"+id).css("z-index", 101);
        }, onfullscreenExit: function () {
            $("#"+id).css("z-index", 99);
        }
    });
}

/**
 * 初始化富文本编辑器
 * @param id
 * @param editorHtml
 * @param init
 * @param height
 */
function createWangEditor(id, editorHtml, init, height) {
    var E = window.wangEditor;
    var editor = new E(document.getElementById(id));
    init(editor);
    editor.create();
    if (height) {
        $(".w-e-text-container").css("height", height);
    }
    $(".w-e-text-container").css("z-index", 98);
    $(".w-e-menu").css("z-index", 99);
    if (!editorHtml){
        editorHtml = "";
    }
    editor.txt.html(editorHtml);
    return editor;
}

function initArticleEditor(editor) {
    var root = getRootScope();
    editor.customConfig.uploadImgMaxLength = 1;
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024; // 3M
    editor.customConfig.uploadImgServer = 'user/file/upload.do';
    editor.customConfig.uploadFileName = 'img';
    editor.customConfig.zIndex = 999;
    editor.customConfig.uploadImgHooks = {
        fail: function (xhr, editor, result) {
            $("#lookUpContent").html(err1 + "&nbsp; " + result.errorMessage + "" + err2);
            showMessage('lookUp', 'false', false, 3);
        }
    }
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        root.model.content = html;
    }
}

function initInterfaceEditor(editor) {
    var root = getRootScope();
    // 配置菜单
    editor.customConfig.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize',  // 字号
        'fontName',  // 字体
        'italic',  // 斜体
        'foreColor',  // 文字颜色
        'backColor',  // 背景颜色
        'justify',  // 对齐方式
        'undo',  // 撤销
        'redo'  // 重复
    ];
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        root.model.remark = html;
    }
}

/********************** end:markdown 富文本 **********************/
function initDragTable(id) {
    $("#" + id + " tbody").sortable({
        cursor: "move",
        // revert: true,                      //释放时，增加动画
        // revertDuration: 200, // 还原（revert）动画的持续时间，以毫秒计。如果 revert 选项是 false 则忽略。
        containment: "parent", // 约束拖拽范围的边界，不能超过父对象
        delay: 100, //鼠标按下后直到拖拽开始为止的时间，以毫秒计。该选项可以防止点击在某个元素上时不必要的拖拽。
        distance: 0, // 鼠标按下后拖拽开始前必须移动的距离，以像素计。该选项可以防止点击在某个元素上时不必要的拖拽
        cancel: "button", // 指令的空间不支持拖拽，可以是class、id等
        axis: "y", // 只能在y轴拖拽
        handle: "span", // 只有span才支持拖拽
        items: ".drag",                       //只是tr可以拖动
        opacity: 1.0,                      //拖动时，透明度为0.6
        update: function(event, ui) {      //更新排序之后
            // var tr = ui.item; //当前拖动的元素
            // var index = tr.attr("index"); //当前元素的顺序
            // var header = $rootScope.headerList.splice(index, 1);
            // // 新的序号计算
            // var newIndex = getNewArray('editHeaderTable');
            // $rootScope.headerList.splice(newIndex - 1, 0, header[0]);
            // $rootScope.$apply();
            //$rootScope.headerList = getNewArray('editHeaderTable');
            //$rootScope.$apply();
        }
    });
    $("#" + id + " tbody").sortable({
        connectToSortable : "#body",  //目标区域列表div的dom
        helper: fixHelperModified,
        stop: updateIndex
    }).disableSelection()
}
/**
 * 表格拖动
 * @param e
 * @param tr
 */
var fixHelperModified = function(e, tr) {
    var $originals = tr.children();
    var $helper = tr.clone();
    $helper.children().each(function(index) {
        $(this).width($originals.eq(index).width() + 6)
    });
    $helper.width(tr.width() + 1);
    $helper.css("margin-left", "-1px");
    return $helper;
}
var updateIndex = function(e, ui) {};
/********************** end:表格拖动 ********************/

/**********************接口、表格公用编辑方法 ************/
/**
 * 删除表格中的一行
 * @param nowTr
 */
function deleteOneTr(nowTr) {
    $(nowTr).parent().parent().parent().remove();
}

/**
 * 判断是最后一个tr
 * @param target
 * @param model
 * @returns {boolean}
 */
function isLast(target, model) {
    if (target){
        if ( $(target).val() == ''){
            return false;
        }
        var tr = $(target).parent().parent();
        var totalTrNum = tr.parent().children().length;
        if( tr.index() + 1 != totalTrNum){
            return false;
        }
    }
    return true;
}

/********************** end: 接口、表格公用编辑方法 ************/
var deleteButton = "<button class='cursor btn btn-xs btn-default' type='button'><i class='iconfont text-danger' onclick='deleteOneTr(this)'>&#xe69d;</i> </button>";
var moverSpan = "<span class='cursor btn btn-xs btn-default' style='cursor: move;'><i class='iconfont'>&#xe6fd;</i></span>";
var insertButton = "<button class='cursor btn btn-xs btn-default C999' type='button' onclick='ADD_ONE_TR(this, null, true)'>插入</button>";

var interNameHtml = "<td><input class='form-control C555 fw500' type='text' name='name' value='INTER_NAME' " +
    "placeholder='参数名必填，多级参数请使用\"->\"分割，如：f1->f2' autocomplete='off' " +
    "onkeyup='ADD_ONE_TR(this)'></td>";
var remarkHtml = "<td><input class='form-control C000' type='text' name='remark' autocomplete='off' value='REMARK' onkeyup='ADD_ONE_TR(this)'></td> ";
var defHtml = "<td> <input class='form-control C000' type= 'text' name='def' autocomplete='off' onkeyup='ADD_ONE_TR(this)' value='DEF' placeholder='默认值'></td>";
var interOperateHtml = "<td class='tc BGFFF'>" + moverSpan + "&nbsp;" + deleteButton + "</td>";
var interResOperateHtml = "<td class='tc BGFFF'>" + insertButton + "&nbsp;" + moverSpan + "&nbsp;" + deleteButton + "</td>";
var interNecessaryHtml = "<td> <select name='necessary' class='form-control'>"
    + "<option value='true' true_select>是</option>"
    + "<option value='false' false_select>否</option>"
    + "</select></td>";
var interTypeHtml = "<td> <select name='type' class='form-control'>"
    + "<option value='string' string_select>string</option>"
    + "<option value='int' int_select>int</option>"
    + "<option value='float' float_select>float</option>"
    + "<option value='long' long_select>long</option>"
    + "<option value='byte' byte_select>byte</option>"
    + "<option value='double' double_select>double</option>"
    + "<option value='number' number_select>number</option>"
    + "<option value='boolean' boolean_select>boolean</option>"
    + "<option value='object' object_select>object</option>"
    + "<option value='array' array_select>array</option>"
    + "<option value='array[int]' array[int]_select>array[int]</option>"
    + "<option value='array[float]' array[float]_select>array[float]</option>"
    + "<option value='array[long]' array[long]_select>array[long]</option>"
    + "<option value='array[byte]' array[byte]_select>array[byte]</option>"
    + "<option value='array[double]' array[double]_select>array[double]</option>"
    + "<option value='array[number]' array[number]_select>array[number]</option>"
    + "<option value='array[boolean]' array[boolean]_select>array[boolean]</option>"
    + "<option value='array[string]' array[string]_select>array[string]</option>"
    + "<option value='array[object]' array[object]_select>array[object]</option>"
    + "<option value='file' file_select>file</option>"
    + "</select> </td>";
/*
 * 数据字典方法
 */
/**************** 数据库表 ****************/
var dictNameHtml = "<td><input class='form-control' autocomplete='off' type='text' name='name' value='DICT_NAME' placeholder='必填，不填将被过滤' onkeyup='addOneDictionaryTr(this)'></td>";
var dictTypeHtml = "<td><input class='form-control' type='text' name='type' value='DICT_TYPE' placeholder='类型'></td>";
var dictNotNullHtml = "<td><select name='notNull' class='form-control'>" +
    "<option value='true' true_select>true</option><option value='false' false_select>false</option>" +
    "</select></td>";
var dictFlagHtml = "<td><select name='flag' class='form-control'>" +
    "<option value='common' common_select>普通</option><option value='primary' primary_select>主键</option>" +
    "<option value='foreign' foreign_select>外键</option><option value='associate' associate_select>关联</option>" +
    "</select></td>";
/**
 * 请求参数
 */
var interParamPositionHtml = "<td> <select name='inUrl' class='form-control'>"
    + "<option value='false' false_select>普通参数</option>"
    + "<option value='true' true_select>URL路径</option>"
    + "</select></td>";

function addOneDictionaryTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#content").append("<tr class='drag'>"
        + replaceAll(dictNameHtml, 'DICT_NAME', model.name)
        + replaceAll(dictTypeHtml, 'DICT_TYPE', model.type)
        + replaceAll(dictNotNullHtml, model.notNull+'_select', ' selected ')
        + replaceAll(replaceAll(defHtml, 'DEF', model.def), "ADD_ONE_TR", "addOneDictionaryTr")
        + replaceAll(dictFlagHtml, model.flag + '_select', ' selected ')
        + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneDictionaryTr")
        + interOperateHtml
        +"</tr>");
}

/************** 接口 ******************/
function addOneInterHeadTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#editHeaderTable").append("<tr class='drag'>"
        + replaceAll(replaceAll(interNameHtml, 'INTER_NAME', model.name), "ADD_ONE_TR", "addOneInterHeadTr")
        + replaceAll(interNecessaryHtml, model.necessary+'_select', ' selected ')
        + replaceAll(interTypeHtml, model.type+'_select', ' selected ')
        + replaceAll(replaceAll(defHtml, 'DEF', model.def), "ADD_ONE_TR", "addOneInterHeadTr")
        + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneInterHeadTr")
        + interOperateHtml
        +"</tr>");
}

var debugValueHtml = "<td> <input class='form-control C000 fw500' type= 'text' name='def' autocomplete='off' onkeyup=\"ADD_ONE_TR('TABLE_ID',this)\" value='DEBUG_VALUE' placeholder='值'></td>";
var debugNameHtml = "<td><input class='form-control C000 fw500' type='text' name='name' value='DEBUG_NAME' placeholder='参数名' autocomplete='off' onkeyup=\"ADD_ONE_TR('TABLE_ID',this)\"></td>";

function addOneDebugTr(id, target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#" + id).append("<tr class='drag'>"
        + replaceAll(replaceAll(replaceAll(debugNameHtml, 'DEBUG_NAME', model.realName), "ADD_ONE_TR", "addOneDebugTr"), 'TABLE_ID', id)
        + replaceAll(replaceAll(replaceAll(debugValueHtml, 'DEBUG_VALUE', model.def), "ADD_ONE_TR", "addOneDebugTr"), 'TABLE_ID', id)
        + interOperateHtml
        +"</tr>");
}

function addOneInterParamTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#editParamTable").append("<tr class='drag'>"
        + replaceAll(replaceAll(interNameHtml, 'INTER_NAME', model.name), "ADD_ONE_TR", "addOneInterParamTr")
        + replaceAll(interNecessaryHtml, model.necessary+'_select', ' selected ')
        + replaceAll(interParamPositionHtml, model.inUrl+'_select', ' selected ')
        + replaceAll(interTypeHtml, model.type+'_select', ' selected ')
        + replaceAll(replaceAll(defHtml, 'DEF', model.def), "ADD_ONE_TR", "addOneInterParamTr")
        + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneInterParamTr")
        + interOperateHtml
        +"</tr>");
}

// 返回字段
function addOneInterRespTr(target, model, isInsert) {
    // 自动添加下一行:最后一行，且不是插入
    if (!isLast(target, model) && (!isInsert)){
        return;
    }
    if (!model){
        model = new Object();
    }

    var trContent = "<tr class='drag'>"
    + replaceAll(replaceAll(interNameHtml, 'INTER_NAME', model.name), "ADD_ONE_TR", "addOneInterRespTr")
    + replaceAll(interNecessaryHtml, model.necessary+'_select', ' selected ')
    + replaceAll(interTypeHtml, model.type+'_select', ' selected ')
    + replaceAll(replaceAll(remarkHtml, 'REMARK', model.remark), "ADD_ONE_TR", "addOneInterRespTr")
    + replaceAll(interResOperateHtml, "ADD_ONE_TR", "addOneInterRespTr")
    +"</tr>";
    if (!isInsert){
        $("#editResponseParamTable").append(trContent);
        if (target){
            // 自动追加一行，不需要选中
            return;
        }
        // 选中最后一行
        $("#editResponseParamTable").find('tr').last().find('td').find('input').first().focus();
        return;
    }

    // 插入子参数
    var $tr = $(target).parent().parent();
    $tr.after(trContent);
    var resParamName = $tr.find('td').find('input').first().val();
    if (resParamName && resParamName.length > 0){
        resParamName = resParamName + "->";
    }
    // 选中新增加的行
    $tr.next().find('td').find('input').first().val('').focus().val(resParamName);
}

// 根据json，导入至参数
function importJson(type){
    var jsonText = jsonToDiv($("#importResponseParam").val());
    if(jsonText.length > 0){
        if(type == 'responseParam'){
            $.each( eval("("+jsonText+")"), function (n, value) {
                addOneInterRespTr(null, value);
            });
        } else if(type == 'header'){
            $.each( eval("("+jsonText+")"), function (n, value) {
                addOneInterHeadTr(null, value);
            });
        }else if(type == 'param'){
            $.each( eval("("+jsonText+")"), function (n, value) {
                addOneInterParamTr(null, value);
            });
        }
    }
    closeMyDialog('myDialog');
}

// 接口头信息

/**if (!rowNum || rowNum == '') {
        var mydate = new Date();
        rowNum = mydate.getTime();
}**/