package cn.crap.model;

import lombok.Data;

@Data
public class ProjectUserPO extends BasePO {
    private String userName;
    private String userEmail;
    private String userId;
    private String permission;
    private Byte status;
    private String projectId;
    private String projectName;
    private String projectUniKey;
    private Byte type;
}