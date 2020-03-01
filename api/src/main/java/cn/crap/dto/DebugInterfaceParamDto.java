package cn.crap.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DebugInterfaceParamDto {
	private List<DebugDto> debugs;
	@Deprecated
	private String moduleId;
	private String moduleUniKey;
	private String moduleName;
	private Integer version;
	private Byte status;
	private String projectUniKey;

}
