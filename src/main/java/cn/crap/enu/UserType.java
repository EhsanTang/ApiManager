package cn.crap.enu;

public enum UserType {
    ADMIN("管理员", Byte.valueOf("100")), USER("普通用户", Byte.valueOf("1"));
    private final byte type;
    private final String name;

    UserType(String name, byte type) {
        this.type = type;
        this.name = name;
    }

    public static String getNameByValue(Byte type) {
        if (type == null) {
            return "";
        }
        for (UserType userType : UserType.values()) {
            if (userType.getType() == type)
                return userType.getName();
        }
        return "";
    }

    public byte getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
