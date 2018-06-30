package cn.crap.model;

import java.io.Serializable;

public class ArticleWithBLOBs extends Article implements Serializable {
    private String content;

    private String markdown;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown == null ? null : markdown.trim();
    }
}