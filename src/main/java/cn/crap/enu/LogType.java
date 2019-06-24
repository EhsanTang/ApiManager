package cn.crap.enu;

public enum LogType {
    DELTET("删除"), UPDATE("修改");
    private final String name;

    LogType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
