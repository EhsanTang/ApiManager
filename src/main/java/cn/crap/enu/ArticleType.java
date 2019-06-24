package cn.crap.enu;

public enum ArticleType {
    DICTIONARY("项目数据库表"), ARTICLE("文档");
    private final String name;

    ArticleType(String name) {
        this.name = name;
    }

    public static String getByEnumName(String enumName) {
        for (ArticleType article : ArticleType.values()) {
            if (article.name().equals(enumName)) {
                return article.getName();
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }
}
