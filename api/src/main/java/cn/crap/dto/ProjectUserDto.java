package cn.crap.dto;

import cn.crap.model.ProjectUserPO;

import java.util.Set;

public class ProjectUserDto extends ProjectUserPO {
	private String projectName;
    private Set<String> permissionSet;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<String> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<String> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
