package cn.crap.enu;

public enum ProjectUserStatus {
    NORMAL("正常", 1);
    private final int status;
    private final String name;

    ProjectUserStatus(String name, int status) {
        this.status = status;
        this.name = name;
    }

    public static String getNameByValue(Byte status) {
        if (status == null) {
            return "";
        }
        for (ProjectUserStatus projectStatus : ProjectUserStatus.values()) {
            if (projectStatus.getStatus() == status)
                return projectStatus.getName();
        }
        return "";
    }

    public Byte getStatus() {
        return Byte.valueOf(status + "");
    }

    public String getName() {
        return name;
    }
}
