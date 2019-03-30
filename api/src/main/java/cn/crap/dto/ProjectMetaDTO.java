package cn.crap.dto;

import cn.crap.model.ProjectMetaPO;

public class ProjectMetaDTO extends ProjectMetaPO {
	private String typeStr;
	private String envUrl;

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getEnvUrl() {
        return envUrl;
    }

    public void setEnvUrl(String envUrl) {
        this.envUrl = envUrl;
    }
}
