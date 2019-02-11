package cn.crap.dto;

import cn.crap.model.ProjectUserPO;

import java.util.List;
import java.util.Set;

public class ProjectUserDto extends ProjectUserPO {
	private String projectName;
    private List<PermissionDTO> crShowPermissionList;
    private Set<String> crShowPermissionSet;
    private String permissionStr;


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<PermissionDTO> getCrShowPermissionList() {
        return crShowPermissionList;
    }

    public void setCrShowPermissionList(List<PermissionDTO> crShowPermissionList) {
        this.crShowPermissionList = crShowPermissionList;
    }

    public Set<String> getCrShowPermissionSet() {
        return crShowPermissionSet;
    }

    public void setCrShowPermissionSet(Set<String> crShowPermissionSet) {
        this.crShowPermissionSet = crShowPermissionSet;
    }

    public String getPermissionStr() {
        return permissionStr;
    }

    public void setPermissionStr(String permissionStr) {
        this.permissionStr = permissionStr;
    }
}
