package cn.crap.enu;

public enum SettingStatus {
    /**
     * 前端配置会返回至浏览器，后端配资只在服务端使用
     */
    DELETE("删除", -1), COMMON("前端配置", 1), HIDDEN("后端设置", 100);
    private final int status;
    private final String name;

    SettingStatus(String name, int status) {
        this.status = status;
        this.name = name;
    }

    public static String getNameByValue(Byte status) {
        if (status == null) {
            return "";
        }
        for (SettingStatus projectStatus : SettingStatus.values()) {
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
