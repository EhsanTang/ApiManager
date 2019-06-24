package cn.crap.enu;

public enum ProjectShowType {
    ALL("所有项目", 0), MY_CREATE("我创建的项目", 1), JOIN("我加入的项目", 2), CREATE_JOIN("创建或加入的项目", 3), RECOMMEND("推荐项目", 4);
    private final int type;
    private final String name;

    ProjectShowType(String name, int type) {
        this.type = type;
        this.name = name;
    }

    public static String getNameByValue(Byte type) {
        if (type == null) {
            return "";
        }
        for (ProjectShowType projectType : ProjectShowType.values()) {
            if (projectType.getType() == type) {
                return projectType.getName();
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public byte getByteType() {
        return new Byte(type + "");
    }

    public String getName() {
        return name;
    }
}
