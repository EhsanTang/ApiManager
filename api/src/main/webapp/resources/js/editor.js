/**
 * 编辑器：富文本wang编辑器，markdown editorMe编辑器
 * 数据字典，接口参数编辑方法
 */
var markdownEditor;
function createEditorMe(id, markdownContent) {
    if (markdownEditor != null) {
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
            return ["undo", "redo", "|", "bold", "del", "italic", "quote", "|"
                , "h1", "h2", "h3", "h4", "h5", "h6", "|",
                "list-ul", "list-ol", "hr", "|",
                "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities", "pagebreak", "|",
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
        imageUploadURL: "markdown/upload.do",
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

function createWangEditor(id, modelField, init, height) {
    userMarkdown = false;
    var root = getRootScope();
    var E = window.wangEditor;
    var editor = new E(document.getElementById(id));
    init(editor, modelField);
    editor.create();
    if (height) {
        $(".w-e-text-container").css("height", height);
    }
    $(".w-e-text-container").css("z-index", 98);
    $(".w-e-menu").css("z-index", 99);
    if (root.model[modelField] == null) {
        root.model[modelField] = "";
    }
    editor.txt.html(root.model[modelField]);
}

function initArticleEditor(editor, modelField) {
    var root = getRootScope();
    editor.customConfig.uploadImgMaxLength = 1;
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024; // 3M
    editor.customConfig.uploadImgServer = 'file/upload.do';
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
        root.model[modelField] = html;
    }
}

function initInterfaceEditor(editor, modelField) {
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
        root.model[modelField] = html;
    }
}

var userMarkdown = false;
function changeEditor(obj, markdown) {
    $(".article-editor").removeClass("btn-main")
    $(".article-editor").addClass("btn-default")

    if (markdown) {
        userMarkdown = true;
        $("#article-editor").addClass('none');
        $("#markdown-editor").removeClass('none');
        createEditorMe('markdown-editor', getRootScope().model.markdown);
    } else {
        userMarkdown = false;
        $("#article-editor").removeClass('none');
        $("#markdown-editor").addClass('none');
    }
    $(obj).addClass('btn-main');
    $(obj).removeClass('btn-default');
}
/********************** end:markdown 富文本 **********************/
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
var deleteButton = "<button class='cursor btn btn-xs btn-default'><i class='iconfont text-danger' onclick='deleteOneTr(this)'>&#xe60e;</i> </button>";
var moverSpan = "<span class='cursor btn btn-xs btn-default' style='cursor: move;'><i class='iconfont'>&#xea17;</i></span>";
/*
 * 数据字典方法
 */
/**************** 数据库表 ****************/
var dictNameHtml = "<td><input class='form-control' autocomplete='off' type='text' name='name' value='DICT_NAME' placeholder='必填，不填将被过滤' onkeyup='addOneDictionaryTr(this)'></td>";
var dictTypeHtml = "<td><input class='form-control' type='text' name='type' value='DICT_TYPE' placeholder='类型'></td>";
var dictNotNullHtml = "<td><select name='notNull' class='form-control'>" +
    "<option value='true' true_select>true</option><option value='false' false_select>false</option>" +
    "</select></td>";
var dictDefhtml = "<td><input class='form-control' autocomplete='off' type='text' name='def' value='DICT_DEF' placeholder='默认值' onkeyup='addOneDictionaryTr(this)'></td>";
var dictFlagHtml = "<td><select name='flag' class='form-control'>" +
    "<option value='common' common_select>普通</option><option value='primary' primary_select>主键</option>" +
    "<option value='foreign' foreign_select>外键</option><option value='associate' associate_select>关联</option>" +
    "</select></td>";
var dictRemarkHtml = "<td><input class='form-control' autocomplete='off' type='text' name='remark' value='DICT_REMARK' placeholder='备注' onkeyup='addOneDictionaryTr(this)'></td>";
var dictOperateHtml = "<td class='cursor tc'>" + deleteButton + "&nbsp;" + moverSpan + "</td>"

function addOneDictionaryTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#content").append("<tr>"
        + replaceAll(dictNameHtml, 'DICT_NAME', model.name)
        + replaceAll(dictTypeHtml, 'DICT_TYPE', model.type)
        + replaceAll(dictNotNullHtml, model.notNull+'_select', ' selected ')
        + replaceAll(dictDefhtml, 'DICT_DEF', model.def)
        + replaceAll(dictFlagHtml, model.flag + '_select', ' selected ')
        + replaceAll(dictRemarkHtml, 'DICT_REMARK', model.remark)
        + dictOperateHtml
        +"</tr>");
}

/************** 接口 ******************/
var interHeadNameHtml = "<td><input class='form-control C000 fw500' type='text' name='name' value='INTER_HEAD_NAME' placeholder='参数名必填，或者将被过滤' autocomplete='off' " +
    "onkeyup='addOneInterHeadTr(this)'></td>";
var interHeadNecessaryHtml = "<td> <select name='necessary' class='form-control'>"
    + "<option value='true' true_select>是</option>"
    + "<option value='false' false_select>否</option>"
    + "</select></td>";
var interHeadTypeHtml = "<td> <select name='type' class='form-control'>"
    + "<option value='string' string_select>string</option>"
    + "<option value='int' int_select>int</option>"
    + "<option value='float' float_select>float</option>"
    + "<option value='long' long_select>long</option>"
    + "<option value='byte' byte_select>byte</option>"
    + "<option value='double' double_select>double</option>"
    + "<option value='boolean' boolean_select>boolean</option>"
    + "<option value='file' file_select>file</option>"
    + "</select> </td>";
var interHeadDefHtml = "<td> <input class='form-control C000 fw500' type= 'text' name='def' autocomplete='off' onkeyup='addOneInterHeadTr(this)' value='INTER_HEAD_DEF' placeholder='默认值'></td>";
var interHeadRemarkHtml = "<td><input class='form-control C000 fw500' type='text' name='remark' autocomplete='off' value='INTER_HEAD_REMARK' onkeyup='addOneInterHeadTr(this)'></td> ";
var interHeadOperateHtml = "<td class='tc BGFFF'>" + deleteButton + "&nbsp;" + moverSpan + "</td>";

function addOneInterHeadTr(target, model) {
    // 自动添加下一行
    if (!isLast(target, model)){
        return;
    }
    if (!model){
        model = new Object();
    }
    $("#editHeaderTable").append("<tr>"
        + replaceAll(interHeadNameHtml, 'INTER_HEAD_NAME', model.name)
        + replaceAll(interHeadNecessaryHtml, model.necessary+'_select', ' selected ')
        + replaceAll(interHeadTypeHtml, model.type+'_select', ' selected ')
        + replaceAll(interHeadDefHtml, 'INTER_HEAD_DEF', model.def)
        + replaceAll(interHeadRemarkHtml, 'INTER_HEAD_REMARK', model.remark)
        + interHeadOperateHtml
        +"</tr>");
}

// 接口头信息

/**if (!rowNum || rowNum == '') {
        var mydate = new Date();
        rowNum = mydate.getTime();
    }**/