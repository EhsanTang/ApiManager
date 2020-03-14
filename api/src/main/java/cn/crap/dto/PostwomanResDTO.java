package cn.crap.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostwomanResDTO {
	private String projectUniKey;
	private String projectName;
    private String projectCover;

    private List<DebugInterfaceParamDto> moduleList;
}
