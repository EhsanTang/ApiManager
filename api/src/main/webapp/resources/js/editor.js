var markdownEditor;

function createEditorme(id, markdownContent) {
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
        createEditorme('markdown-editor', getRootScope().model.markdown);
    } else {
        userMarkdown = false;
        $("#article-editor").removeClass('none');
        $("#markdown-editor").addClass('none');
    }
    $(obj).addClass('btn-main');
    $(obj).removeClass('btn-default');
}