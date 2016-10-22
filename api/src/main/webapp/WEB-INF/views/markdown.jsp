<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>CrapApi|接口、文档管理系统—MarkDown</title>
    <link rel="stylesheet" href="../../resources/markdown/dist/markdown-plus.min.css"/>
    <script src="../../resources/markdown/dist/markdown-plus.min.js" charset="utf-8"></script>
  </head>
  <body>
    <div id="mdp-container" style="height: 99%;">
      <div class="ui-layout-north">
        <div id="toolbar" class="noselect">
          <i title="Bold" class="fa fa-bold styling-icon" data-modifier="**"></i>
          <i title="Italic" class="fa fa-italic styling-icon" data-modifier="*"></i>
          <i title="Strikethrough" class="fa fa-strikethrough styling-icon" data-modifier="~~"></i>
          <i title="Underline" class="fa fa-underline styling-icon" data-modifier="++"></i>
          <i title="Mark" class="fa fa-pencil styling-icon" data-modifier="=="></i>
          <i class="dividor">|</i>
          <i title="Heading 1" class="fa heading-icon" data-level="1">h1</i>
          <i title="Heading 2" class="fa heading-icon" data-level="2">h2</i>
          <i title="Heading 3" class="fa heading-icon" data-level="3">h3</i>
          <i title="Heading 4" class="fa heading-icon" data-level="4">h4</i>
          <i title="Heading 5" class="fa heading-icon" data-level="5">h5</i>
          <i title="Heading 6" class="fa heading-icon" data-level="6">h6</i>
          <i class="dividor">|</i>
          <i title="Horizontal rule" id="horizontal-rule" class="fa fa-minus"></i>
          <i title="Quote" class="fa fa-quote-left list-icon" data-prefix="> "></i>
          <i title="Unordered list" class="fa fa-list-ul list-icon" data-prefix="- "></i>
          <i title="Ordered list" class="fa fa-list-ol list-icon" data-prefix="1. "></i>
          <i title="Incomplete task list" class="fa fa-square-o list-icon" data-prefix="- [ ] "></i>
          <i title="Complete task list" class="fa fa-check-square-o list-icon" data-prefix="- [x] "></i>
          <i class="dividor">|</i>
          <i title="Link" class="fa fa-link" id="link-icon" data-sample-text="link" data-sample-url="http://mdp.tylingsoft.com/"></i>
          <i title="Image" class="fa fa-image" id="image-icon" data-sample-text="image" data-sample-url="http://mdp.tylingsoft.com/icon.png"></i>
          <i title="Code" class="fa fa-code" id="code-icon"></i>
          <i title="Table" class="fa fa-table" id="table-icon" data-sample="header 1 | header 2
  ---|---
  row 1 col 1 | row 1 col 2
  row 2 col 1 | row 2 col 2"></i>
          <i class="dividor">|</i>
          <i title="Emoji" class="fa fa-smile-o" data-remodal-target="emoji-modal"></i>
          <i title="Font awesome" class="fa fa-flag-o" data-remodal-target="fa-modal"></i>
          <i title="Ionicons" class="icon ion-ionic" data-remodal-target="ion-modal"></i>
          <i class="dividor">|</i>
          <i title="Mathematical formula" class="fa fa-superscript" id="math-icon" data-sample="E = mc^2"></i>
          <i title="Flowchart" class="fa fa-long-arrow-right mermaid-icon" data-sample="graph LR
  A-->B"></i>
          <i title="Sequence diagram" class="fa fa-exchange mermaid-icon" data-sample="sequenceDiagram
  A->>B: How are you?
  B->>A: Great!"></i>
          <i title="Gantt diagram" class="fa fa-sliders mermaid-icon" data-sample="gantt
  dateFormat YYYY-MM-DD
  section S1
  T1: 2014-01-01, 9d
  section S2
  T2: 2014-01-11, 9d
  section S3
  T3: 2014-01-02, 9d"></i>
          <i class="dividor">|</i>
          <i title="Hide toolbar" class="fa fa-long-arrow-up" id="toggle-toolbar"></i>
          <i title="Toggle editor" class="fa fa-long-arrow-left" id="toggle-editor"></i>
          <i title="Toggle preview" class="fa fa-long-arrow-right" id="toggle-preview"></i>
          <i class="dividor">|</i>
          <!-- <i title="Preferences" class="fa fa-cog" data-remodal-target="preferences-modal"></i>
          <i title="Help" class="fa fa-question-circle" data-remodal-target="help-modal"></i>
          <i title="About" class="fa fa-info-circle" data-remodal-target="about-modal"></i> -->
        </div>
      </div>
      <div class="ui-layout-center">
        <div id="editor">${markdownText}</div> <!-- editor -->
        <div class="remodal" id="emoji-modal" data-remodal-id="emoji-modal"> <!-- emoji modal -->
          <h2>Please enter an emoji code:</h2>
          <p>Examples: "smile", "whale", "santa", "panda_face", "dog", "truck" ...</p>
          <p>For a complete list, please check <a href="http://www.emoji-cheat-sheet.com/" target="_blank">Emoji Cheat Sheet</a>.</p>
          <p><input class="form-control" id="emoji-code" placeholder="smile"/></p>
          <br/><a data-remodal-action="cancel" class="remodal-cancel">Cancel</a>
          <a data-remodal-action="confirm" class="remodal-confirm" id="emoji-confirm">OK</a>
        </div>
        <div class="remodal" id="fa-modal" data-remodal-id="fa-modal"> <!-- Font Awesome modal -->
          <h2>Please enter a Font Awesome code:</h2>
          <p>Examples: "cloud", "flag", "car", "truck", "heart", "dollar" ...</p>
          <p>For a complete list, please check <a href="http://fontawesome.io/icons/" target="_blank">Font Awesome Icons</a>.</p>
          <p><input class="form-control" id="fa-code" placeholder="heart"/></p>
          <br/><a data-remodal-action="cancel" class="remodal-cancel">Cancel</a>
          <a data-remodal-action="confirm" class="remodal-confirm" id="fa-confirm">OK</a>
        </div>
        <div class="remodal" id="ion-modal" data-remodal-id="ion-modal"> <!-- Ionicons modal -->
          <h2>Please enter an Ionicons code:</h2>
          <p>Examples: "beer", "key", "locked", "location", "plane", "ios-eye" ...</p>
          <p>For a complete list, please check <a href="http://ionicons.com/" target="_blank">Ionicons Website</a>.</p>
          <p><input class="form-control" id="ion-code" placeholder="beer"/></p>
          <br/><a data-remodal-action="cancel" class="remodal-cancel">Cancel</a>
          <a data-remodal-action="confirm" class="remodal-confirm" id="ion-confirm">OK</a>
        </div>
        <div class="remodal" id="preferences-modal" data-remodal-id="preferences-modal" data-remodal-options="closeOnEscape: false, closeOnCancel: false, closeOnOutsideClick: false"> <!-- Preferences modal -->
          <img src="icon.png" width="64"/>
          <h2>Markdown Plus Preferences</h2>
          <p>Show toolbar: <select id="show-toolbar">
            <option value="yes">Yes</option>
            <option value="no">No</option>
          </select></p>
          <p>Editor : Preview <select id="editor-versus-preview">
            <option value="100%">0 : 1</option>
            <option value="66.6%">1 : 2</option>
            <option value="50%">1 : 1</option>
            <option value="33.3%">2 : 1</option>
            <option value="1">1 : 0</option>
          </select></p>
          <p>Editor theme: <select id="editor-theme">
            <option value="tomorrow_night_blue">Blue</option>
            <option value="tomorrow">Bright</option>
            <option value="tomorrow_night_eighties">Dark</option>
            <option value="kuroir">Gray</option>
          </select></p>
          <p>Editor font size: <select id="editor-font-size">
            <option value="8">8px</option><option value="9">9px</option><option value="10">10px</option><option value="11">11px</option>
            <option value="12">12px</option><option value="13">13px</option><option value="14">14px</option><option value="15">15px</option>
            <option value="16">16px</option><option value="17">17px</option><option value="18">18px</option><option value="20">20px</option>
            <option value="24">24px</option><option value="32">32px</option><option value="48">48px</option><option value="64">64px</option>
          </select></p>
          <p>Key binding: <select id="key-binding">
            <option value="default">Default</option>
            <option value="emacs">Emacs</option>
            <option value="vim">Vim</option>
          </select></p>
          <p>Gantt diagram axis format: <input id="gantt-axis-format" placeholder="%Y-%m-%d"/>
            <br/><a href="https://github.com/mbostock/d3/wiki/Time-Formatting" target="_blank">Time formatting reference</a></p>
          <p>Custom CSS files: <textarea id="custom-css-files" wrap="off" placeholder="https://cdn.example.com/file.css" title="Multiple files should be separated by line breaks"></textarea>
            <br/><span class="hint">(You need to restart the editor to apply the CSS files)</span>
            <br/><a href="https://github.com/tylingsoft/markdown-plus-themes" target="_blank">Markdown Plus themes</a></p>
          <p>Custom JS files: <textarea id="custom-js-files" wrap="off" placeholder="https://cdn.example.com/file.js" title="Multiple files should be separated by line breaks"></textarea>
            <br/><span class="hint">(You need to restart the editor to apply the JS files)</span>
            <br/><a href="https://github.com/tylingsoft/markdown-plus-plugins" target="_blank">Markdown Plus plugins</a></p>
          <br/><a data-remodal-action="confirm" class="remodal-confirm">OK</a>
        </div>
        <div class="remodal" data-remodal-id="help-modal"> <!-- help modal -->
          <img src="icon.png" width="64"/>
          <h2>Markdown Plus help</h2>
          <p><a href="http://mdp.tylingsoft.com/" target="_blank">Online Sample</a></p>
          <p><a href="https://github.com/ajaxorg/ace/wiki/Default-Keyboard-Shortcuts" target="_blank">Keyboard Shortcuts</a></p>
          <p><a href="https://guides.github.com/features/mastering-markdown/" target="_blank">Markdown Basics</a></p>
          <p><a href="https://help.github.com/articles/github-flavored-markdown/" target="_blank">GitHub Flavored Markdown</a></p>
          <p><a href="http://www.emoji-cheat-sheet.com/" target="_blank">Emoji Cheat Sheet</a></p>
          <p><a href="http://fontawesome.io/icons/" target="_blank">Font Awesome Icons</a></p>
          <p><a href="http://ionicons.com/" target="_blank">Ionicons Website</a></p>
          <p><a href="http://meta.wikimedia.org/wiki/Help:Displaying_a_formula" target="_blank">Mathematical Formula</a></p>
          <p><a href="http://knsv.github.io/mermaid/#flowcharts-basic-syntax" target="_blank">Flowchart Syntax</a></p>
          <p><a href="http://knsv.github.io/mermaid/#sequence-diagrams" target="_blank">Sequence Diagram Syntax</a></p>
          <p><a href="http://knsv.github.io/mermaid/#gant-diagrams" target="_blank">Gantt Diagram Syntax</a></p>
          <p>If none of the above solves your problem, please <a target="_blank" href="http://tylingsoft.com/contact/">contact us</a>.</p>
          <br/><a data-remodal-action="confirm" class="remodal-confirm">OK</a>
        </div>
        <div class="remodal" data-remodal-id="about-modal"> <!-- about modal -->
          <img src="icon.png" width="64"/>
          <h2>Markdown Plus</h2> Version 2.0.x
          <p>Markdown editor with extra features.</p>
          <p>Copyright © 2016 <a href="http://tylingsoft.com" target="_blank">Tylingsoft</a>.</p>
          <p>Home page: <a href="http://tylingsoft.com/markdown-plus/" target="_blank">http://tylingsoft.com/markdown-plus/</a>.</p>
          <br/><a data-remodal-action="confirm" class="remodal-confirm">OK</a>
        </div>
      </div>
      <div class="ui-layout-east">
        <article class="markdown-body" id="preview">
        	${markdownPreview}
        </article>
        <article class="markdown-body" id="cache" style="display: none"></article>
      </div>
    </div>
  </body>
</html>
