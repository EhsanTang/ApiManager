package cn.crap.dto;

import cn.crap.enu.AdminPermissionEnum;
import cn.crap.utils.Tools;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 前端下拉选着框DTO
 * @author Ehsan
 *
 */

public class PickDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String value;
	private String name;

	public PickDto(){}
	public PickDto(String id,String value,String name){
        this.id = handleId(id);
		this.value=value;
		this.name=name;
	}

    public PickDto(String id,String name){
        this.id = handleId(id);
		this.value=id;
		this.name=name;
	}
	public PickDto(AdminPermissionEnum dateType){
        this.id = handleId(name);
		this.value=dateType.name();
		this.name=dateType.getName();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

    private String handleId(String id) {
        if (id == null){
            id = System.currentTimeMillis() + Tools.getChar(10);
        }
        id = id.replaceAll("\\.", "_");
        id = StringUtils.deleteWhitespace(id);
        return id;
    }
}