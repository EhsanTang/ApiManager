package cn.crap.enu;

public enum CommentType {
    ARTICLE("文档评论", "ARTICLE"), BUG("缺陷评论", "BUG");
    private final String type;
    private final String name;

    CommentType(String name, String type) {
        this.type = type;
        this.name = name;
    }

    public static String getNameByValue(String type) {
        if (type == null) {
            return "";
        }
        for (CommentType commentType : CommentType.values()) {
            if (commentType.getType().equals(type))
                return commentType.getName();
        }
        return "";
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
